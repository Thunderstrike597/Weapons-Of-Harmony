package net.kenji.woh.item.custom.armor;

import net.kenji.woh.registry.WohArmorMaterials;
import net.minecraft.world.item.ArmorItem;

public class RoninAttire extends ArmorItem {

    public RoninAttire(Properties pProperties, ArmorItem.Type armorType) {
        super(WohArmorMaterials.RONIN, armorType, pProperties);
    }
}
