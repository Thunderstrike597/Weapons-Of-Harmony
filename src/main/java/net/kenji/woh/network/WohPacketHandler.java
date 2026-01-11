package net.kenji.woh.network;

import net.kenji.woh.WeaponsOfHarmony;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

public class WohPacketHandler {
    private static final String PROTOCOL_VERSION = "1";

    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(WeaponsOfHarmony.MODID, "main"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );

    private static int packetId = 0;

    private static int id() {
        return packetId++;
    }

    public static void register() {
        INSTANCE.messageBuilder(SheathStatePacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(SheathStatePacket::decode)
                .encoder(SheathStatePacket::encode)
                .consumerMainThread(SheathStatePacket::handle)
                .add();
        INSTANCE.messageBuilder(AimHoldPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(AimHoldPacket::decode)
                .encoder(AimHoldPacket::encode)
                .consumerMainThread(AimHoldPacket::handle)
                .add();
    }

    // Helper method to send packet to server
    public static void sendToServer(Object packet) {
        INSTANCE.sendToServer(packet);
    }

    // Helper method to send packet to specific player
    public static void sendToPlayer(Object packet, ServerPlayer player) {
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), packet);
    }

    // Helper method to send packet to all players
    public static void sendToAll(Object packet) {
        INSTANCE.send(PacketDistributor.ALL.noArg(), packet);
    }
}