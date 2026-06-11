package net.kenji.woh.api.basegameassets.condition;

import net.kenji.woh.WeaponsOfHarmony;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import yesman.epicfight.data.conditions.Condition;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class InAirCondition implements Condition<PlayerPatch<?>> {
    public Condition<PlayerPatch<?>> read(CompoundTag compoundTag) {
        return this;
    }

    public CompoundTag serializePredicate() {
        return new CompoundTag();
    }

    private static Map<UUID, Boolean> wasInAir = new HashMap<>();
    @Mod.EventBusSubscriber(
            modid = WeaponsOfHarmony.MODID,
            bus = Mod.EventBusSubscriber.Bus.FORGE,
            value = Dist.CLIENT)
    public class Events {
        @SubscribeEvent
        public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
            if (event.phase != TickEvent.Phase.END) return;
            boolean inAir = wasInAir.getOrDefault(event.player.getUUID(), false);
            if(inAir) {
                if(event.player.onGround()|| event.player.isInWater())
                    wasInAir.put(event.player.getUUID(), false);
            }
        }
    }

    public boolean predicate(PlayerPatch<?> playerPatch) {
        Player player = (Player) playerPatch.getOriginal();
        if (!player.onGround() && !player.isInWater()) {
            if (!wasInAir.getOrDefault(player.getUUID(), false)) {
                wasInAir.put(player.getUUID(), true);
                return true;
            }
        }
        return false;
    }

    public List<ParameterEditor> getAcceptingParameters(Screen screen) {
        return null;
    }
}
