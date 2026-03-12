package net.kenji.woh.api.animation_types;

import net.kenji.woh.api.manager.AttackManager;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraftforge.registries.RegistryObject;
import org.jline.utils.Log;
import yesman.epicfight.api.animation.AnimationManager;
import yesman.epicfight.api.animation.Joint;
import yesman.epicfight.api.animation.property.AnimationProperty;
import yesman.epicfight.api.animation.types.BasicAttackAnimation;
import yesman.epicfight.api.animation.types.DynamicAnimation;
import yesman.epicfight.api.collider.Collider;
import yesman.epicfight.api.utils.TimePairList;
import yesman.epicfight.gameasset.Armatures;
import yesman.epicfight.particle.HitParticleType;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.damagesource.StunType;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Supplier;

public class ShotogatanaAttackAnimation extends BasicAttackAnimation {

    public final float unsheatheTime;
    public final float sheathTime;
    public final boolean ignoreFallDamage;

    public final boolean isAirAttack;

    private static Map<UUID, Boolean> queFallReset = new HashMap<>();

    public ShotogatanaAttackAnimation(float convertTime, String path, float unsheatheTime, float sheathTime, int phaseCount, float[] start , float[] antic, float[] contact, float[] recovery, float[] end, SoundEvent[] swingSound, SoundEvent[] hitSound, RegistryObject<HitParticleType>[] hitParticle, StunType stunType, Collider[] colliders, Joint[] colliderJoints, boolean moveVertical) {
        super(convertTime, path, Armatures.BIPED, buildPhases(phaseCount, start ,antic, contact, recovery, end, swingSound, hitSound, hitParticle, colliders, colliderJoints));
        this.unsheatheTime = unsheatheTime;
        this.sheathTime = sheathTime;
        this.addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, stunType)
                .addProperty(AnimationProperty.AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.175F)
                .addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, true)
                .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, false)
                .addProperty(AnimationProperty.ActionAnimationProperty.AFFECT_SPEED, false);
        this.addProperty(AnimationProperty.ActionAnimationProperty.MOVE_VERTICAL, moveVertical);

        this.ignoreFallDamage = false;
        this.isAirAttack = false;
    }

    public ShotogatanaAttackAnimation(float convertTime, String path, float unsheatheTime, float sheathTime, int phaseCount, float[] start , float[] antic, float[] contact, float[] recovery, float[] end, SoundEvent[] swingSound, SoundEvent[] hitSound, RegistryObject<HitParticleType>[] hitParticle, StunType stunType, Collider[] colliders, Joint[] colliderJoints, boolean ignoreFallDamage, float[] inAirTime) {
        super(convertTime, path, Armatures.BIPED, buildPhases(phaseCount, start ,antic, contact, recovery, end, swingSound, hitSound, hitParticle, colliders, colliderJoints));
        this.unsheatheTime = unsheatheTime;
        this.sheathTime = sheathTime;
        this.addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, stunType)
                .addProperty(AnimationProperty.AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.175F)
                .addProperty(AnimationProperty.ActionAnimationProperty.MOVE_VERTICAL, true)
                .addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, true)
                .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, false)
                .addProperty(AnimationProperty.ActionAnimationProperty.AFFECT_SPEED, false)
                .addProperty(AnimationProperty.ActionAnimationProperty.NO_GRAVITY_TIME, TimePairList.create(inAirTime));
        this.ignoreFallDamage = ignoreFallDamage;
        this.isAirAttack = true;
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
    public void begin(LivingEntityPatch<?> entitypatch) {
        if(entitypatch instanceof PlayerPatch<?> playerPatch) {
            AttackManager.isInAttack.put(playerPatch.getOriginal().getUUID(), true);
        }
        Log.info("currentAnimName: " + this.getRegistryName());
        super.begin(entitypatch);
    }
    @Override
    public void end(LivingEntityPatch<?> entitypatch, DynamicAnimation nextAnimation, boolean isEnd) {
        if(entitypatch instanceof PlayerPatch<?> playerPatch) {
            AttackManager.isInAttack.remove(playerPatch.getOriginal().getUUID());
        }
        super.end(entitypatch, nextAnimation, isEnd);
    }

    @Override
    protected void attackTick(LivingEntityPatch<?> entitypatch, DynamicAnimation animation) {
        super.attackTick(entitypatch, animation);
        if(!this.isAirAttack) return;
        if (entitypatch instanceof PlayerPatch<?> playerPatch) {
            if (ignoreFallDamage)
                queFallReset.put(playerPatch.getOriginal().getUUID(), true);
        }
    }

    @Override
    public boolean isBasicAttackAnimation() {
        return true;
    }
}
