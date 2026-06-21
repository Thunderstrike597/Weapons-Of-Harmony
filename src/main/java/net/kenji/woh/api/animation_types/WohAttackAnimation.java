package net.kenji.woh.api.animation_types;

import net.kenji.woh.api.AnimationConfig;
import net.kenji.woh.api.WOHAnimationUtils;
import net.kenji.woh.api.manager.AttackManager;
import net.kenji.woh.gameasset.AttackHand;
import net.kenji.woh.gameasset.animation_types.BasisAttackAnimation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraftforge.registries.RegistryObject;
import yesman.epicfight.api.animation.AnimationManager;
import yesman.epicfight.api.animation.Joint;
import yesman.epicfight.api.animation.property.AnimationProperty;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.animation.types.BasicAttackAnimation;
import yesman.epicfight.api.animation.types.DynamicAnimation;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.api.collider.Collider;
import yesman.epicfight.api.utils.math.ValueModifier;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.gameasset.Armatures;
import yesman.epicfight.model.armature.HumanoidArmature;
import yesman.epicfight.particle.HitParticleType;
import yesman.epicfight.skill.BasicAttack;
import yesman.epicfight.skill.SkillSlots;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;
import yesman.epicfight.world.damagesource.ExtraDamageInstance;
import yesman.epicfight.world.damagesource.StunType;
import yesman.epicfight.world.entity.eventlistener.ComboCounterHandleEvent;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Supplier;

public class WohAttackAnimation extends BasisAttackAnimation {

    public static AssetAccessor<? extends HumanoidArmature> biped = Armatures.BIPED;
    private static Map<UUID, Boolean> queFallReset = new HashMap<>();

    public static float convertTime = 0.1f;
    public final boolean isAirAttack;
    public final boolean ignoreFallDamage;
    public final boolean useComboCounterReset;
    public WohAttackAnimation(AnimationManager.AnimationAccessor<AttackAnimation> accessor, AnimationConfig config) {
        super(
                config.attackType,
                config.convertTime,
                accessor,            // Pass accessor to parent
                biped,
                config.ignoreFallDamage,
                buildPhases(config)
        );

        this.addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, config.stunType)
                .addProperty(AnimationProperty.AttackAnimationProperty.ATTACK_SPEED_FACTOR, config.attackSpeed)
                .addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, true)
                .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, false)
                .addProperty(AnimationProperty.ActionAnimationProperty.AFFECT_SPEED, true);
        if(attackType == WOHAnimationUtils.AttackAnimationType.BASIC_ATTACK_JUMP)
             this.addProperty(AnimationProperty.ActionAnimationProperty.MOVE_VERTICAL, true);
        else this.addProperty(AnimationProperty.ActionAnimationProperty.MOVE_VERTICAL, false);
        this.addProperty(AnimationProperty.ActionAnimationProperty.COORD_SET_BEGIN, WOHAnimationUtils.scaledRawCoord(config.movementMultiplier));
        this.addProperty(AnimationProperty.ActionAnimationProperty.COORD_SET_TICK,  WOHAnimationUtils.scaledRawCoord(config.movementMultiplier));
        if(config.airTime != null)
            this.addProperty(AnimationProperty.AttackAnimationProperty.NO_GRAVITY_TIME,  config.airTime);

        isAirAttack = config.attackType == WOHAnimationUtils.AttackAnimationType.AIR_ATTACK;
        ignoreFallDamage = config.ignoreFallDamage;
        useComboCounterReset = config.useComboCounterReset;
    }
    private static AttackAnimation.Phase[] buildPhases(AnimationConfig config) {
        AttackAnimation.Phase[] phases = new AttackAnimation.Phase[config.phaseCount];

        for(int i = 0; i < config.phaseCount; i++) {
            AttackAnimation.JointColliderPair[] phaseColliders;
            if (i < config.colliders.length) {
                phaseColliders = config.colliders[i];
            } else if (config.colliders.length > 0) {
                phaseColliders = config.colliders[config.colliders.length - 1];
            } else {
                phaseColliders = new AttackAnimation.JointColliderPair[0];
            }
            phases[i] = new AttackAnimation.Phase(
                    config.start[i],
                    config.antic[i],
                    config.antic[i],
                    config.contact[i],
                    config.recovery[i],
                    config.end[i],
                    InteractionHand.MAIN_HAND,
                    phaseColliders
            );
            if(i < config.hitSound.length)
                phases[i].addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, config.hitSound[i].get());
            else if(config.hitSound.length > 0)
                phases[i].addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, config.hitSound[config.hitSound.length - 1].get());
            if(i < config.swingSound.length)
                phases[i].addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, config.swingSound[i].get());
            else if(config.hitSound.length > 0)
                phases[i].addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, config.swingSound[config.swingSound.length - 1].get());
            if(i < config.hitParticle.length)
                phases[i].addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, config.hitParticle[i]);
            else if(config.hitParticle.length > 0)
                phases[i].addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, config.hitParticle[config.hitParticle.length - 1]);
            if(i < config.attackDamage.length)
                phases[i].addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.adder(config.attackDamage[i]));
            else if(config.attackDamage.length > 0)
                phases[i].addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.adder(config.attackDamage[config.attackDamage.length - 1]));
            if(i < config.impact.length)
                phases[i].addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.adder(config.impact[i]));
            else if(config.impact.length > 0)
                phases[i].addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.adder(config.impact[config.impact.length - 1]));

        }

        return phases;
    }
    @Override
    public void begin(LivingEntityPatch<?> entitypatch) {
        if(this.useComboCounterReset) {
            if (entitypatch instanceof PlayerPatch<?> playerPatch) {
                AttackManager.isInAttack.put(playerPatch.getOriginal().getUUID(), true);
            }
        }
        super.begin(entitypatch);
    }
    @Override
    public void end(LivingEntityPatch<?> entitypatch, AssetAccessor<? extends DynamicAnimation> nextAnimation, boolean isEnd) {
        if(this.useComboCounterReset) {
            if (entitypatch instanceof PlayerPatch<?> playerPatch) {
                AttackManager.isInAttack.remove(playerPatch.getOriginal().getUUID());
            }
            if (isEnd) {
                if (entitypatch instanceof ServerPlayerPatch serverPlayerPatch) {
                    BasicAttack.setComboCounterWithEvent(ComboCounterHandleEvent.Causal.TIME_EXPIRED, serverPlayerPatch, serverPlayerPatch.getSkill(SkillSlots.BASIC_ATTACK), Animations.EMPTY_ANIMATION.getAccessor(), 0);
                }
            }
        }
        super.end(entitypatch, nextAnimation, isEnd);
    }
    @Override
    protected void attackTick(LivingEntityPatch<?> entitypatch, AssetAccessor<? extends DynamicAnimation> animation) {
        super.attackTick(entitypatch, animation);
        if(!this.isAirAttack) return;
        if (entitypatch instanceof PlayerPatch<?> playerPatch) {
            if (ignoreFallDamage)
                queFallReset.put(playerPatch.getOriginal().getUUID(), true);
        }
    }
}
