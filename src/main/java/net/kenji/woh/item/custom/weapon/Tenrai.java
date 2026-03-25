package net.kenji.woh.item.custom.weapon;

import net.kenji.woh.item.custom.base.WohWeaponItem;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;

public class Tenrai extends WohWeaponItem {


    public Tenrai(Tier tier, Properties builder, boolean hasTooltip, net.minecraft.ChatFormatting tooltipColor, String itemName, String itemTooltip) {
        super(tier, 0, -1.8F, builder, hasTooltip, tooltipColor, itemName, itemTooltip);
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
