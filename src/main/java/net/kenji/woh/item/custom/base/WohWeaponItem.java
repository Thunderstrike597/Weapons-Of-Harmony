package net.kenji.woh.item.custom.base;

import net.kenji.woh.api.interfaces.ITranslatableItem;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import yesman.epicfight.world.item.WeaponItem;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class WohWeaponItem extends WeaponItem implements ITranslatableItem {
   private ChatFormatting tooltipColor;
   private boolean hasTooltip = true;
   private final String itemName;
   private final String itemTooltip;

   public Map<UUID, Boolean> isRendered = new HashMap<>();

    public WohWeaponItem(Tier tier, int damageIn, float speedIn, Properties builder, boolean hasTooltip, net.minecraft.ChatFormatting tooltipColor, String itemName) {
        super(tier, damageIn, speedIn, builder);
        this.tooltipColor = tooltipColor;
        this.hasTooltip = hasTooltip;
        this.itemName = itemName;
        this.itemTooltip = "";
    }
    public WohWeaponItem(Tier tier, int damageIn, float speedIn, Properties builder, boolean hasTooltip, net.minecraft.ChatFormatting tooltipColor, String itemName, String itemTooltip) {
        super(tier, damageIn, speedIn, builder);
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
       if(this.hasTooltip) {
           pTooltipComponents.add(
                   Component.translatable(this.getDescriptionId() + ".tooltip")
                           .withStyle(tooltipColor)
           );
           super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
       }
    }
}
