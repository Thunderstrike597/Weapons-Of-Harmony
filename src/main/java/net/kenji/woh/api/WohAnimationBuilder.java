package net.kenji.woh.api;

import yesman.epicfight.api.animation.AnimationManager;
import yesman.epicfight.api.animation.types.AttackAnimation;

public class WohAnimationBuilder {

    public static AnimationManager.AnimationAccessor<? extends AttackAnimation> shotogatana(
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
                cfg.eventFirstTime, cfg.eventSecondTime, cfg.movementMultiplier
        );
    }
    public static AnimationManager.AnimationAccessor<? extends AttackAnimation> tenraiSplit(
            AnimationManager.AnimationBuilder builder,
            AnimationConfig cfg
    ) {
        return tenraiSplit(builder, WOHAnimationUtils.AttackAnimationType.BASIC_ATTACK, cfg);
    }
    public static AnimationManager.AnimationAccessor<? extends AttackAnimation> tenraiSplit(
            AnimationManager.AnimationBuilder builder,
            WOHAnimationUtils.AttackAnimationType type,
            AnimationConfig cfg
    ) {
        return WOHAnimationUtils.createTenraiSplitAttackAnimation(
                builder, type, cfg.path, cfg.phaseCount, cfg.convertTime, cfg.speed, 0, 0,
                cfg.start, cfg.antic, cfg.contact, cfg.recovery, cfg.end,
                cfg.swingSound, cfg.hitSound, cfg.hitParticle, cfg.colliders, cfg.attackingHands, cfg.stunType, cfg.movementMultiplier, cfg.eventFirstTime, cfg.eventSecondTime
        );
    }
    public static AnimationManager.AnimationAccessor<? extends AttackAnimation> generic(
            AnimationManager.AnimationBuilder builder,
            AnimationConfig cfg
    ) {
        return generic(builder, WOHAnimationUtils.AttackAnimationType.BASIC_ATTACK, cfg);
    }

    public static AnimationManager.AnimationAccessor<? extends AttackAnimation> generic(
            AnimationManager.AnimationBuilder builder,
            WOHAnimationUtils.AttackAnimationType type,
            AnimationConfig cfg
    ) {
        return WOHAnimationUtils.createAttackAnimation(
                builder, type, cfg.path, cfg.phaseCount, cfg.convertTime, cfg.speed, 0, 0,
                cfg.start, cfg.antic, cfg.contact, cfg.recovery, cfg.end,
                cfg.swingSound, cfg.hitSound, cfg.hitParticle, cfg.colliders, cfg.attackingHands, cfg.stunType, cfg.movementMultiplier,-1, -1
        );
    }

}
