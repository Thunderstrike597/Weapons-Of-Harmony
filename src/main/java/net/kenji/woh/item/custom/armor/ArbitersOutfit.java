package net.kenji.woh.item.custom.armor;

import net.kenji.woh.registry.WohArmorMaterials;
import net.minecraft.world.item.ArmorItem;

public class ArbitersOutfit extends ArmorItem {

    public ArbitersOutfit(Properties pProperties, Type armorType) {
        super(WohArmorMaterials.ARBITER, armorType, pProperties);
    }
}
