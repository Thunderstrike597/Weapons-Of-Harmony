package net.kenji.woh.gameasset.skills;

import com.google.common.collect.Lists;
import net.kenji.woh.WeaponsOfHarmony;
import net.kenji.woh.api.manager.AimManager;
import net.kenji.woh.gameasset.WohSkills;
import net.kenji.woh.gameasset.WohWeaponCategories;
import net.kenji.woh.network.WohPacketHandler;
import net.kenji.woh.registry.animation.TessenAnimations;
import net.minecraft.ChatFormatting;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jline.utils.Log;
import yesman.epicfight.api.animation.LivingMotions;
import yesman.epicfight.client.input.EpicFightKeyMappings;
import yesman.epicfight.gameasset.EpicFightSkills;
import yesman.epicfight.server.commands.arguments.EpicFightCommandArgumentTypes;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.skill.SkillBuilder;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.skill.SkillDataKey;
import yesman.epicfight.skill.guard.GuardSkill;
import yesman.epicfight.skill.modules.HoldableSkill;
import yesman.epicfight.skill.weaponinnate.WeaponInnateSkill;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.entity.eventlistener.PlayerEventListener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class TessenAimSkill extends WeaponInnateSkill {

    private static UUID EVENT_UUID = UUID.randomUUID();

    public TessenAimSkill(SkillBuilder<WeaponInnateSkill> builder) {
        super(builder);
    }

    @Mod.EventBusSubscriber(modid = WeaponsOfHarmony.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static class CommonEvents {

        @SubscribeEvent
        public static void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event){
            event.getEntity().getCapability(EpicFightCapabilities.CAPABILITY_ENTITY).ifPresent(cap -> {
                if(cap instanceof PlayerPatch<?> playerPatch){
                //    playerPatch.getSkillCapability().addLearnedSkill(WohSkills.FAN_STANCE);
                }
            });
        }

    }


    @OnlyIn(Dist.CLIENT)
    public void onInitiateClient(SkillContainer container) {
        super.onInitiateClient(container);
        container.getExecutor().getEventListener().addEventListener(PlayerEventListener.EventType.UPDATE_COMPOSITE_LIVING_MOTION_EVENT, EVENT_UUID, (event) -> {
            if(AimManager.wasDown.getOrDefault(container.getExecutor().getOriginal().getUUID(), false)) {
                event.setMotion(LivingMotions.BLOCK);
            }
        });
    }

    public static boolean isAiming(PlayerPatch<?> patch) {
        UUID id = patch.getOriginal().getUUID();
        return AimManager.wasDown.getOrDefault(id, false);
    }

    @Override
    public ResourceLocation getSkillTexture() {
        return EpicFightSkills.LIECHTENAUER.getSkillTexture();
    }



    @Override
    public List<Component> getTooltipOnItem(ItemStack itemStack, CapabilityItem cap, PlayerPatch<?> playerCap) {
        List<Component> list = Lists.newArrayList();
        String traslatableText = this.getTranslationKey();
        list.add(Component.translatable(traslatableText).withStyle(ChatFormatting.WHITE)
                .append(Component.literal(String.format("[%.0f]", this.consumption)).withStyle(ChatFormatting.AQUA)));
        list.add(Component.translatable(traslatableText + ".tooltip")
                .withStyle(ChatFormatting.AQUA));
       return list;
    }


}
