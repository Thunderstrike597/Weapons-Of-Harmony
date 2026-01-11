package net.kenji.woh.registry.animation;

import net.kenji.woh.WeaponsOfHarmony;
import net.kenji.woh.api.WOHAnimationUtils;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingPhase;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import org.jline.utils.Log;
import yesman.epicfight.api.animation.AnimationManager;


@Mod.EventBusSubscriber(modid = WeaponsOfHarmony.MODID)
public class WohAnimations {

    @SubscribeEvent
    public static void registerAnimations(AnimationManager.AnimationRegistryEvent event) {
        event.newBuilder(WeaponsOfHarmony.MODID, WohAnimations::build);    }

    private static void build(AnimationManager.AnimationBuilder builder){
        GenericAnimations.build(builder);
        ShotogatanaAnimations.build(builder);
        TessenAnimations.build(builder);
        TsumeAnimations.build(builder);
        ArbitersBladeAnimations.build((builder));
        WakizashiAnimations.build(builder);
        OdachiAnimations.build(builder);


    }


}
