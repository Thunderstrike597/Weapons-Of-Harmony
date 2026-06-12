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
import yesman.epicfight.world.capabilities.item.ShieldCapability;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mixin(value = CapabilityItem.class, remap = false)
public class CapabilityItemMixin {

    @Inject(method = "getAutoAttackMotion", at = @At("HEAD"), cancellable = true, remap = false)
    private void getCustomAutoAttackMotion(PlayerPatch<?> playerPatch, CallbackInfoReturnable<List<AnimationManager.AnimationAccessor<? extends AttackAnimation>>> cir) {
        SkillContainer container = playerPatch.getSkill(WohSkills.KATAJUTSU);
        if (container != null) {
            CapabilityItem capItem = container.getExecutor().getHoldingItemCapability(InteractionHand.MAIN_HAND);
            if(capItem != null) {
                if(capItem.getWeaponCategory() == CapabilityItem.WeaponCategories.FIST) {
                    List<AnimationManager.AnimationAccessor<? extends AttackAnimation>> customMotions = List.of(
                            GenericAnimations.KATAJUTSU_AUTO_1,
                            GenericAnimations.KATAJUTSU_AUTO_2,
                            GenericAnimations.KATAJUTSU_AUTO_3,
                            GenericAnimations.COMBAT_FIST_DASH,
                           GenericAnimations.COMBAT_FIST_AIRKICK
                    );
                    cir.setReturnValue(customMotions);
                }
            }
        }
    }

    @Inject(method = "getLivingMotionModifier", at = @At("RETURN"), cancellable = true, remap = false)
    private void getCustomLivingMotion(LivingEntityPatch<?> patch, InteractionHand hand, CallbackInfoReturnable<Map<LivingMotion, AnimationManager.AnimationAccessor<? extends StaticAnimation>>> cir) {
        CapabilityItem self = (CapabilityItem) (Object)this;
        if(patch instanceof PlayerPatch<?> playerPatch) {
            SkillContainer container = playerPatch.getSkill(WohSkills.KATAJUTSU);
            Map<LivingMotion, AnimationManager.AnimationAccessor<? extends StaticAnimation>> originalMap = cir.getReturnValue();



            Map<LivingMotion, AnimationManager.AnimationAccessor<? extends StaticAnimation>> mutableMap = new HashMap<>(originalMap);
            if (container != null && container.getSkill() != null) {
                CapabilityItem capItem = container.getExecutor().getHoldingItemCapability(InteractionHand.MAIN_HAND);
                if(capItem != null) {
                    if (capItem.getWeaponCategory() == CapabilityItem.WeaponCategories.FIST) {
                        if (mutableMap != null) {
                            mutableMap.put(LivingMotions.IDLE, GenericAnimations.KATAJUTSU_IDLE);
                        }
                    }
                }
            }
            if(playerPatch.getOriginal().getOffhandItem().getItem() instanceof ArbitersShield) {
                if (mutableMap.get(LivingMotions.BLOCK_SHIELD) == null || mutableMap.get(LivingMotions.BLOCK_SHIELD).get() == Animations.EMPTY_ANIMATION || mutableMap.get(LivingMotions.BLOCK_SHIELD).get() == Animations.BIPED_BLOCK) {
                    mutableMap.put(LivingMotions.BLOCK_SHIELD, GenericAnimations.ARBITERS_SHIELD_BLOCK);
                }
            }
            else if(mutableMap.get(LivingMotions.BLOCK_SHIELD) == null || mutableMap.get(LivingMotions.BLOCK_SHIELD).get() == Animations.EMPTY_ANIMATION) {
                Log.info("Logging anim shield put!");
                mutableMap.put(LivingMotions.BLOCK_SHIELD, Animations.BIPED_BLOCK);
            }

            cir.setReturnValue(mutableMap);
        }
    }
}