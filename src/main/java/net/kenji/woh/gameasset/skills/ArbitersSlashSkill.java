package net.kenji.woh.gameasset.skills;

import net.kenji.woh.api.manager.AimManager;
import net.kenji.woh.entities.ModEntities;
import net.kenji.woh.entities.custom.BeamSlashEntity;
import net.kenji.woh.gameasset.WohWeaponCategories;
import net.kenji.woh.gameasset.animations.BasisAttackAnimation;
import net.kenji.woh.registry.animation.ArbitersBladeAnimations;
import net.minecraft.client.KeyMapping;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.phys.Vec3;
import org.jline.utils.Log;
import yesman.epicfight.api.animation.AnimationManager;
import yesman.epicfight.api.animation.AnimationPlayer;
import yesman.epicfight.api.animation.LivingMotions;
import yesman.epicfight.api.animation.types.DynamicAnimation;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.client.input.EpicFightKeyMappings;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.gameasset.EpicFightSkills;
import yesman.epicfight.network.server.SPSkillExecutionFeedback;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.skill.guard.GuardSkill;
import yesman.epicfight.skill.modules.ChargeableSkill;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;

public class ArbitersSlashSkill extends GuardSkill implements ChargeableSkill {

    public static float travelSpeedMultiplier = 1.75f;

    public ArbitersSlashSkill(Builder builder) {
        super(builder);
        this.maxDuration = 420;
        this.consumption = 10;
        this.maxStackSize = 1;
        guardMotions.put(
                WohWeaponCategories.ARBITERS_BLADE,
                (item, player) -> ArbitersBladeAnimations.ARBITERS_BLADE_AIM
        );
    }

    @Override
    public ResourceLocation getSkillTexture() {
        return EpicFightSkills.SWEEPING_EDGE.getSkillTexture();
    }

    @Override
    protected boolean isBlockableSource(DamageSource damageSource, boolean advanced) {
        return false;
    }

    @Override
    public void updateContainer(SkillContainer container) {
        // Handle charging phase (before activation)
        if (!container.isActivated()) {
            float chargingAmount = container.getExecutor().getChargingAmount();

            // Update animation based on charging progress
            if (chargingAmount > 1) {
                AnimationPlayer animationPlayer = container.getExecutor().getAnimator().getPlayerFor(null);
                if (animationPlayer != null) {
                    AssetAccessor<? extends DynamicAnimation> dynamicAnim = animationPlayer.getAnimation();
                    if (dynamicAnim instanceof StaticAnimation anim) {
                        if (anim != ArbitersBladeAnimations.ARBITERS_BLADE_SKILL_ACTIVATE_START &&
                                anim != ArbitersBladeAnimations.ARBITERS_BLADE_SKILL_ACTIVATE_MID) {
                            container.getExecutor().playAnimationSynchronized(
                                    ArbitersBladeAnimations.ARBITERS_BLADE_SKILL_ACTIVATE_MID, 0.1F
                            );
                        }
                    }
                }
            }

            // Ensure guard animation is set
            if (container.getExecutor().getAnimator().getLivingAnimation(
                    LivingMotions.BLOCK, ArbitersBladeAnimations.ARBITERS_BLADE_AIM) != Animations.LONGSWORD_GUARD) {
                container.getExecutor().getAnimator().addLivingAnimation(
                        LivingMotions.BLOCK, Animations.LONGSWORD_GUARD
                );
            }

            // Check if fully charged and activate
            if (container.getExecutor().getSkillChargingTicks(1.0F) >= getAllowedMaxChargingTicks()) {
                container.getExecutor().playAnimationSynchronized(
                        ArbitersBladeAnimations.ARBITERS_BLADE_SKILL_ACTIVATE_END, 0.1F
                );

                if (container.getExecutor().getOriginal().level() instanceof ServerLevel serverLevel) {
                    BlockPos blockPos = container.getExecutor().getOriginal().blockPosition();
                    serverLevel.playSound(
                            null,
                            blockPos,
                            SoundEvents.ENCHANTMENT_TABLE_USE,
                            SoundSource.PLAYERS,
                            1.0F,
                            1.0F
                    );
                }

                container.activate();
            }
        }

        // Handle active phase (after activation)
        if (container.isActivated()) {
            // Switch to aim animation
            if (container.getExecutor().getAnimator().getLivingAnimation(
                    LivingMotions.BLOCK, Animations.LONGSWORD_GUARD) != ArbitersBladeAnimations.ARBITERS_BLADE_AIM) {
                container.getExecutor().getAnimator().addLivingAnimation(
                        LivingMotions.BLOCK, ArbitersBladeAnimations.ARBITERS_BLADE_AIM
                );
            }

            // Check for attack animations and spawn beam slash
            PlayerPatch<?> playerPatch = container.getExecutor();
            AnimationPlayer player = playerPatch.getAnimator().getPlayerFor(null);

            if (player != null && player.getAnimation().get() instanceof BasisAttackAnimation attackAnim) {

                if (playerPatch.getOriginal().level() instanceof ServerLevel serverLevel) {
                    if (attackAnim.isAttackBegin() && attackAnim.getSlashAngle() != -1) {
                        Log.info("IS ATTACKING WITH BASIS ATTACK");
                        onBeamSlash(playerPatch, attackAnim.getAccessor(), serverLevel);
                    }
                }
            }

            // Decrement duration
            if (container.getRemainDuration() > 0) {
                container.setDuration(container.getRemainDuration() - 1);
            }
        }

        super.updateContainer(container);
    }

