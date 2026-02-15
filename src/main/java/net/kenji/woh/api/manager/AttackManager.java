package net.kenji.woh.api.manager;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AttackManager {
    public static final Map<UUID, Boolean> isInAttack = new HashMap<>();
    public static final Map<UUID, Boolean> isInAttackForCombo = new HashMap<>();

    @SubscribeEvent
    public static void onPLayerTick(TickEvent.PlayerTickEvent event) {
        Player player = event.player;
        boolean attacking = isInAttack.getOrDefault(player.getUUID(), false);
        if(player.level().isClientSide){
            Minecraft mc = Minecraft.getInstance();
            if(attacking){
                mc.options.keyUp.setDown(false);
                mc.options.keyDown.setDown(false);
                mc.options.keyLeft.setDown(false);
                mc.options.keyRight.setDown(false);
            }
        }
    }
    public static void resyncMovementKeys(Minecraft mc) {
        long window = mc.getWindow().getWindow();

        resync(mc.options.keyUp, window);
        resync(mc.options.keyDown, window);
        resync(mc.options.keyLeft, window);
        resync(mc.options.keyRight, window);
    }
    private static void resync(KeyMapping key, long window) {
        InputConstants.Key input = key.getKey();
        boolean physicallyDown = InputConstants.isKeyDown(window, input.getValue());

        key.setDown(physicallyDown);
    }
}
