package net.kenji.woh.api;

import yesman.epicfight.api.animation.AnimationManager;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.animation.types.BasicAttackAnimation;
import yesman.epicfight.gameasset.Armatures;

public class WohAnimationBuilder {

    public static AnimationManager.AnimationAccessor<? extends AttackAnimation> shotogatana(
            AnimationManager.AnimationBuilder builder,
            AnimationConfig cfg
    ) {
        return shotogatana(builder, WOHAnimationUtils.AttackAnimationType.BASIC_ATTACK, cfg);
    }
    public static AnimationManager.AnimationAccessor<? extends AttackAnimation> generic(
            AnimationManager.AnimationBuilder builder,
            AnimationConfig cfg
    ) {
        return shotogatana(builder, WOHAnimationUtils.AttackAnimationType.BASIC_ATTACK, cfg);
    }
    public static AnimationManager.AnimationAccessor<? extends AttackAnimation> shotogatana(
            AnimationManager.AnimationBuilder builder,
            WOHAnimationUtils.AttackAnimationType type,
            AnimationConfig cfg
    ) {
        return WOHAnimationUtils.createShotogatanaAttackAnimation(
                builder, type, cfg.path, cfg.phaseCount, cfg.speed, cfg.convertTime,
                cfg.start, cfg.antic, cfg.contact, cfg.recovery, cfg.end,
                cfg.swingSound, cfg.hitSound, cfg.hitParticle,
                cfg.colliders, cfg.colliderJoints, cfg.stunType,
                cfg.unsheatheTime, cfg.sheathTime
        );
    }
    public static AnimationManager.AnimationAccessor<? extends AttackAnimation> generic(
            AnimationManager.AnimationBuilder builder,
            WOHAnimationUtils.AttackAnimationType type,
            AnimationConfig cfg
    ) {
        return WOHAnimationUtils.createAttackAnimation(
                builder, type, cfg.path, cfg.phaseCount, cfg.convertTime, cfg.speed, 0, 0,
                cfg.start, cfg.antic, cfg.contact, cfg.recovery, cfg.end,
                cfg.swingSound, cfg.hitSound, cfg.hitParticle, cfg.colliders, cfg.attackingHands, cfg.stunType, -1, -1
        );
    }
}