    private void onBeamSlash(PlayerPatch<?> playerPatch,
                             AnimationManager.AnimationAccessor<BasisAttackAnimation> basisAttackAnimation,
                             ServerLevel serverLevel) {
        BlockPos blockPos = playerPatch.getOriginal().blockPosition();
        BeamSlashEntity spawnedEntity = ModEntities.BEAM_SLASH.get().spawn(
                serverLevel, blockPos, MobSpawnType.TRIGGERED
        );

        if (spawnedEntity != null) {
            Vec3 lookDir = playerPatch.getOriginal().getLookAngle();
            float yVelocity = AimManager.isAiming(playerPatch) ? travelSpeedMultiplier : 0;

            spawnedEntity.addDeltaMovement(
                    lookDir.multiply(travelSpeedMultiplier, yVelocity, travelSpeedMultiplier)
            );
            spawnedEntity.setYRot(playerPatch.getOriginal().getYHeadRot());
            spawnedEntity.setSlashAngle(basisAttackAnimation.get().getSlashAngle());
            spawnedEntity.setCasterAndAnimation(playerPatch, basisAttackAnimation);

            // Play sound effects
            SoundEvent[] castSounds = {
                    SoundEvents.TRIDENT_RIPTIDE_1,
                    SoundEvents.TRIDENT_RIPTIDE_2,
                    SoundEvents.TRIDENT_RIPTIDE_3
            };
            int castIndex = (int) Mth.randomBetween(RandomSource.create(), 0, 2);
            SoundEvent castSound = castSounds[castIndex];

            serverLevel.playSound(null, blockPos, castSound, SoundSource.PLAYERS, 1.0F, 1.0F);
            serverLevel.playSound(null, blockPos, SoundEvents.ENCHANTMENT_TABLE_USE,
                    SoundSource.PLAYERS, 0.55F, 1.0F);
        }
    }

    @Override
    public int getAllowedMaxChargingTicks() {
        return 40;
    }

    @Override
    public int getMaxChargingTicks() {
        return 40;
    }

    @Override
    public int getMinChargingTicks() {
        return 0;
    }

    @Override
    public KeyMapping getKeyMapping() {
        return EpicFightKeyMappings.WEAPON_INNATE_SKILL;
    }

    @Override
    public void resetHolding(SkillContainer container) {
        container.getExecutor().setChargingAmount(0);
    }

    @Override
    public void onStopHolding(SkillContainer container, SPSkillExecutionFeedback feedback) {
        // Don't deactivate immediately - let the skill finish naturally
        // Only reset if not fully charged
        if (!container.isActivated()) {
            resetHolding(container);
        }
    }

    @Override
    public void startHolding(SkillContainer container) {
        // DON'T call super.startHolding() - it activates immediately
        // Instead, initialize charging
        container.getExecutor().setChargingAmount(0);
        container.getExecutor().playAnimationSynchronized(
                ArbitersBladeAnimations.ARBITERS_BLADE_SKILL_ACTIVATE_START, 0.1F
        );
    }

    @Override
    public void holdTick(SkillContainer container) {
        // Only increment charging if not yet activated
        if (!container.isActivated()) {
            int currentCharge = container.getExecutor().getChargingAmount();
            if (currentCharge < getMaxChargingTicks()) {
                container.getExecutor().setChargingAmount(currentCharge + 1);
            }
        }

        // Don't call super.holdTick() - we're handling our own logic
    }

    @Override
    public void onInitiate(SkillContainer container) {
        super.onInitiate(container);
    }
}