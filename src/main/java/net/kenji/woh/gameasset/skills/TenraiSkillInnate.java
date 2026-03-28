package net.kenji.woh.gameasset.skills;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.kenji.woh.WeaponsOfHarmony;
import net.kenji.woh.api.interfaces.ITranslatableSkill;
import net.kenji.woh.api.manager.TenraiManager;
import net.kenji.woh.network.ClientTenraiSkillActivatePacket;
import net.kenji.woh.network.WohPacketHandler;
import net.kenji.woh.registry.animation.ShotogatanaAnimations;
import net.kenji.woh.registry.animation.TenraiAnimations;
import net.kenji.woh.registry.animation.TessenAnimations;
import net.minecraft.ChatFormatting;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fml.common.Mod;
import org.jline.utils.Log;
import yesman.epicfight.api.animation.AnimationManager;
import yesman.epicfight.api.animation.AnimationPlayer;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.animation.types.BasicAttackAnimation;
import yesman.epicfight.api.animation.types.DynamicAnimation;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.api.client.animation.ClientAnimator;
import yesman.epicfight.api.client.animation.Layer;
import yesman.epicfight.client.events.engine.ControlEngine;
import yesman.epicfight.client.world.capabilites.entitypatch.player.LocalPlayerPatch;
import yesman.epicfight.gameasset.EpicFightSkills;
import yesman.epicfight.skill.SkillBuilder;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.skill.weaponinnate.WeaponInnateSkill;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;

import java.util.*;

public class TenraiSkillInnate extends WeaponInnateSkill implements ITranslatableSkill {

    public static Map<UUID, Float> storedResource = new HashMap<>();
    public final Map<AnimationManager.AnimationAccessor<? extends StaticAnimation>, AnimationManager.AnimationAccessor<? extends AttackAnimation>> comboAnimation = Maps.newHashMap();

    private int previousStack;

    public TenraiSkillInnate(SkillBuilder<? extends WeaponInnateSkill> builder) {
        super(builder);
        this.maxDuration = 420;
        this.consumption = 20;
        this.maxStackSize = 1;
    }

    @Override
    public boolean canExecute(SkillContainer container) {
        if (!container.isActivated()) {
            return super.checkExecuteCondition(container);
        }
        return true;
    }
    @Override
    public ResourceLocation getSkillTexture() {
        return EpicFightSkills.RELENTLESS_COMBO.getSkillTexture();
    }

    public ItemStack lastMainHandItem = ItemStack.EMPTY;

    boolean isActivated;
    @Override
    public String getSkillName() {
        return "Tenrai Split";
    }
    @Override
    public String getSkillTooltip() {
        return "";
    }

    @Override
    public String getSkillTooltipExtra() {
        return "";
    }

