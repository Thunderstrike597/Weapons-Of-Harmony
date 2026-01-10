package net.kenji.woh.gameasset.skills;

import net.kenji.woh.WeaponsOfHarmony;
import net.kenji.woh.api.manager.AimManager;
import net.kenji.woh.entities.ModEntities;
import net.kenji.woh.entities.custom.BeamSlashEntity;
import net.kenji.woh.gameasset.animations.BasisAttackAnimation;
import net.kenji.woh.network.AimHoldPacket;
import net.kenji.woh.network.WohPacketHandler;
import net.kenji.woh.registry.animation.ArbitersBladeAnimations;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jline.utils.Log;
import yesman.epicfight.api.animation.AnimationPlayer;
import yesman.epicfight.api.animation.types.DynamicAnimation;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.client.events.engine.ControllEngine;
import yesman.epicfight.client.input.EpicFightKeyMappings;
import yesman.epicfight.client.world.capabilites.entitypatch.player.LocalPlayerPatch;
import yesman.epicfight.gameasset.EpicFightSkills;
import yesman.epicfight.network.server.SPSkillExecutionFeedback;
import yesman.epicfight.skill.ChargeableSkill;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.skill.weaponinnate.WeaponInnateSkill;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ArbitersSlash extends WeaponInnateSkill implements ChargeableSkill {

    public static float travelSpeedMultiplier = 1.75f;

    public ArbitersSlash(Builder<? extends Skill> builder) {
        super(builder);
        this.maxDuration = 420;
        this.consumption = 10;
        this.maxStackSize = 1;
    }


    @Override
    public ResourceLocation getSkillTexture() {
        return EpicFightSkills.SWEEPING_EDGE.getSkillTexture();
    }

    @Override
    public void updateContainer(SkillContainer container) {
        if(!container.isActivated()) {
            if (getChargingAmount(container.getExecuter()) > 1 && getChargingAmount(container.getExecuter()) < getMaxChargingTicks()) {
                AnimationPlayer animationPlayer = container.getExecuter().getAnimator().getPlayerFor(null);
                if (animationPlayer != null) {
                    DynamicAnimation dynamicAnim = animationPlayer.getAnimation();
                    if (dynamicAnim instanceof StaticAnimation anim) {
                        if (anim != ArbitersBladeAnimations.ARBITERS_BLADE_SKILL_ACTIVATE_START) {
                            container.getExecuter().playAnimationSynchronized(ArbitersBladeAnimations.ARBITERS_BLADE_SKILL_ACTIVATE_MID, 0.1F);
                        }
                    }
                }
            }
        }
        if (container.getExecuter().getSkillChargingTicks(1.0F) > (float)getAllowedMaxChargingTicks()) {
            if(!container.isActivated())
                container.getExecuter().playAnimationSynchronized(ArbitersBladeAnimations.ARBITERS_BLADE_SKILL_ACTIVATE_END, 0.1F);
            container.activate();
            Log.info("Is Activated:" + container.isActivated());
        }


        if (container.isActivated()) {
            PlayerPatch<?> playerPatch = container.getExecuter();
            if (playerPatch.getAnimator().getPlayerFor(null).getAnimation() instanceof BasisAttackAnimation attackAnim) {
               if(playerPatch.getOriginal().level() instanceof ServerLevel serverLevel) {
                   if (attackAnim.isAttackBegin()) {
                       onBeamSlash(playerPatch, attackAnim, serverLevel);
                   }
               }
            }
            if(container.getRemainDuration() > 0) {
                container.setDuration(container.getRemainDuration() - 1);
            }
            else{
                container.deactivate();
                resetCharging(container.getExecuter());
            }
        }
        super.updateContainer(container);

    }


    @Override
    public void executeOnServer(ServerPlayerPatch executor, FriendlyByteBuf args) {
        super.executeOnServer(executor, args);
    }

    private void onBeamSlash(PlayerPatch<?> playerPatch, BasisAttackAnimation basisAttackAnimation, ServerLevel serverLevel){
        if(serverLevel != null) {
            BlockPos blockPos = playerPatch.getOriginal().blockPosition();
            BeamSlashEntity spawnedEntity = ModEntities.BEAM_SLASH.get().spawn(serverLevel, blockPos, MobSpawnType.TRIGGERED);
            Vec3 lookDir = playerPatch.getOriginal().getLookAngle();
            if (spawnedEntity != null) {
                float yVelocity = AimManager.isAiming(playerPatch) ? travelSpeedMultiplier : 0;

                spawnedEntity.addDeltaMovement(lookDir.multiply(travelSpeedMultiplier, yVelocity, travelSpeedMultiplier));
                spawnedEntity.setYRot(playerPatch.getOriginal().getYRot());
                spawnedEntity.setSlashAngle(basisAttackAnimation.getSlashAngle());
                spawnedEntity.setCasterAndAnimation(playerPatch, basisAttackAnimation);
            }

            SoundEvent cast1 = SoundEvents.TRIDENT_RIPTIDE_1;
            SoundEvent cast2 = SoundEvents.TRIDENT_RIPTIDE_2;
            SoundEvent cast3 = SoundEvents.TRIDENT_RIPTIDE_3;
            int castIndex = (int) Mth.randomBetween(RandomSource.create(), 0, 2);
            SoundEvent castSound = castIndex == 0 ? cast1 : castIndex == 1 ? cast2 : cast3;

            serverLevel.playSound(
                    null,
                    blockPos,
                    castSound,
                    SoundSource.PLAYERS,
                    1.0F,
                    1.0F
            );
        }
    }

    @Override
    public void startCharging(PlayerPatch<?> playerPatch) {
        playerPatch.playAnimationSynchronized(ArbitersBladeAnimations.ARBITERS_BLADE_SKILL_ACTIVATE_START, 0.1F);
    }

    @Override
    public void resetCharging(PlayerPatch<?> playerPatch) {

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
    public void castSkill(ServerPlayerPatch serverPlayerPatch, SkillContainer skillContainer, int i, SPSkillExecutionFeedback spSkillExecutionFeedback, boolean b) {

    }

    @Override
    public void gatherChargingArguemtns(LocalPlayerPatch localPlayerPatch, ControllEngine controllEngine, FriendlyByteBuf friendlyByteBuf) {

    }

    @Override
    public KeyMapping getKeyMapping() {
        return EpicFightKeyMappings.WEAPON_INNATE_SKILL;
    }
}
