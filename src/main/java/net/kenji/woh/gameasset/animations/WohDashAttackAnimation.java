package net.kenji.woh.gameasset.animations;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraftforge.registries.RegistryObject;
import yesman.epicfight.api.animation.Joint;
import yesman.epicfight.api.animation.property.AnimationProperty;
import yesman.epicfight.api.collider.Collider;
import yesman.epicfight.api.utils.math.ValueModifier;
import yesman.epicfight.gameasset.Armatures;
import yesman.epicfight.gameasset.ColliderPreset;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.model.armature.HumanoidArmature;
import yesman.epicfight.particle.EpicFightParticles;
import yesman.epicfight.particle.HitParticleType;
import yesman.epicfight.world.damagesource.StunType;

public class WohDashAttackAnimation extends BasisDashAttackAnimation {

    public static HumanoidArmature biped = Armatures.BIPED;

    public static float convertTime = 0.1f;

    public static float antic = 0.75f;
    public static float contact = 1.1f;
    public static float recovery = 1.65f;
    public static Collider collider = ColliderPreset.TACHI;
    public static Joint colliderJoint = biped.toolR;

    public static float damage = 4.0F;
    public static float impact = 5.0F;
    public static float basisAttackSpeed = 3.0F;

    public SoundEvent hitSound = EpicFightSounds.BLADE_HIT.get();
    public SoundEvent swingSound = EpicFightSounds.WHOOSH.get();
    public RegistryObject<HitParticleType> hitParticle = EpicFightParticles.HIT_BLADE;

    public WohDashAttackAnimation(String path, int phaseCount, float convertTime, float attackSpeed, float[] start , float[] antic, float[] contact, float[] recovery, float[] end, SoundEvent[] swingSound, SoundEvent[] hitSound, RegistryObject<HitParticleType>[] hitParticle, StunType stunType) {
        super(convertTime, path, biped, buildPhases(phaseCount, start ,antic, contact, recovery, end, hitSound, swingSound, hitParticle));
        this.addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, stunType)
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.setter(damage))
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(impact))
                .addProperty(AnimationProperty.AttackAnimationProperty.ATTACK_SPEED_FACTOR, attackSpeed)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, basisAttackSpeed)
                .addProperty(AnimationProperty.ActionAnimationProperty.MOVE_VERTICAL, true);
    }

    private static Phase[] buildPhases(int phaseCount,  float[] start ,float[] antic, float[] contact, float[] recovery, float[] end, SoundEvent[] hitSound, SoundEvent[] swingSound, RegistryObject<HitParticleType>[] hitParticle) {
        Phase[] phases = new Phase[phaseCount];

        for(int i = 0; i < phaseCount; i++) {
            phases[i] = new Phase(
                    start[i],
                    antic[i],
                    contact[i],
                    recovery[i],
                    end[i],
                    InteractionHand.MAIN_HAND,
                    colliderJoint,
                    collider
            ).addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, hitSound[i])
                    .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, swingSound[i])
                    .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, hitParticle[i]);
        }

        return phases;
    }



}
