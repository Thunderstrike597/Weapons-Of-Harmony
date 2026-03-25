package net.kenji.woh.item.custom.base;

import net.kenji.woh.api.interfaces.ITranslatableItem;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ResourceItem extends Item implements ITranslatableItem {
    net.minecraft.ChatFormatting tooltipColor;
    private boolean hasTooltip = true;
    private final String itemName;
    private final String itemTooltip;


    public ResourceItem(Properties pProperties, boolean hasTooltip, net.minecraft.ChatFormatting tooltipColor, String itemName) {
        super(pProperties);
        this.tooltipColor = tooltipColor;
        this.hasTooltip = hasTooltip;
        this.itemName = itemName;
        this.itemTooltip = "";
    }
    public ResourceItem(Properties pProperties, boolean hasTooltip, net.minecraft.ChatFormatting tooltipColor, String itemName, String itemTooltip) {
        super(pProperties);
        this.tooltipColor = tooltipColor;
        this.hasTooltip = hasTooltip;
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

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
       if(hasTooltip) {
           pTooltipComponents.add(
                   Component.translatable(this.getDescriptionId() + ".tooltip")
                           .withStyle(tooltipColor)
           );
           super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
       }
    }

}
