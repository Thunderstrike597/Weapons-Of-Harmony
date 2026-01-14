package net.kenji.woh.item.custom.weapon;

import net.kenji.woh.item.custom.base.HolsterWeaponBase;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import yesman.epicfight.gameasset.Armatures;

public class Tessen extends HolsterWeaponBase {


    static Vec3Pair holsterPos = new Vec3Pair(
            0.047998372F, 0.46F, 0.14F,
            -1, -1,-1);

    static Vec3Pair holsterSize = new Vec3Pair(
            0.7F, 0.7F, 0.7F,
            0, 0, 0);

    static QuaternionFPair holsterRotation = new QuaternionFPair(
            47, -160, 468,    // Hotbar: slight forward tilt, diagonal angle, flipped
            15, -45, 180
    );

    private static HolsterTransform holsterTransform = new HolsterTransform(
            holsterPos,
            holsterSize,
            holsterRotation
    );
    public static JointPair holsterJoints = new JointPair(
            () -> Armatures.BIPED.get().rootJoint,
            () -> null
    );

    public Tessen(Tier tier, Properties builder, boolean hasTooltip, net.minecraft.ChatFormatting tooltipColor) {
        super(tier, 0, 0.8f, builder, hasTooltip, tooltipColor);
    }

    @Override
    public boolean overrideStackedOnOther(ItemStack pStack, Slot pSlot, ClickAction pAction, Player pPlayer) {
        return false;
    }
}
