package net.kenji.woh.network;

import net.kenji.woh.api.basegameassets.HybridHoldableSkill;
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

public record ClientArbitersSlashPacket(boolean deactivate, String tagToSend) {

    // Encode: Write data to buffer
    public static void encode(ClientArbitersSlashPacket packet, FriendlyByteBuf buf) {
        buf.writeBoolean(packet.deactivate);
        buf.writeUtf(packet.tagToSend);
    }

    // Decode: Read data from buffer
    public static ClientArbitersSlashPacket decode(FriendlyByteBuf buf) {
        boolean deactivate = buf.readBoolean();
        String tag = buf.readUtf();
        return new ClientArbitersSlashPacket(deactivate, tag);
    }

    // Handle: Process the packet on the receiving side
    public static void handle(ClientArbitersSlashPacket packet, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            if(ctx.get().getDirection().getReceptionSide().isClient()) {
                executeOnClient(packet);
            }
        });
        ctx.get().setPacketHandled(true);
    }

    @OnlyIn(Dist.CLIENT)
    private static void executeOnClient(ClientArbitersSlashPacket packet){
        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;
        PlayerPatch<?> playerPatch = EpicFightCapabilities.getEntityPatch(player, PlayerPatch.class);
        if(playerPatch == null) return;

        SkillContainer container = playerPatch.getSkill(WohSkills.ARBITERS_SLASH);
        if(container == null)
            return;
        player.getMainHandItem().getOrCreateTag().putString("weapon_variant", packet.tagToSend);
        if(packet.deactivate) {
            container.deactivate();
            if (container.getSkill() instanceof HybridHoldableSkill skill) {
                skill.didActivate.put(player.getUUID(), false);
            }
        }
    }
}