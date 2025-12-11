package net.kenji.woh.registry;

import net.kenji.woh.WeaponsOfHarmony;
import net.kenji.woh.gameasset.EnhancedKatanaSkillInnate;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import yesman.epicfight.api.forgeevent.SkillBuildEvent;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.skill.SkillCategories;

@Mod.EventBusSubscriber(modid = WeaponsOfHarmony.MODID)
public class WOHSkills {

    public static Skill SHEATH_STANCE;

    @SubscribeEvent
    public static void buildSkillEvent(SkillBuildEvent build){
        SkillBuildEvent.ModRegistryWorker modRegistry = build.createRegistryWorker(WeaponsOfHarmony.MODID);

        SHEATH_STANCE = modRegistry.build("sheath_stance", EnhancedKatanaSkillInnate::new,
                EnhancedKatanaSkillInnate.createBuilder()
                        .setActivateType(Skill.ActivateType.DURATION_INFINITE)
                        .setCategory(SkillCategories.WEAPON_INNATE)
                        .setResource(Skill.Resource.NONE)
        );
    }

}
