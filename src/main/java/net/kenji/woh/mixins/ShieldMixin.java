package net.kenji.woh.mixins;

import net.kenji.woh.api.DualSkillWeaponCapability;
import net.kenji.woh.gameasset.WohSkills;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.level.Level;
import org.jline.utils.Log;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;

@Mixin(value = ShieldItem.class, remap = true)
public class ShieldMixin {

    @Inject(method = "use", at = @At("HEAD"), cancellable = true)
    public void onUse(Level pLevel, Player pPlayer, InteractionHand pHand, CallbackInfoReturnable<InteractionResultHolder<ItemStack>> cir){
        PlayerPatch<?> playerPatch = EpicFightCapabilities.getPlayerPatch(pPlayer);
        if (playerPatch == null) return;

        CapabilityItem capItem = EpicFightCapabilities.getItemStackCapability(playerPatch.getOriginal().getMainHandItem());
        SkillContainer container = playerPatch.getSkill(WohSkills.ARBITERS_SLASH);

        if((container != null && container.isActivated()) || (capItem instanceof DualSkillWeaponCapability weaponCapability && !weaponCapability.canUseShield(playerPatch))) {
            Log.info("Shield Canceling USE");
            ItemStack itemstack = pPlayer.getItemInHand(pHand);
            cir.cancel();
            cir.setReturnValue(InteractionResultHolder.pass(itemstack));
        }
    }

}