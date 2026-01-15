package net.kenji.woh.item.custom.shield;

import net.kenji.woh.item.custom.base.HolsterShieldBase;
import net.kenji.woh.registry.WohItems;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import yesman.epicfight.gameasset.Armatures;

public class ArbitersShield extends HolsterShieldBase {

    static Vec3Pair holsterPos = new Vec3Pair(
            0.35F, 0.1F, 0F,    // Hotbar: slightly right, up near shoulder, behind back
            0.35F, 0.1F, 0F    // Offhand: same position
    );

    static Vec3Pair holsterSize = new Vec3Pair(
            0.5F, 0.5F, 0.5F,
            0.5F, 0.5F, 0.5F);

    static QuaternionFPair holsterRotation = new QuaternionFPair(
            78, 40, -80,   // Changed Z from +60 to -45
            78, 40, -80
    );
    private static HolsterTransform holsterTransform = new HolsterTransform(
            holsterPos,
            holsterSize,
            holsterRotation
    );
    public static JointPair holsterJoints = new JointPair(
            () -> Armatures.BIPED.get().chest,  // Lambda only evaluates when called
            () -> null
    );


    public ArbitersShield(Item.Properties builder, boolean hasTooltip, net.minecraft.ChatFormatting tooltipColor) {
        super(builder, hasTooltip, tooltipColor, holsterTransform, ItemStack.EMPTY.getItem(), ItemStack.EMPTY.getItem(), false, holsterJoints);
    }

    @Override
    public boolean overrideStackedOnOther(ItemStack pStack, Slot pSlot, ClickAction pAction, Player pPlayer) {
        return false;
    }


    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pHand) {
        if(pPlayer.getItemInHand(InteractionHand.MAIN_HAND).getItem() == WohItems.ARBITERS_BLADE.get()) {
            return super.use(pLevel, pPlayer, pHand);
        }
        return InteractionResultHolder.pass(pPlayer.getItemInHand(InteractionHand.OFF_HAND));
    }

    @Override
    public boolean canAttackBlock(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer) {
       if(pPlayer.getItemInHand(InteractionHand.MAIN_HAND).getItem() == WohItems.ARBITERS_BLADE.get()) {
           return super.canAttackBlock(pState, pLevel, pPos, pPlayer);
       }
       return false;
    }
}
