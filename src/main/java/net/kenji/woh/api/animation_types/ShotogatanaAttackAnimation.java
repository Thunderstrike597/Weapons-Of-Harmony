package net.kenji.woh.api.animation_types;

import net.corruptdog.cdm.skill.weaponinnate.YamatoAttack;
import net.kenji.woh.api.manager.AttackManager;
import net.kenji.woh.registry.animation.ShotogatanaAnimations;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraftforge.registries.RegistryObject;
import org.jline.utils.Log;
import yesman.epicfight.api.animation.Joint;
import yesman.epicfight.api.animation.property.AnimationProperty;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.animation.types.DynamicAnimation;
import yesman.epicfight.api.client.animation.Layer;
import yesman.epicfight.api.collider.Collider;
import yesman.epicfight.client.world.capabilites.entitypatch.player.LocalPlayerPatch;
import yesman.epicfight.gameasset.Armatures;
import yesman.epicfight.particle.HitParticleType;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.damagesource.StunType;

public class ShotogatanaAttackAnimation extends AttackAnimation {

    public final float unsheatheTime;
    public final float sheathTime;

    public ShotogatanaAttackAnimation(float convertTime, String path, float unsheatheTime, float sheathTime, int phaseCount, float[] start , float[] antic, float[] contact, float[] recovery, float[] end, SoundEvent[] swingSound, SoundEvent[] hitSound, RegistryObject<HitParticleType>[] hitParticle, StunType stunType, Collider[] colliders, Joint[] colliderJoints, boolean ignoreFallDamage) {
        super(convertTime, path, Armatures.BIPED, buildPhases(phaseCount, start ,antic, contact, recovery, end, swingSound, hitSound, hitParticle, colliders, colliderJoints));
        this.unsheatheTime = unsheatheTime;
        this.sheathTime = sheathTime;
        this.addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, stunType)
                .addProperty(AnimationProperty.AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.175F)
                .addProperty(AnimationProperty.ActionAnimationProperty.MOVE_VERTICAL, true)
                .addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, true)
                .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, false)
                .addProperty(AnimationProperty.ActionAnimationProperty.AFFECT_SPEED, false);
    }

    private static AttackAnimation.Phase[] buildPhases(int phaseCount, float[] start , float[] antic, float[] contact, float[] recovery, float[] end, SoundEvent[] swingSound, SoundEvent[] hitSound, RegistryObject<HitParticleType>[] hitParticle, Collider[] colliders, Joint[] colliderJoints) {
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

            Log.info("Swing Sound: " + swingSound[i].toString());
        }


        return phases;
    }

    @Override
    public void begin(LivingEntityPatch<?> entitypatch) {
        if(entitypatch instanceof PlayerPatch<?> playerPatch) {
            AttackManager.isInAttack.put(playerPatch.getOriginal().getUUID(), true);
        }
        super.begin(entitypatch);
    }

    @Override
    public void end(LivingEntityPatch<?> entitypatch, DynamicAnimation nextAnimation, boolean isEnd) {
        if(entitypatch instanceof PlayerPatch<?> playerPatch) {
            AttackManager.isInAttack.remove(playerPatch.getOriginal().getUUID());
        }
        super.end(entitypatch, nextAnimation, isEnd);
    }
}
