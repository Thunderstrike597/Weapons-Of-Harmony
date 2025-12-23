package net.kenji.woh.item.custom.base;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ResourceItem extends Item {
    net.minecraft.ChatFormatting tooltipColor;
    private boolean hasTooltip = true;

    public ResourceItem(Properties pProperties, boolean hasTooltip, net.minecraft.ChatFormatting tooltipColor) {
        super(pProperties);
        this.tooltipColor = tooltipColor;
        this.hasTooltip = hasTooltip;
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
