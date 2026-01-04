package net.kenji.woh.registry.animation;

import net.kenji.woh.WeaponsOfHarmony;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import yesman.epicfight.api.animation.AnimationManager;


@Mod.EventBusSubscriber(modid = WeaponsOfHarmony.MODID)
public class WohAnimations {

    @SubscribeEvent
    public static void registerAnimations(AnimationManager.AnimationRegistryEvent event) {
        event.newBuilder(WeaponsOfHarmony.MODID, WohAnimations::build);    }

    private static void build(AnimationManager.AnimationBuilder builder){
        GenericAnimations.build();
        ShotogatanaAnimations.build();
        TessenAnimations.build();
        TsumeAnimations.build();
        WakizashiAnimations.build();
        OdachiAnimations.build();

    }
}
