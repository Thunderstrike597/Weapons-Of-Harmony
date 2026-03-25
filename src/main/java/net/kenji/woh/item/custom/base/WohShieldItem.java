package net.kenji.woh.item.custom.base;

import net.kenji.woh.api.interfaces.ITranslatableItem;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class WohShieldItem extends ShieldItem implements ITranslatableItem {
   private ChatFormatting tooltipColor;
   private boolean hasTooltip = true;
    private final String itemName;
    private final String itemTooltip;

    public Map<UUID, Boolean> isRendered = new HashMap<>();

    public WohShieldItem(Properties builder, boolean hasTooltip, ChatFormatting tooltipColor, String itemName) {
        super(builder);
        this.tooltipColor = tooltipColor;
        this.hasTooltip = hasTooltip;
        this.itemName = itemName;
        this.itemTooltip = "";
    }
    public WohShieldItem(Properties builder, boolean hasTooltip, ChatFormatting tooltipColor, String itemName, String itemTooltip) {
        super(builder);
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
    public boolean shouldRenderInHand(PlayerPatch<?> playerPatch, Item item){
        ItemStack holdingItem = playerPatch.getOriginal().getItemInHand(InteractionHand.MAIN_HAND);
        return (holdingItem.getItem() == item && playerPatch.isEpicFightMode()) || (playerPatch.getOriginal().isUsingItem() && playerPatch.getOriginal().getUseItem().getItem() == this);
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
