package net.kenji.woh.mixins;

import com.mojang.blaze3d.platform.InputConstants;
import net.kenji.woh.api.WOHAnimationUtils;
import net.kenji.woh.api.animation_types.ShotogatanaAttackAnimation;
import net.kenji.woh.api.interfaces.IPhase;
import net.kenji.woh.api.manager.AttackManager;
import net.kenji.woh.api.manager.ShotogatanaManager;
import net.kenji.woh.gameasset.WohSkills;
import net.kenji.woh.item.custom.weapon.Odachi;
import net.kenji.woh.item.custom.weapon.Shotogatana;
import net.kenji.woh.registry.WohSounds;
import net.kenji.woh.registry.animation.ShotogatanaAnimations;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.sounds.SoundEvent;
import org.jline.utils.Log;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import yesman.epicfight.api.animation.AnimationPlayer;
import yesman.epicfight.api.animation.property.AnimationProperty;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.animation.types.DynamicAnimation;
import yesman.epicfight.api.animation.types.EntityState;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.client.events.engine.ControlEngine;
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
                if (playerPatch.getSkill(WohSkills.SHOTOGATANA_SKILL) == null || !playerPatch.getSkill(WohSkills.SHOTOGATANA_SKILL).isActivated()) {
                    if (!ShotogatanaManager.renderSheathMap.getOrDefault(playerPatch.getOriginal().getUUID(), false)) {
                        if (isEnd || !(playerPatch.getAnimator().getPlayerFor(null).getAnimation().get() instanceof AttackAnimation)) {
                            ShotogatanaManager.renderSheathMap.put(playerPatch.getOriginal().getUUID(), true);
                        }
                    }
                }
            }
            if (playerPatch.getOriginal().level().isClientSide()) {
                Minecraft mc = Minecraft.getInstance();
                resyncMovementKeys(mc);
            }
            WOHAnimationUtils.regainMovementEvent(entitypatch);
        }
    }

    @Inject(method = "getSwingSound", at = @At("HEAD"), cancellable = true)
    public void getSwingSound(LivingEntityPatch<?> entitypatch, AttackAnimation.Phase phase, CallbackInfoReturnable<SoundEvent> cir) {
        AttackAnimation attackAnimation = (AttackAnimation) (Object) this;

        if (entitypatch instanceof PlayerPatch<?> playerPatch) {
            if (playerPatch.getOriginal().getMainHandItem().getItem() instanceof Shotogatana) {
                if (playerPatch.getSkill(WohSkills.SHOTOGATANA_SKILL) == null || !playerPatch.getSkill(WohSkills.SHOTOGATANA_SKILL).isActivated()) {
                    // if (!(attackAnimation instanceof ShotogatanaAttackAnimation))
                    //  cir.setReturnValue(WohSounds.SHOTOGATANA_SWING.get());
                }
            }
            if (playerPatch.getOriginal().getMainHandItem().getItem() instanceof Odachi) {
                cir.setReturnValue(WohSounds.ODACHI_SWING.get());
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
           /* if (time > 0.05F) {
                if (time < phase.recovery) {
                    AttackManager.isInAttack.put(playerId, true);
                    AttackManager.isInAttackForCombo.getOrDefault(playerId, true);
                }
            }if (time > (phase.recovery + phase.end) * 0.1F) {
                AttackManager.isInAttackForCombo.remove(playerId);
                if (time > (phase.recovery + phase.end) * 0.65F)
                    AttackManager.isInAttack.remove(playerId);
            }*/
            if (time > phase.recovery && time < (phase.recovery + phase.end) * 0.2) {
                AnimationPlayer animationPlayer = playerPatch.getAnimator().getPlayerFor(attackAnimation.getAccessor());
                attackAnimation.addProperty(
                        AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER,
                        (dynamicAnim, entityPatch, originalSpeed, prevTime, currentTime) -> {
                            // Slow down during recovery phase
                            if (currentTime > (phase.contact + phase.recovery) * 0.65 && currentTime < (phase.recovery + phase.end) * 0.15) {
                                return originalSpeed * 0.8f; // Half speed during this window
                            }
                            return originalSpeed; // Normal speed otherwise
                        }
                );
            }

        }
    }

    @Inject(method = "tick", at = @At("HEAD"))
    public void onTick(LivingEntityPatch<?> entitypatch, CallbackInfo ci) {
        AttackAnimation attackAnimation = (AttackAnimation) (Object) this;
        if (entitypatch instanceof PlayerPatch<?> playerPatch) {
            AttackAnimation.Phase phase = attackAnimation.phases[attackAnimation.phases.length - 1];

            IPhase iPhase = (IPhase) (Object) phase;
            if (!iPhase.woh$isRecoveryOverridden()) {
                if (playerPatch.getOriginal().getMainHandItem().getItem() instanceof Shotogatana) {
                    float fixedRecovery = phase.contact + (phase.end - phase.contact) * 0.5f;
                    // For your example: 0.78 + (0.88 - 0.78) * 0.5 = 0.83
                    Log.info("contact=" + phase.contact + " end=" + phase.end + " fixedRecovery=" + fixedRecovery);
                    iPhase.woh$setRecoveryOverride(fixedRecovery);
                }
            }
        }
    }
    @Unique
    private static void resyncMovementKeys(Minecraft mc) {
        long window = mc.getWindow().getWindow();

        resync(mc.options.keyUp, window);
        resync(mc.options.keyDown, window);
        resync(mc.options.keyLeft, window);
        resync(mc.options.keyRight, window);
    }
    @Unique
    private static void resync(KeyMapping key, long window) {
        InputConstants.Key input = key.getKey();
        boolean physicallyDown = InputConstants.isKeyDown(window, input.getValue());

        key.setDown(physicallyDown);
    }
}

