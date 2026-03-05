package net.kenji.woh.api.animation_types;

import net.kenji.woh.api.manager.AttackManager;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraftforge.registries.RegistryObject;
import org.jline.utils.Log;
import reascer.wom.gameasset.WOMAnimations;
import yesman.epicfight.api.animation.Joint;
import yesman.epicfight.api.animation.property.AnimationProperty;
import yesman.epicfight.api.animation.property.MoveCoordFunctions;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.animation.types.DashAttackAnimation;
import yesman.epicfight.api.animation.types.DynamicAnimation;
import yesman.epicfight.api.collider.Collider;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.gameasset.Armatures;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.particle.HitParticleType;
import yesman.epicfight.skill.weaponinnate.BattojutsuSkill;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.damagesource.StunType;

public class ShotogatanaDashAttackAnimation extends DashAttackAnimation {

    public final float unsheatheTime;
    public final float sheathTime;

    public ShotogatanaDashAttackAnimation(float convertTime, String path, float unsheatheTime, float sheathTime, int phaseCount, float[] start , float[] antic, float[] contact, float[] recovery, float[] end, SoundEvent[] swingSound, SoundEvent[] hitSound, RegistryObject<HitParticleType>[] hitParticle, StunType stunType, Collider[] colliders, Joint[] colliderJoints, boolean ignoreFallDamage) {
        super(convertTime, path, Armatures.BIPED, buildPhases(phaseCount, start ,antic, contact, recovery, end, swingSound, hitSound, hitParticle, colliders, colliderJoints));
        this.unsheatheTime = unsheatheTime;
        this.sheathTime = sheathTime;
        this.addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, stunType)
                .addProperty(AnimationProperty.AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.175F)
                .addProperty(AnimationProperty.ActionAnimationProperty.MOVE_VERTICAL, true)
                .addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, false)
                .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, true)
                .addProperty(AnimationProperty.ActionAnimationProperty.AFFECT_SPEED, false)
                .addProperty(AnimationProperty.ActionAnimationProperty.COORD_SET_BEGIN, MoveCoordFunctions.RAW_COORD).addProperty(AnimationProperty.ActionAnimationProperty.COORD_SET_TICK, null);
    }

    @Override
    protected boolean shouldMove(float currentTime) {
        return true;
    }

    private static Phase[] buildPhases(int phaseCount, float[] start , float[] antic, float[] contact, float[] recovery, float[] end, SoundEvent[] swingSound, SoundEvent[] hitSound, RegistryObject<HitParticleType>[] hitParticle, Collider[] colliders, Joint[] colliderJoints) {
        Phase[] phases = new Phase[phaseCount];

        for(int i = 0; i < phaseCount; i++) {
            phases[i] = new Phase(
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

    @Override
    public void tick(LivingEntityPatch<?> entitypatch) {
        super.tick(entitypatch);
        float time = entitypatch.getAnimator().getPlayerFor(this).getElapsedTime();


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
