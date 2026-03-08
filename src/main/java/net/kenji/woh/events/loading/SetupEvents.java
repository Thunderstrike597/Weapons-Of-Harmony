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

@Mod.EventBusSubscriber(modid = WeaponsOfHarmony.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class SetupEvents {


    @SubscribeEvent
    public static void onClientLoadComplete(FMLLoadCompleteEvent event) {
        event.enqueueWork(() -> {
            // Populate the map after everything is loaded
            ArbitersSlashSkill.slashAngleMap.put(ArbitersBladeAnimations.ARBITERS_BLADE_SKILL_AIM_AUTO_1.get().toString(), -45);
            ArbitersSlashSkill.slashAngleMap.put(CorruptAnimations.SWORD_ONEHAND_AUTO1.get().toString(), -45);

            ArbitersSlashSkill.slashAngleMap.put(CorruptAnimations.SWORD_ONEHAND_AUTO2.get().toString(), 0);
            ArbitersSlashSkill.slashAngleMap.put(CorruptAnimations.SWORD_ONEHAND_AUTO3.get().toString(), 80);
            ArbitersSlashSkill.slashAngleMap.put(CorruptAnimations.SWORD_ONEHAND_AUTO4.get().toString(), 45);
        });
    }

}
