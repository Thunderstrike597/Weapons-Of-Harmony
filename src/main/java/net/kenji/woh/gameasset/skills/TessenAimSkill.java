package net.kenji.woh.gameasset.skills;

import com.google.common.collect.Lists;
import net.kenji.woh.WeaponsOfHarmony;
import net.kenji.woh.gameasset.WohWeaponCategories;
import net.kenji.woh.network.TessenInnatePacket;
import net.kenji.woh.network.WohPacketHandler;
import net.kenji.woh.registry.animation.TessenAnimations;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import yesman.epicfight.client.input.EpicFightKeyMappings;
import yesman.epicfight.gameasset.EpicFightSkills;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.skill.guard.GuardSkill;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;

import java.util.*;

public class TessenAimSkill extends GuardSkill {

    public static Map<UUID, Float> storedResource = new HashMap<>();

    public static Map<UUID, Boolean> wasDown = new HashMap<>();
    public static Map<UUID, Float> counter = new HashMap<>();

    public TessenAimSkill(Builder builder) {
        super(builder);
        this.maxDuration = 420;
        this.consumption = 20;
        this.maxStackSize = 1;
        guardMotions.put(
                WohWeaponCategories.TESSEN,
                (item, player) -> {
                    return TessenAnimations.TESSEN_SKILL_HOLD;
                }
        );
    }
    @Mod.EventBusSubscriber(modid = WeaponsOfHarmony.MODID, value = Dist.CLIENT)
    public static class ClientInputTracker {

        @SubscribeEvent
        public static void onClientTick(TickEvent.ClientTickEvent event) {
            if (event.phase != TickEvent.Phase.END) return;

            Minecraft mc = Minecraft.getInstance();
            LocalPlayer player = mc.player;
            if (player == null) return;

            UUID id = player.getUUID();

            if (EpicFightKeyMappings.GUARD.isDown()) {
                if (!wasDown.getOrDefault(id, false)) {
                    // Only send when state changes
                    wasDown.put(id, true);
                    WohPacketHandler.sendToServer(new TessenInnatePacket(id, true));
                }
                counter.put(id, 0f);
            } else if (wasDown.getOrDefault(id, false)) {
                counter.put(id, counter.getOrDefault(id, 0f) + 1f);

                if (counter.get(id) >= 2.5) {
                    wasDown.put(id, false);
                    counter.put(id, 0f);
                    WohPacketHandler.sendToServer(new TessenInnatePacket(id, false));
                }
            }
        }
    }

    public static boolean isAiming(PlayerPatch<?> patch) {
        UUID id = patch.getOriginal().getUUID();
        return wasDown.getOrDefault(id, false);
    }

    @Override
    protected boolean isBlockableSource(DamageSource damageSource, boolean advanced) {
        return false;
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
