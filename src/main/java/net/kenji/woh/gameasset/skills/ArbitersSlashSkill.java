package net.kenji.woh.gameasset.skills;

import com.google.common.collect.Lists;
import net.kenji.woh.WeaponsOfHarmony;
import net.kenji.woh.api.manager.AimManager;
import net.kenji.woh.entities.WohEntities;
import net.kenji.woh.entities.custom.BeamSlashEntity;
import net.kenji.woh.gameasset.WohWeaponCategories;
import net.kenji.woh.network.ArbitersSlashSetupPacket;
import net.kenji.woh.network.ClientArbitersSlashPacket;
import net.kenji.woh.network.WohPacketHandler;
import net.kenji.woh.registry.animation.ArbitersBladeAnimations;
import net.minecraft.ChatFormatting;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jline.utils.Log;
import yesman.epicfight.api.animation.AnimationPlayer;
import yesman.epicfight.api.animation.LivingMotions;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.animation.types.DynamicAnimation;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.client.events.engine.ControllEngine;
import yesman.epicfight.client.input.EpicFightKeyMappings;
import yesman.epicfight.client.world.capabilites.entitypatch.player.LocalPlayerPatch;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.gameasset.EpicFightSkills;
import yesman.epicfight.network.server.SPSkillExecutionFeedback;
import yesman.epicfight.skill.ChargeableSkill;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.skill.guard.GuardSkill;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;

import java.util.*;

public class ArbitersSlashSkill extends Skill implements ChargeableSkill {

    private static final Map<UUID, Boolean> wasHoldingMap = new HashMap<>();

    public static float travelSpeedMultiplier = 1.75f;
    public static Map<String, Integer> slashAngleMap = new HashMap<>();
    private static final Map<AttackAnimation, BeamSlashEntity> beamCastMap = new HashMap<>();

    public final int MAX_HOLD_COUNTER = 60;
    public int holdCounter = 0;

    public boolean scheduleDeactivate;
    private boolean isModifiedAnimation;

    public ArbitersSlashSkill(Builder builder) {
        super(builder);
        this.maxDuration = 420;
        this.consumption = 32;
        this.maxStackSize = 1;
    }

