package net.kenji.woh.gameasset.skills;

import com.google.common.collect.Lists;
import net.kenji.woh.WeaponsOfHarmony;
import net.kenji.woh.registry.animation.ShotogatanaAnimations;
import net.kenji.woh.registry.animation.TsumeAnimations;
import net.minecraft.ChatFormatting;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fml.common.Mod;
import yesman.epicfight.api.animation.types.BasicAttackAnimation;
import yesman.epicfight.client.world.capabilites.entitypatch.player.LocalPlayerPatch;
import yesman.epicfight.gameasset.EpicFightSkills;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.skill.weaponinnate.WeaponInnateSkill;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class NewShotogatanaSkillInnate extends WeaponInnateSkill {

    public static Map<UUID, Float> storedResource = new HashMap<>();


    public NewShotogatanaSkillInnate(Builder<? extends Skill> builder) {
        super(builder);
        this.maxDuration = 420;
        this.consumption = 20;
        this.maxStackSize = 1;
    }

    @Override
    public ResourceLocation getSkillTexture() {
        return EpicFightSkills.RELENTLESS_COMBO.getSkillTexture();
    }


    public ItemStack lastMainHandItem = ItemStack.EMPTY;

    boolean isActivated;
    @Mod.EventBusSubscriber(modid = WeaponsOfHarmony.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static class EventHandler {
        private static final Map<UUID, Boolean> didFirstAttack = new HashMap<>();

        private static final Map<UUID, BasicAttackAnimation> storedOriginalAttacks = new HashMap<>();
    }

    @Override
    public void updateContainer(SkillContainer container) {
        super.updateContainer(container);
    }


    @Override
    public void onInitiate(SkillContainer container) {
        super.onInitiate(container);

        if (!(container.getExecuter() instanceof ServerPlayerPatch serverPatch)) return;

        UUID playerId = serverPatch.getOriginal().getUUID();

        Float stored = storedResource.get(playerId);
        if (stored != null) {
            setConsumptionSynchronize(serverPatch, stored);
        }
    }
    @Override
    public void onRemoved(SkillContainer container) {
        if (container.getExecuter().getOriginal() != null) {
            UUID playerId = container.getExecuter().getOriginal().getUUID();

            storedResource.put(playerId, container.getResource());
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
    public void executeOnServer(ServerPlayerPatch executer, FriendlyByteBuf args) {
        executer.playSound(SoundEvents.ARMOR_EQUIP_IRON, 0.0F, 0.0F);
        if (executer.getSkill(this).isActivated()) {
            this.cancelOnServer(executer, args);
        } else {
            super.executeOnServer(executer, args);
            executer.getSkill(this).activate();
            executer.modifyLivingMotionByCurrentItem(false);
            executer.playAnimationSynchronized(ShotogatanaAnimations.SHOTOGATANA_UNSHEATH, 0.15F);
        }
    }
    @Override
    public void cancelOnServer(ServerPlayerPatch executer, FriendlyByteBuf args) {
        executer.getSkill(this).deactivate();
        super.cancelOnServer(executer, args);
        executer.modifyLivingMotionByCurrentItem(false);
        executer.playAnimationSynchronized(ShotogatanaAnimations.SHOTOGATANA_SHEATH, 0.15F);
        if(executer.getSkill(this) != null) {
            setConsumptionSynchronize(executer,0);
            setStackSynchronize(executer, 0);
        }
    }
    @Override
    public void executeOnClient(LocalPlayerPatch executer, FriendlyByteBuf args) {
        super.executeOnClient(executer, args);
        executer.getSkill(this).activate();
    }
    @Override
    public void cancelOnClient(LocalPlayerPatch executer, FriendlyByteBuf args) {
        super.cancelOnClient(executer, args);
        executer.getSkill(this).deactivate();
    }
}
