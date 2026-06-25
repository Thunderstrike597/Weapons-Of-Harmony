package net.kenji.woh.gameasset.mob_patch;

import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.util.Pair;
import net.kenji.woh.entities.custom.alt_entities.RoninSkeletonEntity;
import net.kenji.woh.gameasset.WohMobCombatBehaviors;
import net.kenji.woh.gameasset.WohWeaponCategories;
import net.kenji.woh.registry.animation.ShotogatanaAnimations;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.player.Player;
import yesman.epicfight.api.animation.AnimationManager;
import yesman.epicfight.api.animation.Animator;
import yesman.epicfight.api.animation.LivingMotions;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.data.reloader.MobPatchReloadListener;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.gameasset.MobCombatBehaviors;
import yesman.epicfight.world.capabilities.entitypatch.EntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.Faction;
import yesman.epicfight.world.capabilities.entitypatch.Factions;
import yesman.epicfight.world.capabilities.entitypatch.HumanoidMobPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.damagesource.StunType;
import yesman.epicfight.world.entity.ai.goal.AnimatedAttackGoal;
import yesman.epicfight.world.entity.ai.goal.CombatBehaviors;
import yesman.epicfight.world.entity.ai.goal.TargetChasingGoal;

import java.util.Set;

public class RoninSkeletonPatch extends HumanoidMobPatch<RoninSkeletonEntity> {
    public RoninSkeletonPatch() {
        super(Factions.UNDEAD);
    }
    @Override
    public void updateMotion(boolean b) {
        super.commonMobUpdateMotion(b);
    }

    @Override
    protected CombatBehaviors.Builder<HumanoidMobPatch<?>> getHoldingItemWeaponMotionBuilder() {
        return super.getHoldingItemWeaponMotionBuilder();
    }

    @Override
    protected void setWeaponMotions() {
        super.setWeaponMotions();
        this.weaponLivingMotions.put(WohWeaponCategories.SHOTOGATANA, ImmutableMap.of(CapabilityItem.Styles.TWO_HAND, Set.of(Pair.of(LivingMotions.IDLE, ShotogatanaAnimations.SHOTOGATANA_IDLE), Pair.of(LivingMotions.WALK, ShotogatanaAnimations.SHOTOGATANA_WALK), Pair.of(LivingMotions.CHASE, ShotogatanaAnimations.SHOTOGATANA_WALK))));

    }

    @Override
    protected void initAI() {
        super.initAI();

        this.original.goalSelector.addGoal(
                1,
                new AnimatedAttackGoal<>(this, WohMobCombatBehaviors.RONIN_SKELETON.build(this)));;
        this.original.goalSelector.addGoal(2, new TargetChasingGoal(this, this.getOriginal(), 1.2f, true));
        this.original.goalSelector.addGoal(3, new RandomStrollGoal(original, 1.0f));

        this.original.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(original, Player.class, true));
    }

    public void initAnimator(Animator animator) {
        super.initAnimator(animator);

        // All available living motions are listed in this enum: https://github.com/Epic-Fight/epicfight/blob/1.21.1/src/main/java/yesman/epicfight/api/animation/LivingMotions.java#L4-L6
        animator.addLivingAnimation(LivingMotions.IDLE, ShotogatanaAnimations.SHOTOGATANA_IDLE);
        animator.addLivingAnimation(LivingMotions.WALK, ShotogatanaAnimations.SHOTOGATANA_WALK);
        animator.addLivingAnimation(LivingMotions.RUN, ShotogatanaAnimations.SHOTOGATANA_WALK);
    }
}
