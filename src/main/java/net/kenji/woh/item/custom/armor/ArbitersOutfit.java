package net.kenji.woh.item.custom.armor;

import net.kenji.woh.item.custom.base.WohArmorItem;
import net.kenji.woh.registry.WohArmorMaterials;
import net.minecraft.world.item.ArmorItem;

public class ArbitersOutfit extends WohArmorItem {

    public ArbitersOutfit(Properties pProperties, Type armorType, String itemName) {
        super(WohArmorMaterials.ARBITER, armorType, pProperties, itemName);
    }
}
