package net.kenji.woh.registry;

import net.kenji.woh.WeaponsOfHarmony;
import net.kenji.woh.gameasset.ShotogatanaSkillInnate;
import net.kenji.woh.gameasset.TessenSkillInnate;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import yesman.epicfight.api.forgeevent.SkillBuildEvent;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.skill.SkillCategories;
import yesman.epicfight.skill.weaponinnate.WeaponInnateSkill;

@Mod.EventBusSubscriber(modid = WeaponsOfHarmony.MODID)
public class WohSkills {

    public static Skill SHEATH_STANCE;
    public static Skill FAN_STANCE;

    @SubscribeEvent
    public static void buildSkillEvent(SkillBuildEvent build){
        SkillBuildEvent.ModRegistryWorker modRegistry = build.createRegistryWorker(WeaponsOfHarmony.MODID);

        SHEATH_STANCE = (WeaponInnateSkill)modRegistry.build("sheath_stance",
                ShotogatanaSkillInnate::new,
                WeaponInnateSkill.createWeaponInnateBuilder()
                        .setActivateType(Skill.ActivateType.ONE_SHOT)
                        .setCategory(SkillCategories.WEAPON_INNATE)
                        .setResource(Skill.Resource.COOLDOWN));

        FAN_STANCE = modRegistry.build("fan_stance", TessenSkillInnate::new,
                ShotogatanaSkillInnate.createBuilder()
                        .setActivateType(Skill.ActivateType.DURATION)
                        .setCategory(SkillCategories.WEAPON_INNATE)
                        .setResource(Skill.Resource.COOLDOWN)
        );
    }


}
