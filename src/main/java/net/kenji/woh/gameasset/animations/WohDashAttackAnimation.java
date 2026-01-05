package net.kenji.woh.gameasset.animations;

import net.kenji.woh.api.WOHAnimationUtils;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraftforge.registries.RegistryObject;
import yesman.epicfight.api.animation.AnimationManager;
import yesman.epicfight.api.animation.Joint;
import yesman.epicfight.api.animation.property.AnimationProperty;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.animation.types.BasicAttackAnimation;
import yesman.epicfight.api.animation.types.DashAttackAnimation;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.api.collider.Collider;
import yesman.epicfight.api.model.Armature;
import yesman.epicfight.api.utils.math.ValueModifier;
import yesman.epicfight.gameasset.Armatures;
import yesman.epicfight.gameasset.ColliderPreset;
import yesman.epicfight.model.armature.HumanoidArmature;
import yesman.epicfight.particle.HitParticleType;
import yesman.epicfight.world.damagesource.StunType;

import javax.annotation.Nullable;
import java.util.function.Supplier;

public class WohDashAttackAnimation extends BasisDashAttackAnimation {

    public static AssetAccessor<? extends HumanoidArmature> biped = Armatures.BIPED;

    public static float convertTime = 0.1f;

    public static float antic = 0.75f;
    public static float contact = 1.1f;
    public static float recovery = 1.65f;
    public static Collider collider = ColliderPreset.TACHI;
    public static Joint colliderJoint = biped.get().toolR;

    public static float damage = 4.0F;
    public static float impact = 5.0F;
    public static float basisAttackSpeed = 3.0F;

    public WohDashAttackAnimation(
            float convertTime,
            AnimationManager.AnimationAccessor<? extends DashAttackAnimation>  accessor,  // ADD THIS!
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
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, basisAttackSpeed);
        if(attackType == WOHAnimationUtils.AttackAnimationType.DASH_ATTACK_JUMP)
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