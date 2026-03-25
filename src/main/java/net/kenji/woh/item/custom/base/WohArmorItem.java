package net.kenji.woh.item.custom.base;

import net.kenji.woh.api.interfaces.ITranslatableItem;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;

public class WohArmorItem extends ArmorItem implements ITranslatableItem {
    private final String itemName;
    private final String itemTooltip;

    public WohArmorItem(ArmorMaterial pMaterial, Type pType, Properties pProperties, String itemName) {
        super(pMaterial, pType, pProperties);
        this.itemName = itemName;
        this.itemTooltip = "";
    }
    public WohArmorItem(ArmorMaterial pMaterial, Type pType, Properties pProperties, String itemName, String itemTooltip) {
        super(pMaterial, pType, pProperties);
        this.itemName = itemName;
        this.itemTooltip = itemTooltip;
    }
    @Override
    public String getItemName(){
        return itemName;
    }
    @Override
    public String getItemTooltip(){
        return itemTooltip;
    }
}
