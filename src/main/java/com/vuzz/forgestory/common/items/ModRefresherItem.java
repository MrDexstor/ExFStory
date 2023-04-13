package com.vuzz.forgestory.common.items;

import com.vuzz.forgestory.ForgeStory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResultType;
import net.minecraft.world.World;
import net.minecraft.item.ItemUseContext;
import net.minecraft.item.Rarity;
import com.vuzz.forgestory.api.plotter.story.Root;

public class ModRefresherItem extends Item {

    public ModRefresherItem() {
        super(
                new Item.Properties()
                        .fireResistant()
                        .rarity(Rarity.EPIC)
                        .stacksTo(1)
                        .tab(ForgeStory.MOD_TAB)
        );
    }
    @Override
    public ActionResultType onItemUseFirst(ItemStack stack, ItemUseContext context) {
        World level = context.getLevel();
        if(!level.isClientSide) return onItemUseFirstServer(stack,context);
        return super.onItemUseFirst(stack,context);
    }

    protected ActionResultType onItemUseFirstServer(ItemStack stack, ItemUseContext context) {
        Root.reloadStories();
        return ActionResultType.PASS;
    }

}