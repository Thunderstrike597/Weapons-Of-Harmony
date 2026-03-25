package net.kenji.woh.item.custom.armor;

import net.kenji.woh.item.custom.base.WohArmorItem;
import net.kenji.woh.registry.WohArmorMaterials;
import net.minecraft.world.item.ArmorItem;

public class RoninAttire extends WohArmorItem {

    public RoninAttire(Properties pProperties, ArmorItem.Type armorType, String itemName) {
        super(WohArmorMaterials.RONIN, armorType, pProperties, itemName);
    }

}
