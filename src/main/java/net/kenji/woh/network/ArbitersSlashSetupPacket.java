package net.kenji.woh.network;

import net.kenji.woh.gameasset.skills.ArbitersSlashSkill;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.Map;
import java.util.function.Supplier;

public class ArbitersSlashSetupPacket {
    private final Map<String, Integer> map;
    public ArbitersSlashSetupPacket(Map<String, Integer> map) {
        this.map = map;
    }

    public static void encode(ArbitersSlashSetupPacket packet, FriendlyByteBuf buf) {
        buf.writeMap(
                packet.map,
                FriendlyByteBuf::writeUtf,
                FriendlyByteBuf::writeInt
        );
    }

    public static ArbitersSlashSetupPacket decode(FriendlyByteBuf buf) {
        Map<String, Integer> stringMap = buf.readMap(
                FriendlyByteBuf::readUtf,
                FriendlyByteBuf::readInt
        );

        return new ArbitersSlashSetupPacket(stringMap);
    }

    public static void handle(ArbitersSlashSetupPacket packet, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ArbitersSlashSkill.slashAngleMap = packet.map;
        });
        ctx.get().setPacketHandled(true);
    }
}