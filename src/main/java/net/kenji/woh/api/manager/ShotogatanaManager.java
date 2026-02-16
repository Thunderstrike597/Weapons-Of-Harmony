package net.kenji.woh.api.manager;

import net.kenji.woh.WeaponsOfHarmony;
import net.kenji.woh.item.custom.weapon.Shotogatana;
import net.kenji.woh.registry.WohItems;
import net.kenji.woh.registry.animation.ShotogatanaAnimations;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import yesman.epicfight.api.animation.LivingMotions;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
@Mod.EventBusSubscriber(modid = WeaponsOfHarmony.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ShotogatanaManager {
    public static final Map<UUID, Boolean> sheathWeapon = new HashMap<>();
    public static final Map<UUID, Boolean> hasSetupWeapon = new HashMap<>();
    public static final Map<UUID, Integer> queSheathCounter = new HashMap<>();

    @SubscribeEvent
    public static void onPlayerLeave(PlayerEvent.PlayerLoggedOutEvent event) {
        UUID playerId = event.getEntity().getUUID();
        sheathWeapon.remove(playerId);
        hasSetupWeapon.remove(playerId);

    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        UUID playerId = event.player.getUUID();
        Player player = event.player;
        boolean hasSetup = hasSetupWeapon.getOrDefault(playerId, false);
        if (event.player.getMainHandItem().getItem() == WohItems.SHOTOGATANA.get()) {
            if (!hasSetup) {
                sheathWeapon.put(playerId, true);
                hasSetupWeapon.put(playerId, true);
                queSheathCounter.put(playerId, 20);
            }
        }
        if (player.getMainHandItem().getItem() instanceof Shotogatana) {
            player.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY).ifPresent(cap -> {
                if (cap instanceof PlayerPatch<?> patch) {
                    boolean sheathed = sheathWeapon.getOrDefault(playerId, false);
                    if (sheathed)
                        patch.getAnimator().addLivingAnimation(LivingMotions.BLOCK, ShotogatanaAnimations.SHOTOGATANA_GUARD);
                    else
                        patch.getAnimator().addLivingAnimation(LivingMotions.BLOCK, ShotogatanaAnimations.SHOTOGATANA_UNSHEATHED_GUARD);
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
