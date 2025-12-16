package net.kenji.woh.registry.animation;

import net.kenji.woh.WeaponsOfHarmony;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import yesman.epicfight.api.forgeevent.AnimationRegistryEvent;


@Mod.EventBusSubscriber(modid = WeaponsOfHarmony.MODID)
public class WohAnimations {

    @SubscribeEvent
    public static void registerAnimations(AnimationRegistryEvent event) {
        event.getRegistryMap().put(WeaponsOfHarmony.MODID, WohAnimations::build);
    }

    private static void build(){
        MastersKatanaAnimations.build();
        MastersWakizashiAnimations.build();
    }
}
