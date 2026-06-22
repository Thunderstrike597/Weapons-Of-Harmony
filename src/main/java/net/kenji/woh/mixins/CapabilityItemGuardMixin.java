package net.kenji.woh.mixins;

import net.kenji.woh.gameasset.WohSkills;
import net.kenji.woh.item.custom.shield.ArbitersShield;
import net.kenji.woh.registry.animation.GenericAnimations;
import net.minecraft.world.InteractionHand;
import org.jline.utils.Log;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import yesman.epicfight.api.animation.AnimationManager;
import yesman.epicfight.api.animation.LivingMotion;
import yesman.epicfight.api.animation.LivingMotions;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mixin(value = CapabilityItem.class, remap = false, priority = 800)
public class CapabilityItemGuardMixin {

    @Inject(method = "getLivingMotionModifier", at = @At("RETURN"), cancellable = true, remap = false)
    private void getCustomGuardMotion(LivingEntityPatch<?> patch, InteractionHand hand, CallbackInfoReturnable<Map<LivingMotion, AnimationManager.AnimationAccessor<? extends StaticAnimation>>> cir) {
        CapabilityItem self = (CapabilityItem) (Object)this;
        if(patch instanceof PlayerPatch<?> playerPatch) {
            CapabilityItem capItem = EpicFightCapabilities.getItemStackCapability(patch.getOriginal().getMainHandItem());
            Map<LivingMotion, AnimationManager.AnimationAccessor<? extends StaticAnimation>> mainHandMap = capItem.getLivingMotionModifier(patch, InteractionHand.MAIN_HAND);

            Map<LivingMotion, AnimationManager.AnimationAccessor<? extends StaticAnimation>> originalMap = cir.getReturnValue();
            Map<LivingMotion, AnimationManager.AnimationAccessor<? extends StaticAnimation>> mutableMap = new HashMap<>(originalMap);
            AnimationManager.AnimationAccessor<? extends StaticAnimation> mainHandAccessor = mainHandMap.get(LivingMotions.BLOCK_SHIELD);
            AnimationManager.AnimationAccessor<? extends StaticAnimation> accessor = originalMap.get(LivingMotions.BLOCK_SHIELD);
            boolean isAlternativeBlockPresent = accessor != null && accessor.get() != Animations.BIPED_BLOCK && accessor.get() != Animations.EMPTY_ANIMATION;
            boolean isAlternativeMainHandBlockPresent = mainHandAccessor != null && mainHandAccessor.get() != Animations.BIPED_BLOCK && mainHandAccessor.get() != Animations.EMPTY_ANIMATION;

            if(!isAlternativeBlockPresent && !isAlternativeMainHandBlockPresent) {
                if (playerPatch.getOriginal().getOffhandItem().getItem() instanceof ArbitersShield) {
                    if (accessor == null || accessor.get() == Animations.EMPTY_ANIMATION || accessor.get() == Animations.BIPED_BLOCK) {
                        mutableMap.put(LivingMotions.BLOCK_SHIELD, GenericAnimations.ARBITERS_SHIELD_BLOCK);
                    }
                } else if (accessor == null || accessor.get() == Animations.EMPTY_ANIMATION) {
                    mutableMap.put(LivingMotions.BLOCK_SHIELD, Animations.BIPED_BLOCK);
                }
            }
            else if(isAlternativeMainHandBlockPresent){
                mutableMap.put(LivingMotions.BLOCK_SHIELD, mainHandAccessor);
            }
            cir.setReturnValue(mutableMap);
        }
    }
}