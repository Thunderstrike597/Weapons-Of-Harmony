package net.kenji.woh.network;

import net.kenji.woh.api.manager.ShotogatanaManager;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.UUID;
import java.util.function.Supplier;

public class SheathStatePacket {
    private final UUID playerId;
    private final boolean isSheathed;

    public SheathStatePacket(UUID playerId, boolean isSheathed) {
        this.playerId = playerId;
        this.isSheathed = isSheathed;
    }

    // Encode: Write data to buffer
    public static void encode(SheathStatePacket packet, FriendlyByteBuf buf) {
        buf.writeUUID(packet.playerId);
        buf.writeBoolean(packet.isSheathed);
    }

    // Decode: Read data from buffer
    public static SheathStatePacket decode(FriendlyByteBuf buf) {
        UUID playerId = buf.readUUID();
        boolean isSheathed = buf.readBoolean();
        return new SheathStatePacket(playerId, isSheathed);
    }

    // Handle: Process the packet on the receiving side
    public static void handle(SheathStatePacket packet, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            // This runs on the server thread
            ShotogatanaManager.sheathWeapon.put(packet.playerId, packet.isSheathed);
        });
        ctx.get().setPacketHandled(true);
    }
}