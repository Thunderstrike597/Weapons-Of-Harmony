package net.kenji.woh.events.loading;

import net.corruptdog.cdm.gameasset.CorruptAnimations;
import net.kenji.woh.WeaponsOfHarmony;
import net.kenji.woh.gameasset.skills.ArbitersSlashSkill;
import net.kenji.woh.network.WohPacketHandler;
import net.kenji.woh.registry.animation.ArbitersBladeAnimations;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import org.jline.utils.Log;
import reascer.wom.gameasset.animations.weapons.AnimsRuine;
import yesman.epicfight.gameasset.Animations;

@Mod.EventBusSubscriber(modid = WeaponsOfHarmony.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class SetupEvents {


    @SubscribeEvent
    public static void onClientLoadComplete(FMLLoadCompleteEvent event) {
        event.enqueueWork(ArbitersSlashSkill::setupBeamSlash);
    }

}
