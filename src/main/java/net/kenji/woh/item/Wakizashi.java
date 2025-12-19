package net.kenji.woh.item;

import net.kenji.woh.registry.item.WOHItems;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.phys.Vec3;
import org.joml.Quaternionf;

public class Wakizashi extends HolsterBaseItem {

    static Vec3Pair holsterPos = new Vec3Pair(
            0.2F, 0.15F, -0.7F,
            -0.85F, 0.225F, -0.18F);

    static Vec3Pair holsterSize = new Vec3Pair(
            0.7F, 0.7F, 0.7F,
            0.7F, 0.7F, 0.7F);

    static QuaternionFPair holsterRotation = new QuaternionFPair(
            -10, 210, 180,
            0, -90, 180
    );

    private static HolsterTransform holsterTransform = new HolsterTransform(
            holsterPos,
            holsterSize,
            holsterRotation
    );

    public Wakizashi(Tier tier, int damageIn, float speedIn, Properties builder, boolean hasTooltip, net.minecraft.ChatFormatting tooltipColor) {
        super(tier, damageIn, speedIn, builder, hasTooltip, tooltipColor, holsterTransform, WOHItems.WAKIZASHI_IN_SHEATH.get(), WOHItems.WAKIZASHI_SHEATH.get(), true);
    }


    @Override
    public boolean overrideStackedOnOther(ItemStack pStack, Slot pSlot, ClickAction pAction, Player pPlayer) {
        return false;
    }


}