    @Mod.EventBusSubscriber(modid = WeaponsOfHarmony.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static class EventHandler {
        private static final Map<UUID, Boolean> didFirstAttack = new HashMap<>();

        private static final Map<UUID, BasicAttackAnimation> storedOriginalAttacks = new HashMap<>();
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
        if (!container.isActivated()) {
            PlayerPatch<?> executor = container.getExecutor();
            AnimationPlayer animPlayer = executor.getAnimator().getPlayerFor(null);
            if(container.getStack() != maxStackSize) {

                if (animPlayer == null) {
                    return;
                }
                DynamicAnimation animation = animPlayer.getAnimation().get();
                if (animation.isBasicAttackAnimation() || animation instanceof AttackAnimation) {
                    AssetAccessor<? extends DynamicAnimation> current =
                            animPlayer.getAnimation();
                    AssetAccessor<? extends AttackAnimation> next = this.comboAnimation.get(current.get().getAccessor());
                    if (next != null) {
                        this.previousStack = container.getStack();
                        container.setStack(maxStackSize);
                    }
                } else {
                    container.setStack(this.previousStack);
                }
            }
        }
    }

    @Override
    public void executeOnServer(SkillContainer container, FriendlyByteBuf args) {
        ServerPlayerPatch executor = container.getServerExecutor();
        AnimationPlayer animPlayer = executor.getAnimator().getPlayerFor(null);
        if(animPlayer == null)
            return;
        DynamicAnimation animation = animPlayer.getAnimation().get();
        if(animation.isBasicAttackAnimation() || animation instanceof AttackAnimation) {
            AssetAccessor<? extends DynamicAnimation> current =
                    animPlayer.getAnimation();
            AssetAccessor<? extends AttackAnimation> next = this.comboAnimation.get(current.get().getAccessor());

            if (next != null) {
                executor.playAnimationSynchronized(next, 0.0F);
            }
            executor.playSound(SoundEvents.ARMOR_EQUIP_IRON, 0.0F, 0.0F);
        }
        else {
            if (executor.getSkill(this).isActivated()) {
                this.cancelOnServer(container, args);
            } else {
                TenraiManager.pauseRenderSplitMap.put(executor.getOriginal().getUUID(), 40);
                if(container.getExecutor() instanceof ServerPlayerPatch serverPlayerPatch)
                    WohPacketHandler.sendToPlayer(new ClientTenraiSkillActivatePacket(true), serverPlayerPatch.getOriginal());

                super.executeOnServer(container, args);
                executor.getSkill(this).activate();
                executor.modifyLivingMotionByCurrentItem(false);
                executor.playAnimationSynchronized(TenraiAnimations.TENRAI_SKILL_ACTIVATE, 0.15F);
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
        list.add(Component.translatable(traslatableText + ".tooltip.extra", this.maxDuration)
                .withStyle(ChatFormatting.RED).append(String.valueOf(this.maxDuration / 20)));
        return list;
    }


    @Override
    public void cancelOnServer(SkillContainer container, FriendlyByteBuf args) {
        if(container.getExecutor() instanceof ServerPlayerPatch serverPlayerPatch)
            WohPacketHandler.sendToPlayer(new ClientTenraiSkillActivatePacket(false), serverPlayerPatch.getOriginal());
        // First check the base animation conditions
        ServerPlayerPatch executor = container.getServerExecutor();

        TenraiManager.pauseRenderSplitMap.put(executor.getOriginal().getUUID(), 40);


        executor.getSkill(this).deactivate();
        super.cancelOnServer(container, args);
        executor.modifyLivingMotionByCurrentItem(false);
        executor.playAnimationSynchronized(TenraiAnimations.TENRAI_SKILL_DEACTIVATE, 0.15F);
        if(executor.getSkill(this) != null) {
            setConsumptionSynchronize(container,0);
            setStackSynchronize(container, 0);
        }
    }
    @Override
    public void executeOnClient(SkillContainer container, FriendlyByteBuf args) {
        // First check the base animation conditions
        ServerPlayerPatch executor = container.getServerExecutor();
        TenraiManager.pauseRenderSplitMap.put(executor.getOriginal().getUUID(), 40);

        super.executeOnClient(container, args);
        executor.getSkill(this).activate();
    }
    @Override
    public void cancelOnClient(SkillContainer container, FriendlyByteBuf args) {
        // First check the base animation conditions
        ServerPlayerPatch executor = container.getServerExecutor();
        TenraiManager.pauseRenderSplitMap.put(executor.getOriginal().getUUID(), 40);

        super.cancelOnClient(container, args);
        executor.getSkill(this).deactivate();
    }
    @Override
    public WeaponInnateSkill registerPropertiesToAnimation() {
        this.comboAnimation.clear();
        this.comboAnimation.put(TenraiAnimations.TENRAI_AUTO_1, TenraiAnimations.TENRAI_SKILL_COMBO_1);
        this.comboAnimation.put(TenraiAnimations.TENRAI_AUTO_2, TenraiAnimations.TENRAI_SKILL_COMBO_2);
        this.comboAnimation.put(TenraiAnimations.TENRAI_AUTO_3, TenraiAnimations.TENRAI_SKILL_COMBO_3);

        return this;
    }
}