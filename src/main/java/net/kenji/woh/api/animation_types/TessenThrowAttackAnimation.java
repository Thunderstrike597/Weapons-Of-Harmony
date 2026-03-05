package net.kenji.woh.api.animation_types;

import net.kenji.woh.api.manager.AttackManager;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraftforge.registries.RegistryObject;
import org.jline.utils.Log;
import yesman.epicfight.api.animation.Joint;
import yesman.epicfight.api.animation.property.AnimationProperty;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.animation.types.DynamicAnimation;
import yesman.epicfight.api.collider.Collider;
import yesman.epicfight.gameasset.Armatures;
import yesman.epicfight.particle.HitParticleType;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.damagesource.StunType;

import java.util.HashMap;
import java.util.Map;

public class TessenThrowAttackAnimation extends AttackAnimation {

    private static Map<String, ThrowType> lastAssignedType = new HashMap<>();

    public enum ThrowType{
        RIGHT_HAND,
        LEFT_HAND,
        BOTH
    }

    public TessenThrowAttackAnimation(float convertTime, String path, float speed, float throwStart, float throwEnd, int phaseCount, float start , float antic, float contact, float recovery, float end, SoundEvent swingSound, SoundEvent hitSound, RegistryObject<HitParticleType> hitParticle, StunType stunType, Collider colliders, ThrowType throwType, boolean ignoreFallDamage) {
        super(convertTime, path, Armatures.BIPED, buildPhases(path, phaseCount, throwStart, throwEnd, start ,antic, contact, recovery, end,swingSound, hitSound, hitParticle, colliders, throwType));
        this.addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, stunType)
                .addProperty(AnimationProperty.AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.175F)
                .addProperty(AnimationProperty.AttackAnimationProperty.ATTACK_SPEED_FACTOR, speed)
                .addProperty(AnimationProperty.ActionAnimationProperty.MOVE_VERTICAL, true)
                .addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, true)
                .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, false)
                .addProperty(AnimationProperty.ActionAnimationProperty.AFFECT_SPEED, false);

    }

    private static Phase[] buildPhases(
            String path,
            int phaseCount,
            float throwStart,
            float throwEnd,
            float start,
            float antic,
            float contact,
            float recovery,
            float end,
            SoundEvent swingSound,
            SoundEvent hitSound,
            RegistryObject<HitParticleType> hitParticle,
            Collider collider,
            ThrowType throwType
    ) {
        Phase[] phases = new Phase[phaseCount];
        System.out.println("throwStart: " + throwStart);
        System.out.println("throwEnd: " + throwEnd);
        System.out.println("totalDuration: " + (throwEnd - throwStart));
        // --- Phase 0 (ABSOLUTE / UNSCALED) ---
        phases[0] = new Phase(
                start,
                antic,
                contact,
                recovery,
                end,
                InteractionHand.MAIN_HAND,
                getThrownHand(path, throwType),
                collider
        ).addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, hitSound)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, swingSound)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, hitParticle);

        // If there's only one phase, we're done
        if (phaseCount <= 1) {
            return phases;
        }

        // --- THROW WINDOW SETUP ---
        int remainingPhases = phaseCount - 1;
        float totalDuration = throwEnd - throwStart;
        float sliceDuration = totalDuration / remainingPhases;

        // --- BASE RATIOS (FROM FIRST PHASE SHAPE) ---
        float baseDuration = end - start;

        float anticRatio    = (antic    - start) / baseDuration;
        float contactRatio  = (contact  - start) / baseDuration;
        float recoveryRatio = (recovery - start) / baseDuration;

        // --- BUILD SCALED THROW PHASES ---
        for (int i = 1; i < phaseCount; i++) {
            float newStart = throwStart + (i - 1) * sliceDuration;
            float newEnd   = newStart + sliceDuration;

            float newAntic    = newStart + anticRatio    * sliceDuration;
            float newContact  = newStart + contactRatio  * sliceDuration;
            float newRecovery = newStart + recoveryRatio * sliceDuration;

            phases[i] = new Phase(
                    newStart,
                    newAntic,
                    newContact,
                    newRecovery,
                    newEnd,
                    InteractionHand.MAIN_HAND,
                    getThrownHand(path, throwType),
                    collider
            ).addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, hitSound)
                    .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, swingSound)
                    .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, hitParticle);
        }

        return phases;
    }

    private static Joint getThrownHand(String path, ThrowType throwType){
        Joint jointType = null;
        if(throwType == ThrowType.RIGHT_HAND) jointType = Armatures.BIPED.toolR;
        else if(throwType == ThrowType.LEFT_HAND) jointType = Armatures.BIPED.toolL;
        else if(throwType == ThrowType.BOTH){
            ThrowType lastThrowType = lastAssignedType.getOrDefault(path, ThrowType.LEFT_HAND);

            if(lastThrowType == ThrowType.LEFT_HAND)
                jointType = Armatures.BIPED.toolR;
            else if(lastThrowType == ThrowType.RIGHT_HAND)
                jointType = Armatures.BIPED.toolL;
        }
        if(jointType == null)
            jointType = Armatures.BIPED.toolR;

        lastAssignedType.put(path, throwType);
        return jointType;
    }

    @Override
    public void begin(LivingEntityPatch<?> entitypatch) {
        if(entitypatch instanceof PlayerPatch<?> playerPatch) {
            AttackManager.isInAttack.put(playerPatch.getOriginal().getUUID(), true);
        }
        for(int i = 0; i < phases.length; i++){
            Log.info("Phase " + i + "Start: " + phases[i].start);
            Log.info("Phase " + i + "Antic: " + phases[i].antic);
            Log.info("Phase " + i + "Contact: " + phases[i].contact);
            Log.info("Phase " + i + "Recovery: " + phases[i].recovery);
            Log.info("Phase " + i + "End: " + phases[i].end);
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