    @Mod.EventBusSubscriber(modid = WeaponsOfHarmony.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
    public static class ClientSubscribeEvents {
        @SubscribeEvent
        public static void onClientLogin(ClientPlayerNetworkEvent.LoggingIn event) {
            WohPacketHandler.sendToServer(new ArbitersSlashSetupPacket(ArbitersSlashSkill.slashAngleMap));
        }
    }

    @Override
    public boolean canExecute(PlayerPatch<?> playerPatch) {
        SkillContainer container = playerPatch.getSkill(this);
        if(!container.isActivated()) {
            return super.canExecute(playerPatch) && holdCounter <= 0;
        }
        else{
            container.getExecuter().playAnimationSynchronized(ArbitersBladeAnimations.ARBITERS_BLADE_SKILL_DEACTIVATE, 0.1F);
            scheduleDeactivate = true;
            holdCounter = MAX_HOLD_COUNTER;
        }
        return false;
    }

    @Override
    public ResourceLocation getSkillTexture() {
        return EpicFightSkills.TECHNICIAN.getSkillTexture();
    }

    @Override
    public void updateContainer(SkillContainer container) {

        if(holdCounter > 0){
            holdCounter--;
        }
        if(scheduleDeactivate) {
            if (container.getExecuter() instanceof ServerPlayerPatch serverPlayerPatch) {
                AnimationPlayer animPlayer = serverPlayerPatch.getServerAnimator().animationPlayer;
                DynamicAnimation anim = animPlayer.getAnimation();
                if (anim == ArbitersBladeAnimations.ARBITERS_BLADE_SKILL_DEACTIVATE.get()) {
                    if (animPlayer.getElapsedTime() > 0.58F) {
                        container.deactivate();
                        WohPacketHandler.sendToPlayer(new ClientArbitersSlashPacket(), serverPlayerPatch.getOriginal());
                        scheduleDeactivate = false;
                    }
                }
            }
        }
        if(!container.isActivated()) {
            if(container.getExecuter().getAnimator().getLivingAnimation(LivingMotions.IDLE, getAimAnimation()) == getAimAnimation())
                onAimRelease(container);

            if (getChargingAmount(container.getExecuter()) > 1 && getChargingAmount(container.getExecuter()) < getMaxChargingTicks()) {
                if(container.getExecuter() instanceof ServerPlayerPatch) {
                    AnimationPlayer animationPlayer = container.getExecuter().getAnimator().getPlayerFor(null);
                    if (animationPlayer != null) {
                        DynamicAnimation dynamicAnim = animationPlayer.getAnimation();
                        if(dynamicAnim instanceof StaticAnimation staticAnimation){
                            if (staticAnimation == ArbitersBladeAnimations.ARBITERS_BLADE_SKILL_ACTIVATE_START.get()) {

                                if (animationPlayer.getElapsedTime() > 1.2F) {
                                    container.getExecuter().playAnimationSynchronized(
                                            ArbitersBladeAnimations.ARBITERS_BLADE_SKILL_ACTIVATE_MID, 0.1F
                                    );
                                }
                            }
                        }
                    }
                }
            }
            if(container.getExecuter().getAnimator().getLivingAnimation(LivingMotions.BLOCK, ArbitersBladeAnimations.ARBITERS_BLADE_AIM) != Animations.LONGSWORD_GUARD)
                container.getExecuter().getAnimator().addLivingAnimation(LivingMotions.BLOCK, Animations.LONGSWORD_GUARD);
        }
        if (container.getExecuter().getSkillChargingTicks(1.0F) > (float)getAllowedMaxChargingTicks()) {
            if(!container.isActivated()) {
                container.getExecuter().playAnimationSynchronized(ArbitersBladeAnimations.ARBITERS_BLADE_SKILL_ACTIVATE_END, 0.1F);
                if(container.getExecuter().getOriginal().level() instanceof ServerLevel serverLevel) {
                    BlockPos blockPos = container.getExecuter().getOriginal().blockPosition();

                    serverLevel.playSound(
                            null,
                            blockPos,
                            SoundEvents.ENCHANTMENT_TABLE_USE,
                            SoundSource.PLAYERS,
                            1.0F,
                            1.0F
                    );
                }
            }
            container.activate();
        }


        if (container.isActivated()) {
            Player player = container.getExecuter().getOriginal();
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
            PlayerPatch<?> playerPatch = container.getExecuter();
            if (playerPatch.getAnimator().getPlayerFor(null).getAnimation() instanceof AttackAnimation attackAnim) {
                if(playerPatch.getOriginal().level() instanceof ServerLevel serverLevel) {
                    if (playerPatch.getAnimator().getPlayerFor(null).getElapsedTime() >= (attackAnim.phases[0].contact + attackAnim.phases[0].start) * 0.5) {
                        if(beamCastMap.get(attackAnim) == null || serverLevel.getEntity(beamCastMap.get(attackAnim).getUUID()) == null){
                            onBeamSlash(playerPatch, attackAnim, serverLevel);
                        }
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

    @Override
    public void executeOnClient(LocalPlayerPatch executor, FriendlyByteBuf args) {
        super.executeOnClient(executor, args);
    }

    private void onBeamSlash(PlayerPatch<?> playerPatch, AttackAnimation basisAttackAnimation, ServerLevel serverLevel){
        int slashAngle = slashAngleMap.getOrDefault(basisAttackAnimation.get().toString(), -1);
        if(serverLevel != null && slashAngle != -1) {
            BlockPos blockPos = playerPatch.getOriginal().blockPosition();
            BeamSlashEntity spawnedEntity = WohEntities.BEAM_SLASH.get().spawn(serverLevel, blockPos, MobSpawnType.TRIGGERED);
            Vec3 lookDir = playerPatch.getOriginal().getLookAngle();
            if (spawnedEntity != null) {
                float yVelocity = AimManager.isAiming(playerPatch) ? travelSpeedMultiplier : 0;

                spawnedEntity.addDeltaMovement(lookDir.multiply(travelSpeedMultiplier, yVelocity, travelSpeedMultiplier));
                spawnedEntity.setYRot(playerPatch.getOriginal().getYHeadRot());
                spawnedEntity.setSlashAngle(slashAngleMap.getOrDefault(basisAttackAnimation.get().toString(), -45));
                spawnedEntity.setCasterAndAnimation(playerPatch, basisAttackAnimation);

                beamCastMap.put(basisAttackAnimation, spawnedEntity);


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
                serverLevel.playSound(
                        null,
                        blockPos,
                        SoundEvents.ENCHANTMENT_TABLE_USE,
                        SoundSource.PLAYERS,
                        0.55F,
                        1.0F
                );
            }
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
    public void startCharging(PlayerPatch<?> playerPatch) {
        playerPatch.playAnimationSynchronized(ArbitersBladeAnimations.ARBITERS_BLADE_SKILL_ACTIVATE_START, 0.1F);
    }

    @Override
    public void resetCharging(PlayerPatch<?> playerPatch) {

    }

    @Override
    public int getAllowedMaxChargingTicks() {
        return 100;
    }

    @Override
    public int getMaxChargingTicks() {
        return 100;
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

    @Override
    public void onInitiate(SkillContainer container) {
        super.onInitiate(container);
    }

    private StaticAnimation getAimAnimation(){
        return ArbitersBladeAnimations.ARBITERS_BLADE_AIM;
    }

    private void onAimPress(SkillContainer container){
        container.getExecuter().getAnimator().addLivingAnimation(LivingMotions.IDLE, getAimAnimation());
        container.getExecuter().getAnimator().addLivingAnimation(LivingMotions.WALK, getAimAnimation());
        isModifiedAnimation = true;
    }
    private void onAimRelease(SkillContainer container){
        container.getExecuter().getAnimator().addLivingAnimation(LivingMotions.IDLE, container.getExecuter().getHoldingItemCapability(InteractionHand.MAIN_HAND).getLivingMotionModifier(container.getExecuter(), InteractionHand.MAIN_HAND).get(LivingMotions.IDLE).get());
        container.getExecuter().getAnimator().addLivingAnimation(LivingMotions.WALK, container.getExecuter().getHoldingItemCapability(InteractionHand.MAIN_HAND).getLivingMotionModifier(container.getExecuter(), InteractionHand.MAIN_HAND).get(LivingMotions.WALK).get());
        isModifiedAnimation = false;
    }

}
