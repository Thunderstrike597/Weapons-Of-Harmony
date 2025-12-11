package net.kenji.woh.registry.item;

import net.kenji.woh.WeaponsOfHarmony;
import net.kenji.woh.item.EnhancedKatana;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import yesman.epicfight.world.item.EpicFightItemTier;

public class WOHItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, WeaponsOfHarmony.MODID);

    public static final RegistryObject<Item> ENHANCED_KATANA = ITEMS.register("enhanced_katana", () -> new EnhancedKatana(EpicFightItemTier.UCHIGATANA, 2, 1, new Item.Properties()));
    public static final RegistryObject<Item> ENHANCED_KATANA_SHEATH = ITEMS.register("enhanced_katana_sheath", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> ENHANCED_KATANA_IN_SHEATH = ITEMS.register("enhanced_katana_in_sheath", () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> FOLDED_IRON_INGOT = ITEMS.register("folded_iron_ingot", () -> new Item(new Item.Properties().stacksTo(64)));
    public static final RegistryObject<Item> STEEL_INGOT = ITEMS.register("steel_ingot", () -> new Item(new Item.Properties().stacksTo(64)));
    public static final RegistryObject<Item> FOLDED_STEEL_INGOT = ITEMS.register("folded_steel_ingot", () -> new Item(new Item.Properties().stacksTo(64)));


    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
