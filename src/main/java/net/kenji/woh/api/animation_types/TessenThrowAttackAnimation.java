package net.kenji.woh.api.animation_types;

import net.kenji.woh.gameasset.AttackHand;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraftforge.registries.RegistryObject;
import yesman.epicfight.api.animation.AnimationManager;
import yesman.epicfight.api.animation.AnimationPlayer;
import yesman.epicfight.api.animation.property.AnimationProperty;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.animation.types.DynamicAnimation;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.api.collider.Collider;
import yesman.epicfight.api.utils.TimePairList;
import yesman.epicfight.gameasset.Armatures;
import yesman.epicfight.model.armature.HumanoidArmature;
import yesman.epicfight.particle.HitParticleType;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.damagesource.StunType;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class TessenThrowAttackAnimation extends AttackAnimation {

    private static Map<String, AttackHand> lastAssignedType = new HashMap<>();


    private final boolean useMovement;
    private final boolean ignoreFallDamage;


    public TessenThrowAttackAnimation(float convertTime, String path, AnimationManager.AnimationAccessor<? extends AttackAnimation> accessor, float speed, float throwStart, float throwEnd, int phaseCount, float start , float antic, float contact, float recovery, float end, Supplier<SoundEvent> swingSound, Supplier<SoundEvent> hitSound, RegistryObject<HitParticleType> hitParticle, StunType stunType, Collider colliders, AttackHand throwType, boolean ignoreFallDamage, boolean useMovement) {
        super(convertTime, accessor, Armatures.BIPED, buildPhases(path, phaseCount, throwStart, throwEnd, start ,antic, contact, recovery, end, swingSound, hitSound, hitParticle, colliders, throwType));
        this.addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, stunType)
                .addProperty(AnimationProperty.AttackAnimationProperty.ATTACK_SPEED_FACTOR, speed)
                .addProperty(AnimationProperty.ActionAnimationProperty.MOVE_VERTICAL, useMovement)
                .addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, false)
                .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, false)
                .addProperty(AnimationProperty.ActionAnimationProperty.AFFECT_SPEED, false);
        this.useMovement = useMovement;
        this.ignoreFallDamage = ignoreFallDamage;
    }
    public TessenThrowAttackAnimation(float convertTime, String path, AnimationManager.AnimationAccessor<? extends AttackAnimation> accessor, float speed, float throwStart, float throwEnd, int phaseCount, float start , float antic, float contact, float recovery, float end, Supplier<SoundEvent> swingSound, Supplier<SoundEvent> hitSound, RegistryObject<HitParticleType> hitParticle, StunType stunType, Collider colliders, AttackHand throwType, boolean ignoreFallDamage, float[] airTime) {
        super(convertTime, accessor, Armatures.BIPED, buildPhases(path, phaseCount, throwStart, throwEnd, start ,antic, contact, recovery, end, swingSound, hitSound, hitParticle, colliders, throwType));
        this.addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, stunType)
                .addProperty(AnimationProperty.AttackAnimationProperty.ATTACK_SPEED_FACTOR, speed)
                .addProperty(AnimationProperty.ActionAnimationProperty.MOVE_VERTICAL, true)
                .addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, false)
                .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, false)
                .addProperty(AnimationProperty.ActionAnimationProperty.AFFECT_SPEED, false)
                .addProperty(AnimationProperty.AttackAnimationProperty.NO_GRAVITY_TIME, TimePairList.create(airTime));

        this.useMovement = true;
        this.ignoreFallDamage = ignoreFallDamage;
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
            Supplier<SoundEvent> swingSound,
            Supplier<SoundEvent> hitSound,
            RegistryObject<HitParticleType> hitParticle,
            Collider collider,
            AttackHand throwType
    ) {

        Phase[] phases = new Phase[phaseCount];
        // --- Phase 0 (ABSOLUTE / UNSCALED) ---
        phases[0] = new Phase(
                start,
                antic,
                antic,
                contact,
                recovery,
                end,
                InteractionHand.MAIN_HAND,
                getThrownHand(path, throwType, collider)
        ).addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, hitSound.get())
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, swingSound.get())
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, hitParticle);

        // If there's only one phase, we're done
        if (phaseCount <= 1) {
            return phases;
        }
        float minGap = 0.001F;
        // --- THROW WINDOW SETUP ---
        int remainingPhases = phaseCount - 1;
        float totalDuration = Math.max(throwEnd - throwStart, 0.05F);
        float sliceDuration = totalDuration / remainingPhases;
        sliceDuration = Math.max(sliceDuration, 0.01F);
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

            newAntic    = Math.max(newAntic,    newStart    + minGap);
            newContact  = Math.max(newContact,  newAntic    + minGap);
            newRecovery = Math.max(newRecovery, newContact  + minGap);
            float newEndClamped = Math.max(newEnd, newRecovery + minGap);
            phases[i] = new Phase(
                    newStart,
                    newAntic,
                    newAntic,
                    newContact,
                    newRecovery,
                    newEndClamped,
                    InteractionHand.MAIN_HAND,
                    getThrownHand(path, throwType, collider)
            ).addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, hitSound.get())
                    .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, swingSound.get())
                    .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, hitParticle);

        }
        return phases;
    }

    private static JointColliderPair[] getThrownHand(String path, AttackHand throwType, Collider collider){
        JointColliderPair[] pair = null;
        if(throwType == AttackHand.RIGHT_HAND) return new AttackAnimation.JointColliderPair[]{JointColliderPair.of(((HumanoidArmature)Armatures.BIPED.get()).toolR, (Collider)collider)};
        if(throwType == AttackHand.LEFT_HAND) return new AttackAnimation.JointColliderPair[]{JointColliderPair.of(((HumanoidArmature)Armatures.BIPED.get()).toolL, (Collider)collider)};
        if(throwType == AttackHand.HANDS){
            return new AttackAnimation.JointColliderPair[]{JointColliderPair.of(((HumanoidArmature)Armatures.BIPED.get()).toolR, (Collider)collider), JointColliderPair.of(((HumanoidArmature)Armatures.BIPED.get()).toolL, (Collider)collider)};
        }
        return new AttackAnimation.JointColliderPair[]{JointColliderPair.of(((HumanoidArmature)Armatures.BIPED.get()).toolR, (Collider)collider)};
    }
    @Override
    protected void attackTick(LivingEntityPatch<?> entitypatch, AssetAccessor<? extends DynamicAnimation> animation) {
        if (entitypatch instanceof PlayerPatch<?> playerPatch) {
            AnimationPlayer animPlayer = playerPatch.getAnimator().getPlayerFor(this.accessor);
            if (ignoreFallDamage)
                playerPatch.getOriginal().resetFallDistance();
        }
        super.attackTick(entitypatch, animation);
    }
    @Override
    protected void move(LivingEntityPatch<?> entitypatch, AssetAccessor<? extends DynamicAnimation> animation) {
        if(useMovement)
            super.move(entitypatch, animation);
    }
}
