package net.kenji.woh.mixins;

import net.kenji.woh.gameasset.WohSkills;
import net.kenji.woh.item.custom.shield.ArbitersShield;
import net.kenji.woh.registry.animation.GenericAnimations;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import yesman.epicfight.api.animation.AnimationProvider;
import yesman.epicfight.api.animation.LivingMotion;
import yesman.epicfight.api.animation.LivingMotions;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.item.ShieldCapability;

import java.util.HashMap;
import java.util.Map;

@Mixin(value = ShieldItem.class)
public class ShieldMixin {

    @Inject(method = "use", at = @At("HEAD"), cancellable = true)
    private void getCustomAutoAttackMotion(Level pLevel, Player pPlayer, InteractionHand pHand, CallbackInfoReturnable<InteractionResultHolder<ItemStack>> cir) {
        LivingEntityPatch<?> livingEntityPatch = EpicFightCapabilities.getEntityPatch(pPlayer, PlayerPatch.class);
        if(livingEntityPatch instanceof PlayerPatch<?> playerPatch){
            if(playerPatch.getSkill(WohSkills.ARBITERS_SLASH) != null){
                if(playerPatch.getSkill(WohSkills.ARBITERS_SLASH).isActivated()){
                   // cir.cancel();
                    //cir.setReturnValue(InteractionResultHolder.pass(pPlayer.getItemInHand(InteractionHand.OFF_HAND)));
                }
            }
        }
    }
}
