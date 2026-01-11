package net.kenji.woh.gameasset;

import net.kenji.woh.WeaponsOfHarmony;
import net.kenji.woh.gameasset.skills.*;
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
    public static Skill ENRAGED_CLAWS;
    public static Skill KATAJUTSU;
    public static Skill ARBITERS_SLASH;

    @SubscribeEvent
    public static void buildSkillEvent(SkillBuildEvent build){
        SkillBuildEvent.ModRegistryWorker modRegistry = build.createRegistryWorker(WeaponsOfHarmony.MODID);

        SHEATH_STANCE = (WeaponInnateSkill)modRegistry.build("shotogatana_skill",
                ShotogatanaSkillInnate::new,
                WeaponInnateSkill.createWeaponInnateBuilder()
                        .setActivateType(Skill.ActivateType.ONE_SHOT)
                        .setCategory(SkillCategories.WEAPON_INNATE)
                        .setResource(Skill.Resource.COOLDOWN));

        FAN_STANCE = modRegistry.build("throw_stance", TessenAimSkill::new,
                TessenAimSkill.createGuardBuilder()
                        .setCategory(SkillCategories.WEAPON_PASSIVE)
                        .setResource(Skill.Resource.NONE));

        ENRAGED_CLAWS = modRegistry.build("enraged_claws", TsumeSkillInnate::new,
                TsumeSkillInnate.createBuilder()
                        .setActivateType(Skill.ActivateType.DURATION)
                        .setCategory(SkillCategories.WEAPON_INNATE)
                        .setResource(Skill.Resource.COOLDOWN)
        );
        KATAJUTSU = modRegistry.build("katajutsu", KatajutsaPassive::new,
                KatajutsaPassive.createBuilder().setCategory(SkillCategories.PASSIVE)
        );
        ARBITERS_SLASH = modRegistry.build("arbiters_slash", ArbitersSlashSkill::new,
                ArbitersSlashSkill.createGuardBuilder()
                        .setActivateType(Skill.ActivateType.CHARGING)
                        .setCategory(SkillCategories.WEAPON_INNATE)
                        .setResource(Skill.Resource.COOLDOWN)
        );
    }


}
