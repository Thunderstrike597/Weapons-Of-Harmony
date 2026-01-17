package net.kenji.woh.item.custom.weapon;

import net.kenji.woh.item.custom.base.HolsterWeaponBase;
import net.kenji.woh.registry.WohItems;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.Level;
import org.jline.utils.Log;
import yesman.epicfight.gameasset.Armatures;

public class ArbitersBlade extends HolsterWeaponBase {



    static Vec3Pair holsterPos = new Vec3Pair(
            0.49599558F, 1.05F, -0.14F,    // Hotbar: slightly right, up near shoulder, behind back
            0.49599558F, 1.05F, -0.22799583F    // Offhand: same position
    );


    static Vec3Pair holsterSize = new Vec3Pair(
            0.7F, 0.7F, 0.7F,
            0.7F, 0.7F, 0.7F);

    static QuaternionFPair holsterRotation = new QuaternionFPair(
            -1597.4375F, -1387.8457F, 290.4004F,
            0, -90, 180
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

    public ArbitersBlade(Tier tier, Properties builder, boolean hasTooltip, net.minecraft.ChatFormatting tooltipColor) {
        super(tier, 0, 1f, builder, hasTooltip, tooltipColor, holsterTransform, WohItems.ARBITERS_BLADE_IN_SHEATH.get(), WohItems.ARBITERS_BLADE_SHEATH.get(), true, holsterJoints);
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return stack.hasTag() && stack.getTag().getBoolean("woh_force_glint");
    }

    @Override
    public boolean overrideStackedOnOther(ItemStack pStack, Slot pSlot, ClickAction pAction, Player pPlayer) {
        return false;
    }
}
