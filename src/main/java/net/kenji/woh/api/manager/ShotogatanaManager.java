package net.kenji.woh.api.manager;

import net.corruptdog.cdm.gameasset.CorruptAnimations;
import net.kenji.woh.WeaponsOfHarmony;
import net.kenji.woh.gameasset.WohStyles;
import net.kenji.woh.item.custom.weapon.Shotogatana;
import net.kenji.woh.registry.WohItems;
import net.kenji.woh.registry.animation.ShotogatanaAnimations;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import yesman.epicfight.api.animation.LivingMotions;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.capabilities.item.Style;
import yesman.epicfight.world.capabilities.item.WeaponCapability;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
@Mod.EventBusSubscriber(modid = WeaponsOfHarmony.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ShotogatanaManager {
    public static final Map<UUID, Boolean> hasSetupWeapon = new HashMap<>();
    public static final Map<UUID, Integer> queSheathCounter = new HashMap<>();
    public static Map<UUID, Boolean> renderSheathMap = new HashMap<>();
    public static Map<UUID, Style> sheathStyleMap = new HashMap<>();
    public static Map<UUID, Boolean> sheathPauseMap = new HashMap<>();


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
        if (event.player.getMainHandItem().getItem() == WohItems.SHOTOGATANA.get()) {
            if (!hasSetup) {
                hasSetupWeapon.put(playerId, true);
                queSheathCounter.put(playerId, 20);
                renderSheathMap.put(playerId, true);
            }
        }
        if (player.getMainHandItem().getItem() instanceof Shotogatana shotogatana) {
            player.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY).ifPresent(cap -> {
                if (cap instanceof PlayerPatch<?> playerPatch) {
                    boolean sheathed = renderSheathMap.getOrDefault(playerId, false);
                    CapabilityItem capItem = playerPatch.getHoldingItemCapability(InteractionHand.MAIN_HAND);

                    if (playerPatch instanceof ServerPlayerPatch serverPlayerPatch) {
                        if (sheathStyleMap.getOrDefault(playerId, capItem.getStyle(playerPatch)) != capItem.getStyle(playerPatch)) {
                            if(!sheathPauseMap.getOrDefault(playerId, false))
                                serverPlayerPatch.modifyLivingMotionByCurrentItem();
                        }
                        sheathStyleMap.put(playerId, capItem.getStyle(playerPatch));
                    }
                }
            });
        }
    }

    @SubscribeEvent
    public static void onDeath(PlayerEvent.PlayerRespawnEvent event) {
        UUID playerId = event.getEntity().getUUID();
        hasSetupWeapon.replace(playerId, false);
    }
}
