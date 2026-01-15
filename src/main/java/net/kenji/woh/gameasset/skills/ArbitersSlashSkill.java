package net.kenji.woh.gameasset.skills;

import com.google.common.collect.Lists;
import net.kenji.woh.api.manager.AimManager;
import net.kenji.woh.entities.ModEntities;
import net.kenji.woh.entities.custom.BeamSlashEntity;
import net.kenji.woh.gameasset.animation_types.BasisAttackAnimation;
import net.kenji.woh.registry.animation.ArbitersBladeAnimations;
import net.minecraft.ChatFormatting;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
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
import yesman.epicfight.skill.Skill;
import yesman.epicfight.skill.SkillBuilder;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.skill.modules.ChargeableSkill;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;

import java.util.*;

public class ArbitersSlashSkill extends Skill implements ChargeableSkill {

    public static float travelSpeedMultiplier = 1.75f;

    private SkillContainer currentContainer;

    private static final Map<UUID, Boolean> wasHoldingMap = new HashMap<>();

    private static final Map<UUID, List<BasisAttackAnimation>> beamCastMap = new HashMap<>();

    public ArbitersSlashSkill(SkillBuilder builder) {
        super(builder);
        this.maxDuration = 350;
        this.consumption = 10;
        this.maxStackSize = 1;
    }

    @Override
    public ResourceLocation getSkillTexture() {
        return EpicFightSkills.SWEEPING_EDGE.getSkillTexture();
    }


    @Override
    public void updateContainer(SkillContainer container) {
        // Handle charging phase (before activation)
        currentContainer = container;
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
            Player player = container.getExecutor().getOriginal();
            if (player.level().isClientSide) {
                Minecraft mc = Minecraft.getInstance();
                UUID id = player.getUUID();

                boolean isHolding = EpicFightKeyMappings.GUARD.isDown();
                boolean wasHolding = wasHoldingMap.getOrDefault(id, false);

                if (isHolding && !wasHolding) {
                    // === PRESS (start) ===
                    onAimPress(container);
                }

                if (!isHolding && wasHolding) {
                    // === RELEASE (stop) ===
                    onAimRelease(container);
                }
                wasHoldingMap.put(id, isHolding);
            }
            if (container.getExecutor().getAnimator().getLivingAnimation(
                    LivingMotions.BLOCK, ArbitersBladeAnimations.ARBITERS_BLADE_AIM) != Animations.LONGSWORD_GUARD) {
                container.getExecutor().getAnimator().addLivingAnimation(
                        LivingMotions.BLOCK, ArbitersBladeAnimations.ARBITERS_BLADE_AIM
                );
            }
            // Check for attack animation_types and spawn beam slash
            PlayerPatch<?> playerPatch = container.getExecutor();
            AnimationPlayer animPlayer = playerPatch.getAnimator().getPlayerFor(null);

            if (animPlayer != null && animPlayer.getAnimation().get() instanceof BasisAttackAnimation attackAnim) {

                if (playerPatch.getOriginal().level() instanceof ServerLevel serverLevel) {
                    if (attackAnim.isAttackBegin() && attackAnim.getSlashAngle() != -1) {
                        if(beamCastMap.get(player.getUUID()) == null) {
                            List<BasisAttackAnimation> anims = new ArrayList<>();
                            anims.add(attackAnim);
                            onBeamSlash(playerPatch, attackAnim.getAccessor(), serverLevel);
                            beamCastMap.put(player.getUUID(), anims);
                        }
                        else{
                           List<BasisAttackAnimation> anims = beamCastMap.get(player.getUUID());
                           boolean shouldCast = true;
                           for(BasisAttackAnimation anim : anims){
                               if(anim == attackAnim){
                                   shouldCast = false;
                                   break;
                               }
                           }
                           if(shouldCast){
                               onBeamSlash(playerPatch, attackAnim.getAccessor(), serverLevel);
                               anims.add(attackAnim);
                               beamCastMap.put(player.getUUID(), anims);
                           }
                        }
                    }
                    List<BasisAttackAnimation> anims = beamCastMap.get(player.getUUID());
                    if (anims != null) {
                        AnimationPlayer current = playerPatch.getAnimator().getPlayerFor(null);
                        anims.removeIf(anim ->
                                current == null || current.getAnimation().get() != anim
                        );

                        if (anims.isEmpty()) {
                            beamCastMap.remove(player.getUUID());
                        }
                    }
                }
            }

            // Decrement duration
            if (container.getRemainDuration() > 0) {
                container.setDuration(container.getRemainDuration() - 1);
            }
            else{
                container.deactivate();
                resetHolding(container);
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
    public List<Component> getTooltipOnItem(ItemStack itemStack, CapabilityItem cap, PlayerPatch<?> playerCap) {
        List<Component> list = Lists.newArrayList();
        String traslatableText = this.getTranslationKey();
        list.add(Component.translatable(traslatableText).withStyle(ChatFormatting.WHITE)
                .append(Component.literal(String.format("[%.0f]", this.consumption)).withStyle(ChatFormatting.AQUA)));
        list.add(Component.translatable(traslatableText + ".tooltip")
                .withStyle(ChatFormatting.AQUA));
        list.add(Component.translatable(traslatableText + ".tooltip.extra")
                .withStyle(ChatFormatting.RED).append(String.valueOf(this.maxDuration / 20)));
        return list;
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

    private AssetAccessor<StaticAnimation> getAimAnimation(){
        return ArbitersBladeAnimations.ARBITERS_BLADE_AIM;
    }

    private void onAimPress(SkillContainer container){
        container.getExecutor().getAnimator().addLivingAnimation(LivingMotions.IDLE, getAimAnimation());
        container.getExecutor().getAnimator().addLivingAnimation(LivingMotions.WALK, getAimAnimation());
    }
    private void onAimRelease(SkillContainer container){
        container.getExecutor().getAnimator().addLivingAnimation(LivingMotions.IDLE, container.getExecutor().getHoldingItemCapability(InteractionHand.MAIN_HAND).getLivingMotionModifier(container.getExecutor(), InteractionHand.MAIN_HAND).get(LivingMotions.IDLE));
        container.getExecutor().getAnimator().addLivingAnimation(LivingMotions.WALK, container.getExecutor().getHoldingItemCapability(InteractionHand.MAIN_HAND).getLivingMotionModifier(container.getExecutor(), InteractionHand.MAIN_HAND).get(LivingMotions.WALK));

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
        if(!container.isActivated()) {
            container.getExecutor().setChargingAmount(0);
            container.getExecutor().playAnimationSynchronized(
                    ArbitersBladeAnimations.ARBITERS_BLADE_SKILL_ACTIVATE_START, 0.1F
            );
            return;
        }

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