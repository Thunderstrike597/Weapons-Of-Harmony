package net.kenji.woh.gameasset.animations;

import net.kenji.woh.api.WOHAnimationUtils;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraftforge.registries.RegistryObject;
import yesman.epicfight.api.animation.AnimationManager;
import yesman.epicfight.api.animation.Joint;
import yesman.epicfight.api.animation.property.AnimationProperty;
import yesman.epicfight.api.animation.types.AirSlashAnimation;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.api.collider.Collider;
import yesman.epicfight.api.model.Armature;
import yesman.epicfight.api.utils.TimePairList;
import yesman.epicfight.api.utils.math.ValueModifier;
import yesman.epicfight.gameasset.Armatures;
import yesman.epicfight.gameasset.ColliderPreset;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.model.armature.HumanoidArmature;
import yesman.epicfight.particle.EpicFightParticles;
import yesman.epicfight.particle.HitParticleType;
import yesman.epicfight.world.damagesource.StunType;

import java.util.function.Supplier;

public class WohAirAttackAnimation extends BasisAirAttackAnimation {

    public static AssetAccessor<? extends HumanoidArmature> biped = Armatures.BIPED;

    public static Joint colliderJoint = biped.get().toolR;
    public static float basisAttackSpeed = 3.0F;

    public SoundEvent hitSound = EpicFightSounds.BLADE_HIT.get();
    public SoundEvent swingSound = EpicFightSounds.WHOOSH.get();
    public RegistryObject<HitParticleType> hitParticle = EpicFightParticles.HIT_BLADE;

    public WohAirAttackAnimation(int phaseCount, float convertTime, AnimationManager.AnimationAccessor<? extends AirSlashAnimation> accessor, float attackSpeed, float damage, float impact, float[] start , float[] antic, float[] contact, float[] recovery, float[] end, Supplier<SoundEvent>[] swingSound, Supplier<SoundEvent>[]  hitSound, RegistryObject<HitParticleType>[] hitParticle, StunType stunType, Collider[] colliders, Joint[] joints, float[] airTime) {
        super(convertTime, accessor, biped, buildPhases(phaseCount, start ,antic, contact, recovery, end, hitSound, swingSound, hitParticle, colliders, joints));
        this.addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, stunType)
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.setter(damage))
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(impact))
                .addProperty(AnimationProperty.AttackAnimationProperty.ATTACK_SPEED_FACTOR, attackSpeed)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, basisAttackSpeed)
                .addProperty(AnimationProperty.ActionAnimationProperty.MOVE_VERTICAL, true)
                .addProperty(AnimationProperty.ActionAnimationProperty.NO_GRAVITY_TIME, TimePairList.create(airTime));
    }

    private static Phase[] buildPhases(int phaseCount,  float[] start ,float[] antic, float[] contact, float[] recovery, float[] end,  Supplier<SoundEvent>[] hitSound,  Supplier<SoundEvent>[]  swingSound, RegistryObject<HitParticleType>[] hitParticle, Collider[] colliders, Joint[] joints) {
        Phase[] phases = new Phase[phaseCount];

        for(int i = 0; i < phaseCount; i++) {
            phases[i] = new Phase(
                    start[i],
                    antic[i],
                    contact[i],
                    recovery[i],
                    end[i],
                    InteractionHand.MAIN_HAND,
                    joints[i],
                    colliders[i]
            ).addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, hitSound[i].get())
                    .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, swingSound[i].get())
                    .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, hitParticle[i]);
        }

        return phases;
    }



}
