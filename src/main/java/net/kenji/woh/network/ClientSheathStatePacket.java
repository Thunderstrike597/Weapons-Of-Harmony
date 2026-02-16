package net.kenji.woh.network;

import net.kenji.woh.api.manager.ShotogatanaManager;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ClientSheathStatePacket {
    private final int sheathCounter;


    public ClientSheathStatePacket(int sheathCounter) {
        this.sheathCounter = sheathCounter;
    }

    // Encode: Write data to buffer
    public static void encode(ClientSheathStatePacket packet, FriendlyByteBuf buf) {
        buf.writeInt(packet.sheathCounter);
    }

    // Decode: Read data from buffer
    public static ClientSheathStatePacket decode(FriendlyByteBuf buf) {
        int sheathCounter = buf.readInt();
        return new ClientSheathStatePacket(sheathCounter);
    }

    // Handle: Process the packet on the receiving side
    public static void handle(ClientSheathStatePacket packet, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            if(ctx.get().getDirection().getReceptionSide().isClient()) {
                Minecraft mc = Minecraft.getInstance();
                Player player = mc.player;

                ShotogatanaManager.queSheathCounter.put(player.getUUID(), packet.sheathCounter);
            }
        });
        ctx.get().setPacketHandled(true);
    }
}