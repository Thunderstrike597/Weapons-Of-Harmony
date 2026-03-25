package net.kenji.woh.network;

import net.kenji.woh.gameasset.WohSkills;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkEvent;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;

import java.util.function.Supplier;

public class ClientTenraiSkillActivatePacket {
    private final boolean isActivated;
    public ClientTenraiSkillActivatePacket(boolean isActivated) {
        this.isActivated = isActivated;
    }

    // Encode: Write data to buffer
    public static void encode(ClientTenraiSkillActivatePacket packet, FriendlyByteBuf buf) {
        buf.writeBoolean(packet.isActivated);
    }

    // Decode: Read data from buffer
    public static ClientTenraiSkillActivatePacket decode(FriendlyByteBuf buf) {
        boolean isActivated = buf.readBoolean();
        return new ClientTenraiSkillActivatePacket(isActivated);
    }

    // Handle: Process the packet on the receiving side
    public static void handle(ClientTenraiSkillActivatePacket packet, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            if(ctx.get().getDirection().getReceptionSide().isClient()) {
                executeOnClient(packet);
            }
        });
        ctx.get().setPacketHandled(true);
    }

    @OnlyIn(Dist.CLIENT)
    private static void executeOnClient(ClientTenraiSkillActivatePacket packet){
        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;
        PlayerPatch<?> playerPatch = EpicFightCapabilities.getEntityPatch(player, PlayerPatch.class);
        if(playerPatch == null) return;

        SkillContainer container = playerPatch.getSkill(WohSkills.SPLIT_TENRAI);
        if(packet.isActivated)
            container.activate();
        else container.deactivate();
    }
}