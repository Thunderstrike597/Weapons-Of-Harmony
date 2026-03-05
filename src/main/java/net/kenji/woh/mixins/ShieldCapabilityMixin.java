package net.kenji.woh.mixins;

import net.kenji.woh.item.custom.shield.ArbitersShield;
import net.kenji.woh.registry.animation.GenericAnimations;
import net.minecraft.world.InteractionHand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import yesman.epicfight.api.animation.AnimationManager;
import yesman.epicfight.api.animation.LivingMotion;
import yesman.epicfight.api.animation.LivingMotions;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.item.ShieldCapability;

import java.util.HashMap;
import java.util.Map;

@Mixin(value = ShieldCapability.class, remap = false)
public class ShieldCapabilityMixin {

    @Inject(method = "getLivingMotionModifier", at = @At("RETURN"), cancellable = true, remap = false)
    private void getCustomAutoAttackMotion(LivingEntityPatch<?> playerdata, InteractionHand hand, CallbackInfoReturnable<Map<LivingMotion, AnimationManager.AnimationAccessor<?>>> cir) {
        try {
            if (!(playerdata.getOriginal().getOffhandItem().getItem() instanceof ArbitersShield))
                return;
        } catch (IllegalStateException e) {
            return; // Config not loaded yet, skip
        }
        if(!(playerdata instanceof PlayerPatch<?> playerPatch)) return;

        Map<LivingMotion, AnimationManager.AnimationAccessor<?>> originalMap = cir.getReturnValue();
        if (originalMap == null) return;

        Map<LivingMotion, AnimationManager.AnimationAccessor<?>> mutableMap = new HashMap<>(originalMap);
        mutableMap.put(LivingMotions.BLOCK_SHIELD, GenericAnimations.ARBITERS_SHIELD_BLOCK);
        cir.setReturnValue(mutableMap);
    }
}
