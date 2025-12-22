package net.kenji.woh.item;

import net.kenji.woh.registry.item.WOHItems;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import yesman.epicfight.gameasset.Armatures;

public class Odachi extends HolsterBaseItem {


    static Vec3Pair holsterPos = new Vec3Pair(
            -0.17600179F, 0.28800145F, 0.5720006F,    // Hotbar: slightly right, up near shoulder, behind back
            -0.17600179F, 0.28800145F, 0.5720006F    // Offhand: same position
    );


    static Vec3Pair holsterSize = new Vec3Pair(
            0.7F, 0.7F, 0.7F,
            0.7F, 0.7F, 0.7F);

    static QuaternionFPair holsterRotation = new QuaternionFPair(
            -1600.6406F, -1382.9609F, 290.4004F,
            0, -90, 180
    );

    private static HolsterTransform holsterTransform = new HolsterTransform(
            holsterPos,
            holsterSize,
            holsterRotation
    );
    public static JointPair holsterJoints = new JointPair(
            () -> Armatures.BIPED.chest,  // Lambda only evaluates when called
            () -> null
    );

    public Odachi(Tier tier, int damageIn, float speedIn, Properties builder, boolean hasTooltip, net.minecraft.ChatFormatting tooltipColor) {
        super(tier, damageIn, speedIn, builder, hasTooltip, tooltipColor, holsterTransform, WOHItems.ODACHI_HOLSTER.get(), null, false, holsterJoints);
    }

    @Override
    public boolean overrideStackedOnOther(ItemStack pStack, Slot pSlot, ClickAction pAction, Player pPlayer) {
        return false;
    }
}
