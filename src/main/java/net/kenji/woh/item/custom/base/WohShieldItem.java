package net.kenji.woh.item.custom.base;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import yesman.epicfight.world.item.WeaponItem;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class WohShieldItem extends ShieldItem {
   private ChatFormatting tooltipColor;
   private boolean hasTooltip = true;

   public Map<UUID, Boolean> isRendered = new HashMap<>();

    public WohShieldItem(Properties builder, boolean hasTooltip, ChatFormatting tooltipColor) {
        super(builder);
        this.tooltipColor = tooltipColor;
        this.hasTooltip = hasTooltip;
    }


    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
       if(this.hasTooltip) {
           pTooltipComponents.add(
                   Component.translatable(this.getDescriptionId() + ".tooltip")
                           .withStyle(tooltipColor)
           );
           super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
       }
    }
}
