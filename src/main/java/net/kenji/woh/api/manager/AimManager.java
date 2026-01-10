package net.kenji.woh.api.manager;

import net.kenji.woh.WeaponsOfHarmony;
import net.kenji.woh.network.AimHoldPacket;
import net.kenji.woh.network.WohPacketHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import yesman.epicfight.client.input.EpicFightKeyMappings;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AimManager {
    public static Map<UUID, Boolean> wasDown = new HashMap<>();
    public static Map<UUID, Float> counter = new HashMap<>();

    public static boolean isAiming(PlayerPatch<?> patch) {
        UUID id = patch.getOriginal().getUUID();
        return AimManager.wasDown.getOrDefault(id, false);
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
                    WohPacketHandler.sendToServer(new AimHoldPacket(id, true));
                }
                counter.put(id, 0f);
            } else if (wasDown.getOrDefault(id, false)) {
                counter.put(id, counter.getOrDefault(id, 0f) + 1f);

                if (counter.get(id) >= 2.5) {
                    wasDown.put(id, false);
                    counter.put(id, 0f);
                    WohPacketHandler.sendToServer(new AimHoldPacket(id, false));
                }
            }
        }
    }
}
