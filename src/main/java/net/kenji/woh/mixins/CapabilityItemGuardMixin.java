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
            Map<LivingMotion, AnimationManager.AnimationAccessor<? extends StaticAnimation>> originalMap = cir.getReturnValue();
            Map<LivingMotion, AnimationManager.AnimationAccessor<? extends StaticAnimation>> mutableMap = new HashMap<>(originalMap);

            if(playerPatch.getOriginal().getOffhandItem().getItem() instanceof ArbitersShield) {
                if (mutableMap.get(LivingMotions.BLOCK_SHIELD) == null || mutableMap.get(LivingMotions.BLOCK_SHIELD).get() == Animations.EMPTY_ANIMATION || mutableMap.get(LivingMotions.BLOCK_SHIELD).get() == Animations.BIPED_BLOCK) {
                    mutableMap.put(LivingMotions.BLOCK_SHIELD, GenericAnimations.ARBITERS_SHIELD_BLOCK);
                }
            }
            else if(mutableMap.get(LivingMotions.BLOCK_SHIELD) == null || mutableMap.get(LivingMotions.BLOCK_SHIELD).get() == Animations.EMPTY_ANIMATION) {
                mutableMap.put(LivingMotions.BLOCK_SHIELD, Animations.BIPED_BLOCK);
            }

            cir.setReturnValue(mutableMap);
        }
    }
}