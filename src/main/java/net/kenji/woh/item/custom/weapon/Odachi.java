package net.kenji.woh.item.custom.weapon;

import net.kenji.woh.item.custom.base.HolsterBaseItem;
import net.kenji.woh.registry.WohItems;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import yesman.epicfight.gameasset.Armatures;

public class Odachi extends HolsterBaseItem {


    static Vec3Pair holsterPos = new Vec3Pair(
            0.49599558F, 1.1199979F, -0.22799583F,    // Hotbar: slightly right, up near shoulder, behind back
            0.49599558F, 1.1199979F, -0.22799583F    // Offhand: same position
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
            () -> Armatures.BIPED.chest,  // Lambda only evaluates when called
            () -> null
    );

    public Odachi(Tier tier, Properties builder, boolean hasTooltip, net.minecraft.ChatFormatting tooltipColor) {
        super(tier, 0, 0.8f, builder, hasTooltip, tooltipColor, holsterTransform, WohItems.ODACHI_IN_SHEATH.get(), WohItems.ODACHI_SHEATH.get(), false, holsterJoints);
    }

    @Override
    public boolean overrideStackedOnOther(ItemStack pStack, Slot pSlot, ClickAction pAction, Player pPlayer) {
        return false;
    }
}
