package net.kenji.woh.registry;

import net.kenji.woh.WeaponsOfHarmony;
import net.kenji.woh.item.custom.armor.RoninAttire;
import net.kenji.woh.item.custom.weapon.Odachi;
import net.kenji.woh.item.custom.weapon.Shotogatana;
import net.kenji.woh.item.custom.base.ResourceItem;
import net.kenji.woh.item.custom.weapon.Tessen;
import net.kenji.woh.item.custom.weapon.Wakizashi;
import net.minecraft.ChatFormatting;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import yesman.epicfight.world.item.EpicFightItemTier;

public class WOHItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, WeaponsOfHarmony.MODID);

    public static final RegistryObject<Item> SHOTOGATANA_SHEATH = ITEMS.register("z_shotogatana_sheath", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> SHOTOGATANA_IN_SHEATH = ITEMS.register("z_shotogatana_in_sheath", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> WAKIZASHI_IN_SHEATH = ITEMS.register("z_wakizashi_in_sheath", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> WAKIZASHI_SHEATH = ITEMS.register("z_wakizashi_sheath", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> ODACHI_IN_SHEATH = ITEMS.register("z_odachi_in_sheath", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> ODACHI_SHEATH = ITEMS.register("z_odachi_sheath", () -> new Item(new Item.Properties()));


    public static final RegistryObject<Item> SHOTOGATANA = ITEMS.register("shotogatana", () -> new Shotogatana(WohWeaponTiers.SHOTOGATANA, new Item.Properties(), true, ChatFormatting.AQUA));
    public static final RegistryObject<Item> TESSEN = ITEMS.register("tessen", () -> new Tessen(WohWeaponTiers.TESSEN, new Item.Properties(), true, ChatFormatting.AQUA));
    public static final RegistryObject<Item> WAKIZASHI = ITEMS.register("wakizashi", () -> new Wakizashi(WohWeaponTiers.WAKIZASHI, new Item.Properties(), true, ChatFormatting.BLUE));
    public static final RegistryObject<Item> ODACHI = ITEMS.register("odachi", () -> new Odachi(WohWeaponTiers.ODACHI, new Item.Properties(), true, ChatFormatting.BLUE));


    public static final RegistryObject<Item> FOLDED_IRON = ITEMS.register("folded_iron", () -> new ResourceItem(new Item.Properties().stacksTo(64), true, ChatFormatting.GRAY));
    public static final RegistryObject<Item> FOLDED_STEEL = ITEMS.register("folded_steel", () -> new ResourceItem(new Item.Properties().stacksTo(64), true, ChatFormatting.GRAY));
    public static final RegistryObject<Item> HARDENED_STEEL_INGOT = ITEMS.register("hardened_steel_ingot", () -> new ResourceItem(new Item.Properties().stacksTo(64), true, ChatFormatting.GRAY));

    public static final RegistryObject<Item> BLADE_CURVE_MODULE = ITEMS.register("blade_curve_module", () -> new ResourceItem(new Item.Properties().stacksTo(16), false, ChatFormatting.GRAY));
    public static final RegistryObject<Item> BROKEN_BLADE_AND_SHEATH = ITEMS.register("broken_blade", () -> new ResourceItem(new Item.Properties().stacksTo(1), false, ChatFormatting.GRAY));


    public static final RegistryObject<Item> METAL_RONIN_HEADWEAR = ITEMS.register("metal_ronin_headwear", () -> new RoninAttire(new Item.Properties(), ArmorItem.Type.HELMET));
    public static final RegistryObject<Item> RONIN_TUNIC = ITEMS.register("ronin_tunic", () -> new RoninAttire(new Item.Properties(), ArmorItem.Type.CHESTPLATE));
    public static final RegistryObject<Item> RONIN_LEGGINGS = ITEMS.register("ronin_leggings", () -> new RoninAttire(new Item.Properties(), ArmorItem.Type.LEGGINGS));
    public static final RegistryObject<Item> RONIN_BOOTS = ITEMS.register("ronin_boots", () -> new RoninAttire(new Item.Properties(), ArmorItem.Type.BOOTS));


    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
