package net.kenji.woh.registry;

import net.kenji.woh.WeaponsOfHarmony;
import net.kenji.woh.block.ModBlocks;
import net.kenji.woh.item.custom.armor.ArbitersOutfit;
import net.kenji.woh.item.custom.armor.RoninAttire;
import net.kenji.woh.item.custom.shield.ArbitersShield;
import net.kenji.woh.item.custom.weapon.*;
import net.kenji.woh.item.custom.base.ResourceItem;
import net.minecraft.ChatFormatting;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class WohItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, WeaponsOfHarmony.MODID);

    public static final RegistryObject<Item> SHOTOGATANA_IN_SHEATH = ITEMS.register("z_shotogatana_in_sheath", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> SHOTOGATANA_SHEATH = ITEMS.register("z_shotogatana_sheath", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> WAKIZASHI_IN_SHEATH = ITEMS.register("z_wakizashi_in_sheath", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> WAKIZASHI_SHEATH = ITEMS.register("z_wakizashi_sheath", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> ODACHI_IN_SHEATH = ITEMS.register("z_odachi_in_sheath", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> ODACHI_SHEATH = ITEMS.register("z_odachi_sheath", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> ARBITERS_BLADE_IN_SHEATH = ITEMS.register("z_arbiters_blade_in_sheath", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> ARBITERS_BLADE_SHEATH = ITEMS.register("z_arbiters_blade_sheath", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> TENRAI_SPLIT_PRIMARY = ITEMS.register("z_split_tenrai_primary", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> TENRAI_SPLIT_SECONDARY = ITEMS.register("z_split_tenrai_secondary", () -> new Item(new Item.Properties()));


    public static final RegistryObject<Item> SHOTOGATANA = ITEMS.register("shotogatana", () -> new Shotogatana(WohWeaponTiers.SHOTOGATANA, new Item.Properties(), true, ChatFormatting.AQUA,"Shotogatana",
            """
                        A precise blade paired with a siya.
                        nimble and capable of cutting through the toughest of armor with style
                      """
            ));
    public static final RegistryObject<Item> TESSEN = ITEMS.register("tessen", () -> new Tessen(WohWeaponTiers.TESSEN, new Item.Properties(), true, ChatFormatting.AQUA,"Tessen",
            """
                      A nimble war fan that strikes with quick precision.
                      this mighty weapon is able to be thrown when holding down aim(Right Click), allowing for ranged attacks
                      """));
    public static final RegistryObject<Item> TSUME = ITEMS.register("tsume", () -> new Tsume(WohWeaponTiers.TSUME, new Item.Properties(), true, ChatFormatting.AQUA,"Tsume",
            """
                      A pair of sharp claws designed with close range swiping attacks in mind.
                      """));
    public static final RegistryObject<Item> ARBITERS_BLADE = ITEMS.register("arbiters_blade", () -> new ArbitersBlade(WohWeaponTiers.ARBITERS_BLADE, new Item.Properties(), true, ChatFormatting.AQUA,"Arbiters Blade",
            """
                      A Mysterious And Ancient Blade That Seems to Hold Some Sort Of Hidden Power.. Or even. Courage?
                      """));
    public static final RegistryObject<Item> TENRAI = ITEMS.register("tenrai", () -> new Tenrai(WohWeaponTiers.TERAI, new Item.Properties(), true, ChatFormatting.AQUA,"Tenrai",
            """
                      This Ancient Weapon known as the Tenrai, is a double bladed staff-sword-hybrid.
                      
                      Designed by ancient technologically advanced monks, this weapon is not only agile but can be split
                      in half to be used as dual swords, one for each hand.
                      """));

    public static final RegistryObject<Item> WAKIZASHI = ITEMS.register("wakizashi", () -> new Wakizashi(WohWeaponTiers.WAKIZASHI, new Item.Properties(), true, ChatFormatting.BLUE,"Wakizashi",
            """
                      A short and nimble, one sided blade that is capable of being dual wielded
                      """));
    public static final RegistryObject<Item> ODACHI = ITEMS.register("odachi", () -> new Odachi(WohWeaponTiers.ODACHI, new Item.Properties(), true, ChatFormatting.BLUE,"Odachi", """
                      A large, one sided greatsword that is slow but powerful
                      """));

    public static final RegistryObject<Item> ARBITERS_SHIELD = ITEMS.register("arbiters_shield", () -> new ArbitersShield(new Item.Properties().defaultDurability(3400), true, ChatFormatting.AQUA, "Arbiters Shield",
            """
                      An Extremely Durable Metal Shield, the metal used in the shield seems unworldly?
                      """));

    public static final RegistryObject<Item> FOLDED_IRON = ITEMS.register("folded_iron", () -> new ResourceItem(new Item.Properties().stacksTo(64), true, ChatFormatting.GRAY, "Folded Iron",
            """
                      A piece of iron that with much higher density than regular iron.
                      Although tough, it tends to be fragile without being smelted first
                      """));
    public static final RegistryObject<Item> FOLDED_STEEL = ITEMS.register("folded_steel", () -> new ResourceItem(new Item.Properties().stacksTo(64), true, ChatFormatting.GRAY, "Folded Steel",
            """
                      A very dense piece of steel
                      """));
    public static final RegistryObject<Item> HARDENED_STEEL_INGOT = ITEMS.register("hardened_steel_ingot", () -> new ResourceItem(new Item.Properties().stacksTo(64), true, ChatFormatting.GRAY, "Hardened Steel Ingot",
            """
                      An extremely durable and hard metal
                      that can be used to craft weapons of great variety
                      """));

    public static final RegistryObject<Item> ANCIENT_GOLD = ITEMS.register("ancient_gold", () -> new ResourceItem(new Item.Properties().stacksTo(64), true, ChatFormatting.GRAY, "Ancient Gold",
            """
                      An ancient piece of gold that seems to be incredibly strong.. This is unusual for gold?
                      """));

    public static final RegistryObject<Item> BLADE_CURVE_MODULE = ITEMS.register("blade_curve_module", () -> new ResourceItem(new Item.Properties().stacksTo(16), false, ChatFormatting.GRAY, "Blade Curve Module"));
    public static final RegistryObject<Item> WEAPON_REPAIR_MODULE = ITEMS.register("weapon_repair_module", () -> new ResourceItem(new Item.Properties().stacksTo(16), false, ChatFormatting.GRAY, "Weapon Repair Module"));
    public static final RegistryObject<Item> MYSTERIOUS_CLOTH = ITEMS.register("mysterious_cloth", () -> new ResourceItem(new Item.Properties().stacksTo(16), false, ChatFormatting.GRAY, "Mysterious Cloth"));


    public static final RegistryObject<Item> BROKEN_BLADE_AND_SHEATH = ITEMS.register("broken_blade", () -> new ResourceItem(new Item.Properties().stacksTo(1), false, ChatFormatting.GRAY, "Broken Blade & Sheath"));
    public static final RegistryObject<Item> BROKEN_FAN_BLADE = ITEMS.register("broken_fan_blade", () -> new ResourceItem(new Item.Properties().stacksTo(1), false, ChatFormatting.GRAY, "Broken Fan Blade"));
    public static final RegistryObject<Item> BROKEN_CLAWS = ITEMS.register("broken_claws", () -> new ResourceItem(new Item.Properties().stacksTo(1), false, ChatFormatting.GRAY, "Broken Claws"));
    public static final RegistryObject<Item> BROKEN_SPLIT_BLADE = ITEMS.register("broken_split_blade", () -> new ResourceItem(new Item.Properties().stacksTo(1), false, ChatFormatting.GRAY, "Broken Split Blade"));


    public static final RegistryObject<Item> METAL_RONIN_HEADWEAR = ITEMS.register("ronin_headwear", () -> new RoninAttire(new Item.Properties(), ArmorItem.Type.HELMET, "Ronin Headwear"));
    public static final RegistryObject<Item> RONIN_TUNIC = ITEMS.register("ronin_tunic", () -> new RoninAttire(new Item.Properties(), ArmorItem.Type.CHESTPLATE, "Ronin Tunic"));
    public static final RegistryObject<Item> RONIN_LEGGINGS = ITEMS.register("ronin_leggings", () -> new RoninAttire(new Item.Properties(), ArmorItem.Type.LEGGINGS, "Ronin Leggings"));
    public static final RegistryObject<Item> RONIN_BOOTS = ITEMS.register("ronin_boots", () -> new RoninAttire(new Item.Properties(), ArmorItem.Type.BOOTS, "Ronin Boots"));

    public static final RegistryObject<Item> ARBITERS_HAT = ITEMS.register("arbiters_hat", () -> new ArbitersOutfit(new Item.Properties(), ArmorItem.Type.HELMET, "Arbiters Hat"));
    public static final RegistryObject<Item> ARBITERS_TUNIC = ITEMS.register("arbiters_tunic", () -> new ArbitersOutfit(new Item.Properties(), ArmorItem.Type.CHESTPLATE, "Arbiters Tunic"));
    public static final RegistryObject<Item> ARBITERS_LEGGINGS = ITEMS.register("arbiters_leggings", () -> new ArbitersOutfit(new Item.Properties(), ArmorItem.Type.LEGGINGS, "Arbiters Leggings"));
    public static final RegistryObject<Item> ARBITERS_BOOTS = ITEMS.register("arbiters_boots", () -> new ArbitersOutfit(new Item.Properties(), ArmorItem.Type.BOOTS, "Arbiters Boots"));



    public static final RegistryObject<Item> SWORD_PEDISTOOL = ITEMS.register("sword_pedistool", () -> new BlockItem(ModBlocks.SWORD_PEDISTOOL.get(), new Item.Properties()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
