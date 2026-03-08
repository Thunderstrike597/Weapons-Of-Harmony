package net.kenji.woh.network;

import net.kenji.woh.api.manager.ShotogatanaManager;
import net.kenji.woh.gameasset.WohSkills;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkEvent;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;

import java.util.function.Supplier;

public class ClientArbitersSlashPacket {
    public ClientArbitersSlashPacket() {
    }

    // Encode: Write data to buffer
    public static void encode(ClientArbitersSlashPacket packet, FriendlyByteBuf buf) {
    }

    // Decode: Read data from buffer
    public static ClientArbitersSlashPacket decode(FriendlyByteBuf buf) {

        return new ClientArbitersSlashPacket();
    }

    // Handle: Process the packet on the receiving side
    public static void handle(ClientArbitersSlashPacket packet, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            if(ctx.get().getDirection().getReceptionSide().isClient()) {
                Minecraft mc = Minecraft.getInstance();
                Player player = mc.player;
                PlayerPatch<?> playerPatch = EpicFightCapabilities.getEntityPatch(player, PlayerPatch.class);
                if(playerPatch == null) return;

                SkillContainer container = playerPatch.getSkill(WohSkills.ARBITERS_SLASH);

                container.deactivate();
            }
        });
        ctx.get().setPacketHandled(true);
    }
}