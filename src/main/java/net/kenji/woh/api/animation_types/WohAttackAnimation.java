package net.kenji.woh.api.animation_types;

import net.kenji.woh.api.WOHAnimationUtils;
import net.kenji.woh.gameasset.AttackHand;
import net.kenji.woh.gameasset.animation_types.BasisAttackAnimation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraftforge.registries.RegistryObject;
import yesman.epicfight.api.animation.AnimationManager;
import yesman.epicfight.api.animation.Joint;
import yesman.epicfight.api.animation.property.AnimationProperty;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.animation.types.BasicAttackAnimation;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.api.collider.Collider;
import yesman.epicfight.gameasset.Armatures;
import yesman.epicfight.model.armature.HumanoidArmature;
import yesman.epicfight.particle.HitParticleType;
import yesman.epicfight.world.damagesource.StunType;

import javax.annotation.Nullable;
import java.util.function.Supplier;

public class WohAttackAnimation extends BasisAttackAnimation {

    public static AssetAccessor<? extends HumanoidArmature> biped = Armatures.BIPED;

    public static float convertTime = 0.1f;


    public WohAttackAnimation(
            float convertTime,
            AnimationManager.AnimationAccessor<? extends BasicAttackAnimation>  accessor,  // ADD THIS!
            WOHAnimationUtils.AttackAnimationType attackType,
            @Nullable AnimationManager.AnimationAccessor<StaticAnimation> endAnimation,
            int phaseCount,
            float attackSpeed,
            float[] start,
            float[] antic,
            float[] contact,
            float[] recovery,
            float[] end,
            Supplier<SoundEvent>[] swingSound,
            Supplier<SoundEvent>[] hitSound,
            RegistryObject<HitParticleType>[] hitParticle,
            StunType stunType,
            Collider[] colliders,
            AttackHand[] attackHand,
            boolean ignoreFallDamage
    ) {
        // Pass convertTime, path (from accessor), accessor, endAnimation, ignoreFallDamage, phases
        super(
                attackType,
                convertTime,
                accessor,            // Pass accessor to parent
                biped,
                endAnimation,
                ignoreFallDamage,
                buildPhases(phaseCount, start, antic, contact, recovery, end, swingSound, hitSound, hitParticle, colliders, attackHand)
        );

        this.addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, stunType)
                .addProperty(AnimationProperty.AttackAnimationProperty.ATTACK_SPEED_FACTOR, attackSpeed)
                .addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, true)
                .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, false)
                .addProperty(AnimationProperty.ActionAnimationProperty.AFFECT_SPEED, true);
        if(attackType == WOHAnimationUtils.AttackAnimationType.BASIC_ATTACK_JUMP)
             this.addProperty(AnimationProperty.ActionAnimationProperty.MOVE_VERTICAL, true);
        else this.addProperty(AnimationProperty.ActionAnimationProperty.MOVE_VERTICAL, false);


    }
    public WohAttackAnimation(
            float convertTime,
            AnimationManager.AnimationAccessor<? extends BasicAttackAnimation>  accessor,  // ADD THIS!
            WOHAnimationUtils.AttackAnimationType attackType,
            @Nullable AnimationManager.AnimationAccessor<StaticAnimation> endAnimation,
            int phaseCount,
            float attackSpeed,
            float[] start,
            float[] antic,
            float[] contact,
            float[] recovery,
            float[] end,
            Supplier<SoundEvent>[] swingSound,
            Supplier<SoundEvent>[] hitSound,
            RegistryObject<HitParticleType>[] hitParticle,
            StunType stunType,
            Collider[] colliders,
            AttackHand[] attackHand,
            boolean ignoreFallDamage,
            float slashAngle,
            float movementEnd
    ) {
        // Pass convertTime, path (from accessor), accessor, endAnimation, ignoreFallDamage, phases
        super(
                attackType,
                convertTime,
                accessor,            // Pass accessor to parent
                biped,
                endAnimation,
                slashAngle,
                movementEnd,
                buildPhases(phaseCount, start, antic, contact, recovery, end, swingSound, hitSound, hitParticle, colliders, attackHand)
        );

        this.addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, stunType).addProperty(AnimationProperty.AttackAnimationProperty.ATTACK_SPEED_FACTOR, attackSpeed)
                .addProperty(AnimationProperty.AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.175F)
                .addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, true)
                .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, false)
                .addProperty(AnimationProperty.ActionAnimationProperty.AFFECT_SPEED, false);
        if(attackType == WOHAnimationUtils.AttackAnimationType.BASIC_ATTACK_JUMP)
            this.addProperty(AnimationProperty.ActionAnimationProperty.MOVE_VERTICAL, true);
        else this.addProperty(AnimationProperty.ActionAnimationProperty.MOVE_VERTICAL, false);


    }
    private static AttackAnimation.Phase[] buildPhases(int phaseCount, float[] start ,float[] antic, float[] contact, float[] recovery, float[] end, Supplier<SoundEvent>[] swingSound, Supplier<SoundEvent>[] hitSound, RegistryObject<HitParticleType>[] hitParticle, Collider[] colliders, AttackHand[] attackHand) {
        AttackAnimation.Phase[] phases = new AttackAnimation.Phase[phaseCount];

        for(int i = 0; i < phaseCount; i++) {
            AttackHand hand = attackHand[0];
            if(i < attackHand.length)
                hand = attackHand[i];
            else hand = attackHand[attackHand.length - 1];
            Collider collider = colliders[0];
            if(i < colliders.length)
                collider = colliders[i];
            else collider = colliders[attackHand.length - 1];

            phases[i] = new AttackAnimation.Phase(
                    start[i],
                    antic[i],
                    antic[i],
                    contact[i],
                    recovery[i],
                    end[i],
                    InteractionHand.MAIN_HAND,
                    getThrownHand(hand, collider)
            );
            if(i < hitSound.length)
                phases[i].addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, hitSound[i].get());
            else if(hitSound.length > 0)
                phases[i].addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, hitSound[hitSound.length - 1].get());
            if(i < swingSound.length)
                phases[i].addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, swingSound[i].get());
            else if(hitSound.length > 0)
                phases[i].addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, swingSound[swingSound.length - 1].get());
            if(i < hitParticle.length)
                phases[i].addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, hitParticle[i]);
            else if(hitParticle.length > 0)
                phases[i].addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, hitParticle[hitParticle.length - 1]);
        }

        return phases;
    }

    private static JointColliderPair[] getThrownHand(AttackHand throwType, Collider collider){
        JointColliderPair[] pair = null;
        if(throwType == AttackHand.RIGHT_HAND) return new AttackAnimation.JointColliderPair[]{JointColliderPair.of(((HumanoidArmature)Armatures.BIPED.get()).toolR, (Collider)collider)};
        if(throwType == AttackHand.LEFT_HAND) return new AttackAnimation.JointColliderPair[]{JointColliderPair.of(((HumanoidArmature)Armatures.BIPED.get()).toolL, (Collider)collider)};
        if(throwType == AttackHand.HANDS){
            return new AttackAnimation.JointColliderPair[]{JointColliderPair.of(((HumanoidArmature)Armatures.BIPED.get()).toolR, (Collider)collider), JointColliderPair.of(((HumanoidArmature)Armatures.BIPED.get()).toolL, (Collider)collider)};
        }
        if(throwType == AttackHand.TORSO){
            return new AttackAnimation.JointColliderPair[]{JointColliderPair.of(((HumanoidArmature)Armatures.BIPED.get()).torso, (Collider)collider)};
        }
        if(throwType == AttackHand.RIGHT_LEG){
            return new AttackAnimation.JointColliderPair[]{JointColliderPair.of(((HumanoidArmature)Armatures.BIPED.get()).legR, (Collider)collider)};
        }
        if(throwType == AttackHand.LEFT_LEG){
            return new AttackAnimation.JointColliderPair[]{JointColliderPair.of(((HumanoidArmature)Armatures.BIPED.get()).legL, (Collider)collider)};
        }
        return new AttackAnimation.JointColliderPair[]{JointColliderPair.of(((HumanoidArmature)Armatures.BIPED.get()).toolR, (Collider)collider)};
    }

}
