package net.kenji.woh.mixins;

import net.kenji.woh.api.manager.AttackManager;
import net.kenji.woh.api.manager.ShotogatanaManager;
import net.kenji.woh.gameasset.WohSkills;
import net.kenji.woh.item.custom.weapon.Shotogatana;
import net.kenji.woh.registry.animation.ShotogatanaAnimations;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import yesman.epicfight.api.animation.AnimationPlayer;
import yesman.epicfight.api.animation.property.AnimationProperty;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.animation.types.DynamicAnimation;
import yesman.epicfight.api.animation.types.EntityState;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;

import java.util.UUID;

@Mixin(value = AttackAnimation.class, remap = false)
public class AttackAnimationMixin {

    @Inject(method = "end", at = @At("HEAD"))
    public void onEndAttack(LivingEntityPatch<?> entitypatch, AssetAccessor<? extends DynamicAnimation> nextAnimation, boolean isEnd, CallbackInfo ci) {
        AttackAnimation attackAnimation = (AttackAnimation) (Object) this;
        if (entitypatch instanceof PlayerPatch<?> playerPatch) {
            AttackManager.isInAttack.remove(playerPatch.getOriginal().getUUID());
            if (playerPatch.getOriginal().getMainHandItem().getItem() instanceof Shotogatana) {
                ShotogatanaManager.queSheathCounter.put(playerPatch.getOriginal().getUUID(), 20);
                if (playerPatch.getSkill(WohSkills.SHEATH_STANCE) == null || !playerPatch.getSkill(WohSkills.SHEATH_STANCE).isActivated()) {
                    if (!ShotogatanaManager.sheathWeapon.getOrDefault(playerPatch.getOriginal().getUUID(), false)) {
                        if (nextAnimation == null || (isEnd & nextAnimation != ShotogatanaAnimations.SHOTOGATANA_SHEATH && !(nextAnimation instanceof AttackAnimation))) {
                            playerPatch.playAnimationSynchronized(ShotogatanaAnimations.SHOTOGATANA_SHEATH, 0.3f);
                        }
                    }
                }
            }
            if (playerPatch.getOriginal().level().isClientSide()) {
                Minecraft mc = Minecraft.getInstance();
                AttackManager.resyncMovementKeys(mc);
            }
        }
    }

    @Inject(method = "attackTick", at = @At("HEAD"))
    public void onAttackTick(LivingEntityPatch<?> entitypatch, AssetAccessor<? extends DynamicAnimation> animation, CallbackInfo ci) {
        AttackAnimation attackAnimation = (AttackAnimation) (Object) this;
        if (entitypatch instanceof PlayerPatch<?> playerPatch) {
            float time = playerPatch.getAnimator().getPlayerFor(attackAnimation.getAccessor()).getElapsedTime();
            UUID playerId = playerPatch.getOriginal().getUUID();
            AttackAnimation.Phase phase = attackAnimation.phases[attackAnimation.phases.length - 1];
            if (time > 0.05F) {
                if (time < phase.recovery) {
                    AttackManager.isInAttack.put(playerId, true);
                    AttackManager.isInAttackForCombo.getOrDefault(playerId, true);
                }
            }if (time > (phase.recovery + phase.end) * 0.1F) {
                AttackManager.isInAttackForCombo.remove(playerId);
                if (time > (phase.recovery + phase.end) * 0.65F)
                    AttackManager.isInAttack.remove(playerId);
            }
          /*  if(time > phase.recovery && time < (phase.recovery + phase.end) * 0.2){
                AnimationPlayer animationPlayer = playerPatch.getAnimator().getPlayerFor(attackAnimation.getAccessor());
                attackAnimation.addProperty(
                        AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER,
                        (dynamicAnim, entityPatch, originalSpeed, prevTime, currentTime) -> {
                            // Slow down during recovery phase
                            if(currentTime > (phase.contact + phase.recovery) * 0.65 && currentTime < (phase.recovery + phase.end) * 0.15) {
                                return originalSpeed * 0.8f; // Half speed during this window
                            }
                            return originalSpeed; // Normal speed otherwise
                        }
                );
            }*/
            StaticAnimation staticAnimation = playerPatch.getAnimator().getLivingAnimation(playerPatch.getCurrentLivingMotion(), Animations.BIPED_IDLE).get();

            DynamicAnimationAccessor dynamicAnimationMixin = (DynamicAnimationAccessor)(Object)staticAnimation;
            if(dynamicAnimationMixin.woh$getConvertTime() < 0.4) {
                dynamicAnimationMixin.woh$setConvertTime(0.28F);
            }
        }
    }

    @Inject(method = "tick", at = @At("HEAD"))
    public void onTick(LivingEntityPatch<?> entitypatch, CallbackInfo ci) {
        if (entitypatch instanceof PlayerPatch<?> playerPatch) {
            if (AttackManager.isInAttackForCombo.getOrDefault(playerPatch.getOriginal().getUUID(), false)) {
                playerPatch.getEntityState().setState(EntityState.CAN_BASIC_ATTACK, false);
            }
        }
    }
}

