package net.kenji.woh.item.custom.base;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;

import java.util.List;
import java.util.function.Supplier;

public class WohShieldItem extends ShieldItem {
   private ChatFormatting tooltipColor;
   private boolean hasTooltip = true;
    private Supplier<StaticAnimation> blockAnimationSupplier;
    public WohShieldItem(Properties builder, boolean hasTooltip, ChatFormatting tooltipColor, Supplier<StaticAnimation> blockAnimation) {
        super(builder);
        this.tooltipColor = tooltipColor;
        this.hasTooltip = hasTooltip;
        this.blockAnimationSupplier = blockAnimation != null ? blockAnimation : () -> Animations.BIPED_BLOCK;

    }
    public boolean shouldRenderInHand(PlayerPatch<?> playerPatch, Item item){
        ItemStack holdingItem = playerPatch.getOriginal().getItemInHand(InteractionHand.MAIN_HAND);
        return (holdingItem.getItem() == item && playerPatch.isBattleMode()) || (playerPatch.getOriginal().isUsingItem() && playerPatch.getOriginal().getUseItem().getItem() == this);
    }

    public StaticAnimation getBlockAnimation(){
        return blockAnimationSupplier.get();
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
