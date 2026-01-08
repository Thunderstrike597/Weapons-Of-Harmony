package net.kenji.woh.mixins;

import net.kenji.woh.gameasset.WohSkills;
import net.kenji.woh.registry.animation.GenericAnimations;
import net.minecraft.world.InteractionHand;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import yesman.epicfight.api.animation.AnimationProvider;
import yesman.epicfight.api.animation.LivingMotion;
import yesman.epicfight.api.animation.LivingMotions;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;

import java.util.List;
import java.util.Map;

@Mixin(value = CapabilityItem.class, remap = false)
public class EpicFightFistMixin {

    @Inject(method = "getAutoAttckMotion", at = @At("HEAD"), cancellable = true, remap = false)
    private void getCustomAutoAttackMotion(PlayerPatch<?> playerPatch, CallbackInfoReturnable<List<AnimationProvider<?>>> cir) {
        SkillContainer container = playerPatch.getSkill(WohSkills.KATAJUTSU);
        if (container != null) {
            CapabilityItem capItem = container.getExecuter().getHoldingItemCapability(InteractionHand.MAIN_HAND);
            if(capItem != null) {
                if(capItem.getWeaponCategory() == CapabilityItem.WeaponCategories.FIST) {
                    List<AnimationProvider<?>> customMotions = List.of(
                            (AnimationProvider<?>) () -> GenericAnimations.KATAJUTSU_AUTO_1,
                            (AnimationProvider<?>) () -> GenericAnimations.KATAJUTSU_AUTO_2,
                            (AnimationProvider<?>) () -> GenericAnimations.KATAJUTSU_AUTO_3,
                            (AnimationProvider<?>) () -> GenericAnimations.COMBAT_FIST_DASH,
                            (AnimationProvider<?>) () -> GenericAnimations.COMBAT_FIST_AIRKICK
                    );
                    cir.setReturnValue(customMotions);
                }
            }

        }
    }
    @Inject(method = "getLivingMotionModifier", at = @At("RETURN"), cancellable = true, remap = false)
    private void getCustomLivingMotion(LivingEntityPatch<?> patch, InteractionHand hand, CallbackInfoReturnable<Map<LivingMotion, AnimationProvider<?>>> cir) {
        if(patch instanceof PlayerPatch<?> playerPatch) {
            SkillContainer container = playerPatch.getSkill(WohSkills.KATAJUTSU);

            if (container != null && container.getSkill() != null) {
                Map<LivingMotion, AnimationProvider<?>> customMotions = cir.getReturnValue();
                CapabilityItem capItem = container.getExecuter().getHoldingItemCapability(InteractionHand.MAIN_HAND);
                if(capItem != null) {
                    if (capItem.getWeaponCategory() == CapabilityItem.WeaponCategories.FIST) {
                        // Now customMotions should have the original return value
                        if (customMotions != null) {
                            customMotions.put(LivingMotions.IDLE, GenericAnimations.KATAJUTSU_IDLE);
                            cir.setReturnValue(customMotions);
                        }
                    }
                }
            }
        }
    }

}