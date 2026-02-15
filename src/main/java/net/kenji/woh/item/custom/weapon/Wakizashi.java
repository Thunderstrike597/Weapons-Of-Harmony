package net.kenji.woh.item.custom.weapon;

import net.kenji.woh.item.custom.base.HolsterWeaponBase;
import net.kenji.woh.registry.WohItems;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import yesman.epicfight.gameasset.Armatures;

public class Wakizashi extends HolsterWeaponBase {

    static Vec3Pair holsterPos = new Vec3Pair(
            -0.32800245F, 0.4959988F, -0.79599875F,
            -0.85F, 0.225F, -0.18F);

    static Vec3Pair holsterSize = new Vec3Pair(
            0.7F, 0.7F, 0.7F,
            0.7F, 0.7F, 0.7F);

    static QuaternionFPair holsterRotation = new QuaternionFPair(
            47.24048F, -150.32031F, 468.0F,
            0, -90, 180
    );

    private static HolsterTransform holsterTransform = new HolsterTransform(
            holsterPos,
            holsterSize,
            holsterRotation
    );
    public static JointPair holsterJoints = new JointPair(
            () -> Armatures.BIPED.get().rootJoint,
            () -> Armatures.BIPED.get().rootJoint
    );

    public Wakizashi(Tier tier, Properties builder, boolean hasTooltip, net.minecraft.ChatFormatting tooltipColor) {
        super(tier, 0, -1.6f, builder, hasTooltip, tooltipColor, holsterTransform, WohItems.WAKIZASHI_IN_SHEATH.get(), WohItems.WAKIZASHI_SHEATH.get(), true, holsterJoints);
    }


    @Override
    public boolean overrideStackedOnOther(ItemStack pStack, Slot pSlot, ClickAction pAction, Player pPlayer) {
        return false;
    }


}
