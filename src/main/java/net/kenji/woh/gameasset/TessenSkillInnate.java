package net.kenji.woh.gameasset;

import net.kenji.woh.WeaponsOfHarmony;
import net.kenji.woh.registry.animation.ShotogatanaAnimations;
import net.kenji.woh.registry.animation.TessenAnimations;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fml.common.Mod;
import yesman.epicfight.api.animation.types.BasicAttackAnimation;
import yesman.epicfight.client.world.capabilites.entitypatch.player.LocalPlayerPatch;
import yesman.epicfight.gameasset.EpicFightSkills;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.skill.SkillBuilder;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.skill.weaponinnate.WeaponInnateSkill;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.capabilities.item.WeaponCapability;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TessenSkillInnate extends WeaponInnateSkill {

    public static Map<UUID, Float> storedResource = new HashMap<>();

    public TessenSkillInnate(SkillBuilder<? extends WeaponInnateSkill> builder) {
        super(builder);
        this.maxDuration = 420;
        this.consumption = 20;
        this.maxStackSize = 1;
    }

    @Override
    public ResourceLocation getSkillTexture() {
        return EpicFightSkills.LIECHTENAUER.getSkillTexture();
    }

    public ItemStack lastMainHandItem = ItemStack.EMPTY;

    boolean isActivated;
    @Mod.EventBusSubscriber(modid = WeaponsOfHarmony.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static class EventHandler {
        private static final Map<UUID, Boolean> didFirstAttack = new HashMap<>();

        private static final Map<UUID, BasicAttackAnimation> storedOriginalAttacks = new HashMap<>();
    }

    @Override
    public boolean canExecute(SkillContainer container) {
        // First check the base animation conditions
        ServerPlayerPatch executor = container.getServerExecutor();

        if(!executor.getSkill(this).isActivated()){

            CapabilityItem mainHandCap = executor.getHoldingItemCapability(InteractionHand.MAIN_HAND);
            CapabilityItem offHandCap = executor.getHoldingItemCapability(InteractionHand.OFF_HAND);
            if(offHandCap instanceof WeaponCapability offHandWeaponCap) {
                if (mainHandCap instanceof WeaponCapability weaponCap) {
                   if(offHandWeaponCap.checkOffhandValid(executor)) {
                       if (weaponCap.checkOffhandValid(executor)) {
                           if (executor.getValidItemInHand(InteractionHand.OFF_HAND) != ItemStack.EMPTY) {
                               return true;
                           }
                       }
                   }
                }
            }
            return false;
        }

        return true;
    }

    @Override
    public void onInitiate(SkillContainer container) {
        super.onInitiate(container);

        if (!(container.getExecutor() instanceof ServerPlayerPatch serverPatch)) return;

        UUID playerId = serverPatch.getOriginal().getUUID();

        Float stored = storedResource.get(playerId);
        if (stored != null) {
            setConsumptionSynchronize(container, stored);
        }
    }
    @Override
    public void onRemoved(SkillContainer container) {
        if (container.getExecutor().getOriginal() != null) {
            UUID playerId = container.getExecutor().getOriginal().getUUID();

            storedResource.put(playerId, container.getResource());
        }
    }

    @Override
    public void updateContainer(SkillContainer container) {
        super.updateContainer(container);
    }

    @Override
    public void executeOnServer(SkillContainer container, FriendlyByteBuf args) {

            // First check the base animation conditions
            ServerPlayerPatch executor = container.getServerExecutor();

        executor.playSound(SoundEvents.ARMOR_EQUIP_IRON, 0.0F, 0.0F);
        if (executor.getSkill(this).isActivated()) {
            this.cancelOnServer(container, args);
        } else {
            super.executeOnServer(container, args);
            executor.getSkill(this).activate();
            executor.modifyLivingMotionByCurrentItem(false);
            executor.playAnimationSynchronized(TessenAnimations.TESSEN_SKILL_ACTIVATE, 0.15F);
        }
    }
    @Override
    public void cancelOnServer(SkillContainer container, FriendlyByteBuf args) {

        // First check the base animation conditions
        ServerPlayerPatch executor = container.getServerExecutor();
        executor.getSkill(this).deactivate();
        super.cancelOnServer(container, args);
        executor.modifyLivingMotionByCurrentItem(false);
        executor.playAnimationSynchronized(TessenAnimations.TESSEN_SKILL_DEACTIVATE, 0.15F);
          if(executor.getSkill(this) != null) {
              setConsumptionSynchronize(container,0);
              setStackSynchronize(container, 0);
          }
    }
    @Override
    public void executeOnClient(SkillContainer container, FriendlyByteBuf args) {
        // First check the base animation conditions
        ServerPlayerPatch executor = container.getServerExecutor();
        super.executeOnClient(container, args);
        executor.getSkill(this).activate();
    }
    @Override
    public void cancelOnClient(SkillContainer container, FriendlyByteBuf args) {
        // First check the base animation conditions
        ServerPlayerPatch executor = container.getServerExecutor();
        super.cancelOnClient(container, args);
        executor.getSkill(this).deactivate();
    }
}
