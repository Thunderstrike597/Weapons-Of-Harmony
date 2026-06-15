package net.kenji.woh.api.manager;

import net.kenji.woh.WeaponsOfHarmony;
import net.kenji.woh.gameasset.WohSkills;
import net.kenji.woh.gameasset.skills.TenraiSkillInnate;
import net.kenji.woh.item.custom.weapon.Shotogatana;
import net.kenji.woh.item.custom.weapon.Tenrai;
import net.kenji.woh.network.SplitStatePacket;
import net.kenji.woh.network.WohPacketHandler;
import net.kenji.woh.registry.WohItems;
import net.kenji.woh.registry.animation.TenraiAnimations;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jline.utils.Log;
import yesman.epicfight.api.animation.AnimationPlayer;
import yesman.epicfight.api.animation.types.DynamicAnimation;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Mod.EventBusSubscriber(modid = WeaponsOfHarmony.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class TenraiManager {
    public static final Map<UUID, Boolean> hasSetupWeapon = new HashMap<>();
    public static Map<UUID, Boolean> renderSplitMap = new HashMap<>();

    public static void setWeaponSplit(LivingEntity player, boolean split){
        UUID playerID = player.getUUID();
        TenraiManager.renderSplitMap.put(playerID, split);
        if(player instanceof ServerPlayer serverPlayer)
            WohPacketHandler.sendToPlayer(new SplitStatePacket(playerID, split), serverPlayer);
    }
    public static boolean getWeaponSplit(LivingEntity player){
        UUID playerID = player.getUUID();
        return TenraiManager.renderSplitMap.getOrDefault(playerID, false);
    }

    public static void resetWeaponCounter(LivingEntity player){
        player.getMainHandItem().getOrCreateTag().putInt("tenrai_split_counter", 40);
    }
    @SubscribeEvent
    public static void onPlayerLeave(PlayerEvent.PlayerLoggedOutEvent event) {
        UUID playerId = event.getEntity().getUUID();
        hasSetupWeapon.remove(playerId);
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        UUID playerId = event.player.getUUID();
        Player player = event.player;
        boolean hasSetup = hasSetupWeapon.getOrDefault(playerId, false);
        ItemStack stack = player.getMainHandItem();
        CompoundTag tag = stack.getOrCreateTag();
        if (event.player.getMainHandItem().getItem() == WohItems.TENRAI.get()) {
            if (!hasSetup) {
                hasSetupWeapon.put(playerId, true);
                TenraiManager.setWeaponSplit(player, false);
            }
        }
        PlayerPatch<?> playerPatch = EpicFightCapabilities.getPlayerPatch(player);
        if (playerPatch == null) return;
        if (event.player.level().isClientSide()) return;

        AnimationPlayer animPlayer = playerPatch.getAnimator().getPlayerFor(null);
        if (playerPatch.getSkill(WohSkills.SPLIT_TENRAI) == null) return;
        SkillContainer container = playerPatch.getSkill(WohSkills.SPLIT_TENRAI);
        if (animPlayer == null) return;

        DynamicAnimation anim = animPlayer.getAnimation().get();
        if(anim != TenraiAnimations.TENRAI_SKILL_ACTIVATE.get() && anim != TenraiAnimations.TENRAI_SKILL_DEACTIVATE.get()) {
            if (tag.getInt("tenrai_split_counter") <= 0) {
                if (!container.isActivated()) {
                    if(container.getSkill() instanceof TenraiSkillInnate skill) {
                        if(!skill.comboAnimation.containsValue(anim.getAccessor()))
                            TenraiManager.setWeaponSplit(player, false);
                    }
                }
                else TenraiManager.setWeaponSplit(player, true);
            } else {
                int counter = tag.getInt("tenrai_split_counter");
                tag.putInt("tenrai_split_counter", counter - 1);
            }
        }
    }

    @SubscribeEvent
    public static void onDeath(PlayerEvent.PlayerRespawnEvent event) {
        UUID playerId = event.getEntity().getUUID();
        hasSetupWeapon.replace(playerId, false);
    }
}
