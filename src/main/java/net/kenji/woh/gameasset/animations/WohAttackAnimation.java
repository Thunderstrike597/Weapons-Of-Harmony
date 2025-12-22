package net.kenji.woh.gameasset.animations;

import net.kenji.woh.api.WOHAnimationUtils;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraftforge.registries.RegistryObject;
import yesman.epicfight.api.animation.Joint;
import yesman.epicfight.api.animation.property.AnimationProperty;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.collider.Collider;
import yesman.epicfight.api.utils.math.ValueModifier;
import yesman.epicfight.gameasset.Armatures;
import yesman.epicfight.gameasset.ColliderPreset;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.model.armature.HumanoidArmature;
import yesman.epicfight.particle.EpicFightParticles;
import yesman.epicfight.particle.HitParticleType;
import yesman.epicfight.world.damagesource.StunType;

import javax.annotation.Nullable;

public class WohAttackAnimation extends BasisAttackAnimation {

    public static HumanoidArmature biped = Armatures.BIPED;

    public static float convertTime = 0.1f;

    public static float antic = 0.75f;
    public static float contact = 1.1f;
    public static float recovery = 1.65f;

    public static float basisAttackSpeed = 3.0F;

    public SoundEvent hitSound = EpicFightSounds.BLADE_HIT.get();
    public SoundEvent swingSound = EpicFightSounds.WHOOSH.get();
    public RegistryObject<HitParticleType> hitParticle = EpicFightParticles.HIT_BLADE;

    public WohAttackAnimation(WOHAnimationUtils.AttackAnimationType attackType, String path, @Nullable StaticAnimation endAnimation, int phaseCount, float convertTime, float attackSpeed, float damage, float impact, float[] start , float[] antic, float[] contact, float[] recovery, float[] end, SoundEvent[] swingSound, SoundEvent[] hitSound, RegistryObject<HitParticleType>[] hitParticle, StunType stunType, Collider[] colliders, Joint[] colliderJoints, boolean ignoreFallDamage) {
        super(attackType, convertTime, path, biped, endAnimation, ignoreFallDamage, buildPhases(phaseCount, start ,antic, contact, recovery, end, hitSound, swingSound, hitParticle, colliders, colliderJoints));
        this.addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, stunType)
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.setter(damage))
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(impact))
                .addProperty(AnimationProperty.AttackAnimationProperty.ATTACK_SPEED_FACTOR, attackSpeed)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, basisAttackSpeed)
                .addProperty(AnimationProperty.ActionAnimationProperty.MOVE_VERTICAL, true)
         .addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, true)
                .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, false)
                .addProperty(AnimationProperty.ActionAnimationProperty.AFFECT_SPEED, false);
    }

    private static AttackAnimation.Phase[] buildPhases(int phaseCount,  float[] start ,float[] antic, float[] contact, float[] recovery, float[] end, SoundEvent[] hitSound, SoundEvent[] swingSound, RegistryObject<HitParticleType>[] hitParticle, Collider[] colliders, Joint[] colliderJoints) {
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
            ).addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, hitSound[i])
                    .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, swingSound[i])
                    .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, hitParticle[i]);
        }

        return phases;
    }



}
