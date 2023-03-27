package com.vuzz.forgestory.common.entity;

import com.vuzz.forgestory.common.networking.NBTBank;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.GroundPathNavigator;
import net.minecraft.util.HandSide;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.IAnimationTickable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

public class NPCEntity extends MobEntity implements IAnimatable,IAnimationTickable {

    private AnimationFactory anFactory = GeckoLibUtil.createFactory(this);

    private final NonNullList<ItemStack> armorInv = NonNullList.withSize(4, ItemStack.EMPTY);
    private final NonNullList<ItemStack> inventory = NonNullList.withSize(2, ItemStack.EMPTY);

    private final NBTBank nbtBank = new NBTBank();

    private int ticks = 0;

    public NPCEntity(EntityType<? extends MobEntity> type, World world) { super(type,world); }

    public static AttributeModifierMap.MutableAttribute genAttributes() {
        return MobEntity.createMobAttributes()
                .add(Attributes.MAX_HEALTH,20.0D)
                .add(Attributes.ARMOR,4D)
                .add(Attributes.ARMOR_TOUGHNESS,4D)
                .add(Attributes.MOVEMENT_SPEED,0.3);
    }

    @Override
    public void tick() {
        super.tick();
        if(level.isClientSide) return;
        GroundPathNavigator nav = (GroundPathNavigator) getNavigation();
            nav.setCanFloat(true);
            nav.setCanOpenDoors(true);
        if(ticks % 10 == 0) flushOnClient();
        ticks++;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this,"controller",5,this::predicateDef));
    }

    private <E extends IAnimatable> PlayState predicateDef(AnimationEvent<E> event) {
        event.getController().transitionLengthTicks = 5;
        String idleAnim = getPersistentData().getString("idleAnim");
        String walkAnim = getPersistentData().getString("walkAnim");
        if (event.isMoving()) {
            AnimationBuilder def = new AnimationBuilder()
                .loop(walkAnim == "" ? "animation.npc.walk" : walkAnim);
            event.getController().setAnimation(def);
            return PlayState.CONTINUE;
        }
        AnimationBuilder def = new AnimationBuilder()
            .loop(idleAnim == "" ? "animation.npc.idle" : idleAnim);             
        event.getController().setAnimation(def);
        return PlayState.CONTINUE;
    }

    public void setTexturePath(String texture) { nbtBank.postOnClient("texturePath", texture, NBTBank.Type.STRING); }
    public void setModelPath(String model) { nbtBank.postOnClient("modelPath", model, NBTBank.Type.STRING); }
    public void setAnimationPath(String model) { nbtBank.postOnClient("animPath", model, NBTBank.Type.STRING); }
    public void flushOnClient() { nbtBank.flush(this); }

    @Override
    public void setItemSlot(EquipmentSlotType arg0, ItemStack arg1) {
        if(arg0 == EquipmentSlotType.MAINHAND) inventory.set(0, arg1);
        if(arg0 == EquipmentSlotType.OFFHAND) inventory.set(1, arg1);
    }

    @Override
    public ItemStack getItemBySlot(EquipmentSlotType arg0) {
        if(arg0 == EquipmentSlotType.MAINHAND) return inventory.get(0);
        if(arg0 == EquipmentSlotType.OFFHAND) return inventory.get(1);
        return ItemStack.EMPTY;
    }

    @Override public boolean isLeashed() { return false;}
    @Override public boolean canChangeDimensions() {return true;}
    @Override public boolean removeWhenFarAway(double p_213397_1_) {return false;}
    @Override public boolean isPersistenceRequired() {return true;}
    @Override public AnimationFactory getFactory() { return this.anFactory; }
    @Override protected GroundPathNavigator createNavigation(World world) { return new GroundPathNavigator(this,world); }
    @Override public Iterable<ItemStack> getArmorSlots() { return armorInv; }
    @Override public HandSide getMainArm() { return HandSide.RIGHT; }

    @Override
    public int tickTimer() {
        return tickCount;
    }

}
