package net.kenji.woh.api.manager;

import net.kenji.woh.WeaponsOfHarmony;
import net.kenji.woh.gameasset.WohSkills;
import net.kenji.woh.gameasset.skills.TenraiSkillInnate;
import net.kenji.woh.item.custom.weapon.Shotogatana;
import net.kenji.woh.item.custom.weapon.Tenrai;
import net.kenji.woh.registry.WohItems;
import net.kenji.woh.registry.animation.TenraiAnimations;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
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
        if(!(player.getMainHandItem().getItem() instanceof Tenrai)){
            if(player.getMainHandItem().getTag() != null){
                player.getMainHandItem().getTag().remove("is_tenrai_split");
                player.getMainHandItem().getTag().remove("tenrai_split_counter");

            }
            return;
        }
        player.getMainHandItem().getOrCreateTag().putBoolean("is_tenrai_split", split);
    }
    public static boolean getWeaponSplit(LivingEntity player){
        if(!(player.getMainHandItem().getItem() instanceof Tenrai)){
            if(player.getMainHandItem().getTag() != null){
                player.getMainHandItem().getTag().remove("is_tenrai_split");
                player.getMainHandItem().getTag().remove("tenrai_split_counter");

            }
            return false;
        }
        return player.getMainHandItem().getOrCreateTag().getBoolean("is_tenrai_split");
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
