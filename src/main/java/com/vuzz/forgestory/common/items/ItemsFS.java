package com.vuzz.forgestory.common.items;

import com.vuzz.forgestory.ForgeStory;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.MapItemRenderer;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemsFS {
    private static String MOD_ID = ForgeStory.MOD_ID;
    private static ItemGroup MOD_TAB = ForgeStory.MOD_TAB;
    public static DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS,MOD_ID);

    //public static RegistryObject<Item> NPC_CREATOR = localSetup("npc_creator", itemNpcCreator::new,true);
    public static RegistryObject<Item> NPC_DELETER = ITEMS.register("npc_deleter", () -> new Item(new Item.Properties().fireResistant().stacksTo(1).tab(MOD_TAB)));
    public static RegistryObject<Item> STORY_REFRESHER = ITEMS.register("story_refresher", ModRefresherItem::new);

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
