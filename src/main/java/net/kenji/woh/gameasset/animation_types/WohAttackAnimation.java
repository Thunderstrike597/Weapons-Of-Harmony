package net.kenji.woh.gameasset.animation_types;

import net.kenji.woh.api.WOHAnimationUtils;
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
import yesman.epicfight.api.utils.math.ValueModifier;
import yesman.epicfight.gameasset.Armatures;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.model.armature.HumanoidArmature;
import yesman.epicfight.particle.EpicFightParticles;
import yesman.epicfight.particle.HitParticleType;
import yesman.epicfight.world.damagesource.StunType;

import javax.annotation.Nullable;
import java.util.function.Supplier;

public class WohAttackAnimation extends BasisAttackAnimation {

    public static AssetAccessor<? extends HumanoidArmature> biped = Armatures.BIPED;

    public static float convertTime = 0.1f;

    public static float antic = 0.75f;
    public static float contact = 1.1f;
    public static float recovery = 1.65f;

    public static float basisAttackSpeed = 3.0F;

    public SoundEvent hitSound = EpicFightSounds.BLADE_HIT.get();
    public SoundEvent swingSound = EpicFightSounds.WHOOSH.get();
    public RegistryObject<HitParticleType> hitParticle = EpicFightParticles.HIT_BLADE;

    public WohAttackAnimation(
            float convertTime,
            AnimationManager.AnimationAccessor<? extends BasicAttackAnimation>  accessor,  // ADD THIS!
            WOHAnimationUtils.AttackAnimationType attackType,
            @Nullable AnimationManager.AnimationAccessor<StaticAnimation> endAnimation,
            int phaseCount,
            float attackSpeed,
            float damage,
            float impact,
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
            Joint[] colliderJoints,
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
                buildPhases(phaseCount, start, antic, contact, recovery, end, swingSound, hitSound, hitParticle, colliders, colliderJoints)
        );

        this.addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, stunType)
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.setter(damage))
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(impact))
                .addProperty(AnimationProperty.AttackAnimationProperty.ATTACK_SPEED_FACTOR, attackSpeed)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, basisAttackSpeed)
                .addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, true)
                .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, false)
                .addProperty(AnimationProperty.ActionAnimationProperty.AFFECT_SPEED, false);
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
            float damage,
            float impact,
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
            Joint[] colliderJoints,
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
                buildPhases(phaseCount, start, antic, contact, recovery, end, swingSound, hitSound, hitParticle, colliders, colliderJoints)
        );

        this.addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, stunType)
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.setter(damage))
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(impact))
                .addProperty(AnimationProperty.AttackAnimationProperty.ATTACK_SPEED_FACTOR, attackSpeed)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, basisAttackSpeed)
                .addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, true)
                .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, false)
                .addProperty(AnimationProperty.ActionAnimationProperty.AFFECT_SPEED, false);
        if(attackType == WOHAnimationUtils.AttackAnimationType.BASIC_ATTACK_JUMP)
            this.addProperty(AnimationProperty.ActionAnimationProperty.MOVE_VERTICAL, true);
        else this.addProperty(AnimationProperty.ActionAnimationProperty.MOVE_VERTICAL, false);


    }
    private static AttackAnimation.Phase[] buildPhases(int phaseCount,  float[] start ,float[] antic, float[] contact, float[] recovery, float[] end, Supplier<SoundEvent>[] swingSound, Supplier<SoundEvent>[] hitSound, RegistryObject<HitParticleType>[] hitParticle, Collider[] colliders, Joint[] colliderJoints) {
        AttackAnimation.Phase[] phases = new AttackAnimation.Phase[phaseCount];

        for(int i = 0; i < phaseCount; i++) {
            phases[i] = new AttackAnimation.Phase(
                    start[i],
                    antic[i],
                    contact[i],
                    recovery[i],
                    end[i],
                    InteractionHand.MAIN_HAND,
                    colliderJoints[i],
                    colliders[i]
            ).addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, hitSound[i].get())
                    .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, swingSound[i].get())
                    .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, hitParticle[i]);
        }

        return phases;
    }



}
