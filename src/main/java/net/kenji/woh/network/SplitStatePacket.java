package net.kenji.woh.network;

import net.kenji.woh.api.manager.ShotogatanaManager;
import net.kenji.woh.api.manager.TenraiManager;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.UUID;
import java.util.function.Supplier;

public class SplitStatePacket {
    private final UUID playerId;
    private final boolean isSplit;

    public SplitStatePacket(UUID playerId, boolean isSplit) {
        this.playerId = playerId;
        this.isSplit = isSplit;
    }

    // Encode: Write data to buffer
    public static void encode(SplitStatePacket packet, FriendlyByteBuf buf) {
        buf.writeUUID(packet.playerId);
        buf.writeBoolean(packet.isSplit);
    }

    // Decode: Read data from buffer
    public static SplitStatePacket decode(FriendlyByteBuf buf) {
        UUID playerId = buf.readUUID();
        boolean isSplit = buf.readBoolean();
        return new SplitStatePacket(playerId, isSplit);
    }

    // Handle: Process the packet on the receiving side
    public static void handle(SplitStatePacket packet, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            // This runs on the server thread
            TenraiManager.renderSplitMap.put(packet.playerId, packet.isSplit);
        });
        ctx.get().setPacketHandled(true);
    }
}