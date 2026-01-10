package net.kenji.woh.network;

import net.kenji.woh.api.manager.AimManager;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;
import org.jline.utils.Log;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;

import java.util.UUID;
import java.util.function.Supplier;

public class AimHoldPacket {
    private final UUID playerId;
    private final boolean isAiming;

    // Constructor only needs UUID and boolean - playerPatch is looked up on server
    public AimHoldPacket(UUID playerId, boolean isAiming) {
        this.playerId = playerId;
        this.isAiming = isAiming;
    }

    public static void encode(AimHoldPacket packet, FriendlyByteBuf buf) {
        buf.writeUUID(packet.playerId);
        buf.writeBoolean(packet.isAiming);
    }

    public static AimHoldPacket decode(FriendlyByteBuf buf) {
        UUID playerId = buf.readUUID();
        boolean isAiming = buf.readBoolean();
        return new AimHoldPacket(playerId, isAiming); // Fixed - matches constructor
    }

    public static void handle(AimHoldPacket packet, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayer player = ctx.get().getSender();
            if (player == null) {
                Log.warn("TessenInnatePacket: Sender is null!");
                return;
            }


            // Update the server-side wasDown map
            AimManager.wasDown.put(packet.playerId, packet.isAiming);

            // Reset counter if releasing
            if (!packet.isAiming) {
                AimManager.counter.put(packet.playerId, 0f);
            }

            // Force style update by refreshing the held item capability
            player.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY).ifPresent(cap -> {
                if (cap instanceof ServerPlayerPatch serverPatch) {
                    // This forces Epic Fight to re-evaluate the style
                    serverPatch.updateMotion(true);
                    serverPatch.updateEntityState();
                }
            });
        });
        ctx.get().setPacketHandled(true);
    }
}