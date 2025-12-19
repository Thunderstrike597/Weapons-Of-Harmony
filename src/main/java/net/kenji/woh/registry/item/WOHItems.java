package net.kenji.woh.registry.item;

import net.kenji.woh.WeaponsOfHarmony;
import net.kenji.woh.item.EnhancedKatana;
import net.kenji.woh.item.ResourceItem;
import net.kenji.woh.item.Wakizashi;
import net.minecraft.ChatFormatting;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import yesman.epicfight.world.item.EpicFightItemTier;

public class WOHItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, WeaponsOfHarmony.MODID);

    public static final RegistryObject<Item> ENHANCED_KATANA_SHEATH = ITEMS.register("z_enhanced_katana_sheath", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> ENHANCED_KATANA_IN_SHEATH = ITEMS.register("z_enhanced_katana_in_sheath", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> WAKIZASHI_IN_SHEATH = ITEMS.register("z_wakizashi_in_sheath", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> WAKIZASHI_SHEATH = ITEMS.register("z_wakizashi_sheath", () -> new Item(new Item.Properties()));


    public static final RegistryObject<Item> ENHANCED_KATANA = ITEMS.register("enhanced_katana", () -> new EnhancedKatana(EpicFightItemTier.UCHIGATANA, 2, 1, new Item.Properties(), true, ChatFormatting.AQUA));
    public static final RegistryObject<Item> MASTERS_WAKIZASHI = ITEMS.register("masters_wakizashi", () -> new Wakizashi(EpicFightItemTier.UCHIGATANA, 2, 1, new Item.Properties(), true, ChatFormatting.AQUA));


    public static final RegistryObject<Item> FOLDED_IRON = ITEMS.register("folded_iron", () -> new ResourceItem(new Item.Properties().stacksTo(64), true, ChatFormatting.GRAY));
    public static final RegistryObject<Item> FOLDED_STEEL = ITEMS.register("folded_steel", () -> new ResourceItem(new Item.Properties().stacksTo(64), true, ChatFormatting.GRAY));
    public static final RegistryObject<Item> HARDENED_STEEL_INGOT = ITEMS.register("hardened_steel_ingot", () -> new ResourceItem(new Item.Properties().stacksTo(64), true, ChatFormatting.GRAY));

    public static final RegistryObject<Item> HARDENED_BLADE = ITEMS.register("hardened_blade", () -> new ResourceItem(new Item.Properties().stacksTo(64), false, ChatFormatting.GRAY));
    public static final RegistryObject<Item> PATTERNED_HANDLE = ITEMS.register("patterned_handle", () -> new ResourceItem(new Item.Properties().stacksTo(64), false, ChatFormatting.GRAY));
    public static final RegistryObject<Item> GOLD_TSUBA = ITEMS.register("gold_tsuba", () -> new ResourceItem(new Item.Properties().stacksTo(64), false, ChatFormatting.GRAY));


    public static final RegistryObject<Item> SHORT_HARDENED_BLADE = ITEMS.register("short_hardened_blade", () -> new ResourceItem(new Item.Properties().stacksTo(64), false, ChatFormatting.GRAY));
    public static final RegistryObject<Item> SILVER_TSUBA = ITEMS.register("silver_tsuba", () -> new ResourceItem(new Item.Properties().stacksTo(64), false, ChatFormatting.GRAY));


    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
