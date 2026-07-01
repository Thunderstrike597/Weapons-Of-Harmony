package net.kenji.woh.gameasset.skills;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.p1nero.invincible.api.skill.ComboNode;
import com.p1nero.invincible.capability.InvincibleCapabilities;
import com.p1nero.invincible.capability.InvinciblePlayer;
import net.corruptdog.cdm.gameasset.CorruptAnimations;
import net.kenji.woh.api.basegameassets.HybridSkill;
import net.kenji.woh.api.interfaces.ITranslatableSkill;
import net.kenji.woh.api.manager.ComboBasicAttackCounterManager;
import net.kenji.woh.api.manager.TenraiManager;
import net.kenji.woh.network.ClientShotogatanaSkillPacket;
import net.kenji.woh.network.ClientTenraiSkillActivatePacket;
import net.kenji.woh.network.WohPacketHandler;
import net.kenji.woh.registry.animation.ShotogatanaAnimations;
import net.kenji.woh.registry.animation.TenraiAnimations;
import net.minecraft.ChatFormatting;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ItemStack;
import org.jline.utils.Log;
import yesman.epicfight.api.animation.AnimationManager;
import yesman.epicfight.api.animation.AnimationPlayer;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.animation.types.DynamicAnimation;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.gameasset.EpicFightSkills;
import yesman.epicfight.skill.*;
import yesman.epicfight.skill.weaponinnate.WeaponInnateSkill;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ShotogatanaSkillInnate extends HybridSkill {

    public static Map<UUID, Float> storedResource = new HashMap<>();
    public final Map<Integer, AnimationManager.AnimationAccessor<? extends AttackAnimation>> comboAnimation = Maps.newHashMap();


    public ShotogatanaSkillInnate(SkillBuilder<? extends Skill> builder) {
        super(builder, 1.0F);
        this.maxDuration = 420;
        this.consumption = 2;
        this.maxStackSize = 3;
    }
    public ShotogatanaSkillInnate(SkillBuilder<? extends Skill> builder, float stackChargeTime) {
        super(builder, stackChargeTime);
        this.maxDuration = 420;
        this.consumption = 2;
        this.maxStackSize = 3;
    }

    @Override
    public boolean canExecute(SkillContainer container) {
        if (!container.isActivated()) {
            PlayerPatch<?> executor = container.getExecutor();
            AnimationPlayer animPlayer = executor.getAnimator().getPlayerFor(null);
            if(container.getExecutor().getOriginal().isCreative())
                return true;
            if (animPlayer == null) {
                Log.info("Logging Skill CanExecute!!");

                return super.checkExecuteCondition(container);
            }

            DynamicAnimation animation = animPlayer.getAnimation().get();
            if (animation.isBasicAttackAnimation() || animation instanceof AttackAnimation) {
                return container.getStack() > 0;
            }
            /*else{
                return container.getStack() >= container.getSkill().getMaxStack();
            }*/
        }
        return true;
    }



    @Override
    public void sendSkillActivateToClient(boolean value, ServerPlayer serverPlayer) {
        WohPacketHandler.sendToPlayer(new ClientTenraiSkillActivatePacket(value), serverPlayer);
    }

    @Override
    public ResourceLocation getSkillTexture() {
        return EpicFightSkills.RELENTLESS_COMBO.getSkillTexture();
    }

    public ItemStack lastMainHandItem = ItemStack.EMPTY;

    boolean isActivated;
    @Override
    public String getSkillName() {
        return "Shotogatana Skill";
    }
    @Override
    public String getSkillTooltip() {
        return """
               §aThere are two states for this Innate Skill:§r
               
               1: Activating the Innate Skill while attacking allows you to perform a unique
               combo depending on the auto attack that was being performed when the Innate Was Activated.
               
               2: You have a cooldown meter you your innate skill.
              
               §bIf the cooldown is charged and you activate the innate while idle (Not Attacking), you can unsheathe your weapon and you
               will have a completely new moveset, performing two-handed slashes with each attack.§r
               """;
    }

    @Override
    public String getSkillTooltipExtra() {
        return "This Innate Skill(State 2) Lasts for: ";
    }

    @Override
    public void updateContainer(SkillContainer container) {
        super.updateContainer(container);
        tickHoldCooldown();
    }

    @Override
    public void onInitiate(SkillContainer container) {
        super.onInitiate(container);

        if (!(container.getExecutor() instanceof ServerPlayerPatch serverPatch)) return;

        UUID playerId = serverPatch.getOriginal().getUUID();
        if (container.getExecutor() instanceof ServerPlayerPatch serverPlayer
                && serverPlayer.getOriginal().connection != null) {
            Float stored = storedResource.get(playerId);
            if (stored != null) {
                setConsumptionSynchronize(container, stored);
            }
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
    public void executeOnServer(SkillContainer container, FriendlyByteBuf args) {
        ServerPlayerPatch executor = container.getServerExecutor();
        AnimationPlayer animPlayer = executor.getAnimator().getPlayerFor(null);
        if(animPlayer == null)
            return;
        DynamicAnimation animation = animPlayer.getAnimation().get();
        InvinciblePlayer invinciblePlayer = InvincibleCapabilities.getPlayerCap(executor.getOriginal());
        SkillContainer basicAttackContainer = executor.getSkill(EpicFightSkills.BASIC_ATTACK);
        ComboNode node = invinciblePlayer.getCurrentLogicNode();
        if(node == null)return;
        int comboCounter = ComboBasicAttackCounterManager.getInvincibleComboCounter(executor.getOriginal());
        AssetAccessor<? extends AttackAnimation> next = this.comboAnimation.get(comboCounter);
        if(next == null){
            for(int i = this.comboAnimation.size(); i > 0; i--){
                if(this.comboAnimation.get(i) != null){
                    next = this.comboAnimation.get(i);
                    break;
                }
            }
        }

        if (next != null) {
            executor.playAnimationSynchronized(next, 0.0F);
        }

        /*else if(container.getStack() >= container.getSkill().getMaxStack() - 1 || container.isActivated() || container.getExecutor().getOriginal().isCreative()) {
            if (executor.getSkill(this).isActivated()) {
                this.cancelOnServer(container, args);
            } else {
                setMaxHoldCooldown();
                TenraiManager.resetWeaponCounter(executor.getOriginal());
                if(container.getExecutor() instanceof ServerPlayerPatch serverPlayerPatch)
                    WohPacketHandler.sendToPlayer(new ClientTenraiSkillActivatePacket(true), serverPlayerPatch.getOriginal());

                super.executeOnServer(container, args);
                executor.getSkill(this).activate();
                executor.modifyLivingMotionByCurrentItem(false);
                executor.playAnimationSynchronized(TenraiAnimations.TENRAI_SKILL_ACTIVATE, 0.15F);
            }
        }*/
    }

    @Override
    public List<Component> getTooltipOnItem(ItemStack itemStack, CapabilityItem cap, PlayerPatch<?> playerCap) {
        List<Component> list = Lists.newArrayList();
        String traslatableText = this.getTranslationKey();
        list.add(Component.translatable(traslatableText).withStyle(ChatFormatting.WHITE)
                .append(Component.literal(String.format("[%.0f]", this.consumption))));
        list.add(Component.translatable(traslatableText + ".tooltip")
                .withStyle(ChatFormatting.AQUA));
        if(!getSkillTooltipExtra().isEmpty())
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

        TenraiManager.resetWeaponCounter(executor.getOriginal());

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
        TenraiManager.resetWeaponCounter(executor.getOriginal());
        super.executeOnClient(container, args);
        Log.info("Logging EXECUTE On CLIENT");
        executor.getSkill(this).activate();
    }
    @Override
    public void cancelOnClient(SkillContainer container, FriendlyByteBuf args) {
        // First check the base animation conditions
        ServerPlayerPatch executor = container.getServerExecutor();
        TenraiManager.resetWeaponCounter(executor.getOriginal());
        super.cancelOnClient(container, args);
        Log.info("Logging Cancel On CLIENT");
        executor.getSkill(this).deactivate();
    }

    private void addAnimationCombo(AnimationManager.AnimationAccessor<? extends AttackAnimation> animation){
        this.comboAnimation.put(this.comboAnimation.size(), animation);
    }
    @Override
    public Skill registerPropertiesToAnimation() {
        this.comboAnimation.clear();
        addAnimationCombo(CorruptAnimations.YAMATO_JUDGEMENT_CUT);
        addAnimationCombo(CorruptAnimations.YAMATO_JUDGEMENT_CUT);
        addAnimationCombo(CorruptAnimations.YAMATO_JUDGEMENT_CUT);

        addAnimationCombo(ShotogatanaAnimations.SHOTOGATANA_SKILL_COMBO_2);
        addAnimationCombo(ShotogatanaAnimations.SHOTOGATANA_SKILL_COMBO_3);

        return super.registerPropertiesToAnimation();
    }
}
