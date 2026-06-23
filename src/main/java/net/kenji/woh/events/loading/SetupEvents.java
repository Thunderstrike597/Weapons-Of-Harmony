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
        event.enqueueWork(() -> {
            // Populate the map after everything is loaded
            ArbitersSlashSkill.slashAngleMap.put(ArbitersBladeAnimations.ARBITERS_BLADE_SKILL_AIM_ATTACK.get().toString(), -45);

            ArbitersSlashSkill.slashAngleMap.put(CorruptAnimations.LONGSWORD_OLD_AUTO1.get().toString(), -45);
            ArbitersSlashSkill.slashAngleMap.put(CorruptAnimations.LONGSWORD_OLD_AUTO2.get().toString(), -45);
            ArbitersSlashSkill.slashAngleMap.put(CorruptAnimations.LONGSWORD_OLD_AUTO3.get().toString(), 20);
            ArbitersSlashSkill.slashAngleMap.put(CorruptAnimations.LONGSWORD_OLD_AUTO4.get().toString(), 45);
            ArbitersSlashSkill.slashAngleMap.put(Animations.LONGSWORD_AUTO1.get().toString(), -70);
            ArbitersSlashSkill.slashAngleMap.put(Animations.LONGSWORD_AUTO2.get().toString(), -45);
            ArbitersSlashSkill.slashAngleMap.put(Animations.LONGSWORD_AUTO3.get().toString(), 70);
            ArbitersSlashSkill.slashAngleMap.put(ArbitersBladeAnimations.ARBITERS_BLADE_SKILL_AUTO_1.get().toString(), -60);
            ArbitersSlashSkill.slashAngleMap.put(ArbitersBladeAnimations.ARBITERS_BLADE_SKILL_AUTO_2.get().toString(), -15);
            ArbitersSlashSkill.slashAngleMap.put(ArbitersBladeAnimations.ARBITERS_BLADE_SKILL_AUTO_3.get().toString(), -52);

            ArbitersSlashSkill.slashAngleMap.put(CorruptAnimations.LONGSWORD_OLD_AUTO1.get().toString(), -52);
            ArbitersSlashSkill.slashAngleMap.put(CorruptAnimations.LONGSWORD_OLD_AUTO2.get().toString(), -70);
            ArbitersSlashSkill.slashAngleMap.put(AnimsRuine.RUINE_AUTO_1.get().toString(), 52);
            ArbitersSlashSkill.slashAngleMap.put(CorruptAnimations.TACHI_TWOHAND_AUTO_4.get().toString(), 52);




        });
    }

}
