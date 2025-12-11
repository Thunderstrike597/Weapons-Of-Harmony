package net.kenji.woh.item;

import net.corruptdog.cdm.world.item.CDAddonItems;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import yesman.epicfight.api.utils.math.OpenMatrix4f;
import yesman.epicfight.client.renderer.patched.item.RenderItemBase;
import yesman.epicfight.gameasset.Armatures;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.item.WeaponItem;

public class EnhancedKatana extends WeaponItem {
    public EnhancedKatana(Tier tier, int damageIn, float speedIn, Properties builder) {
        super(tier, damageIn, speedIn, builder);
    }

    @Override
    public boolean overrideStackedOnOther(ItemStack pStack, Slot pSlot, ClickAction pAction, Player pPlayer) {
        return false;
    }


}
