package net.kenji.woh.gameasset;

import net.kenji.woh.WeaponsOfHarmony;
import net.kenji.woh.gameasset.skills.*;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import yesman.epicfight.api.forgeevent.SkillBuildEvent;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.skill.SkillCategories;
import yesman.epicfight.skill.weaponinnate.WeaponInnateSkill;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(modid = WeaponsOfHarmony.MODID)
public class WohSkills {
    public static List<Skill> skills = new ArrayList<>();

    public static Skill SHOTOGATANA_SKILL;
    public static Skill SHEATH_STANCE;
    public static Skill FAN_STANCE;
    public static Skill ENRAGED_CLAWS;
    public static Skill KATAJUTSU;
    public static Skill ARBITERS_SLASH;
    public static Skill SPLIT_TENRAI;

    @SubscribeEvent
    public static void buildSkillEvent(SkillBuildEvent build){
        SkillBuildEvent.ModRegistryWorker modRegistry = build.createRegistryWorker(WeaponsOfHarmony.MODID);

       skills.add(SHOTOGATANA_SKILL = (WeaponInnateSkill)modRegistry.build("shotogatana_skill",
                ShotogatanaSkillInnate::new,
                WeaponInnateSkill.createWeaponInnateBuilder()
                        .setActivateType(Skill.ActivateType.ONE_SHOT)
                        .setCategory(SkillCategories.WEAPON_INNATE)
                        .setResource(Skill.Resource.COOLDOWN)
       ));
        skills.add(SHEATH_STANCE = modRegistry.build("shotogatana_two_hand_stance", NewShotogatanaSkillInnate::new,
                TsumeSkillInnate.createBuilder()
                        .setActivateType(Skill.ActivateType.DURATION)
                        .setCategory(SkillCategories.WEAPON_INNATE)
                        .setResource(Skill.Resource.COOLDOWN)
        ));
        skills.add(FAN_STANCE = modRegistry.build("throw_stance", TessenAimSkill::new,
                TessenAimSkill.createWeaponInnateBuilder()
                        .setActivateType(Skill.ActivateType.HELD)
                        .setCategory(SkillCategories.WEAPON_INNATE)  // Changed to WEAPON_INNATE
                        .setResource(Skill.Resource.NONE)
        ));

        skills.add(ENRAGED_CLAWS = modRegistry.build("enraged_claws", TsumeSkillInnate::new,
                WeaponInnateSkill.createWeaponInnateBuilder()
                        .setActivateType(Skill.ActivateType.DURATION)
                        .setCategory(SkillCategories.WEAPON_INNATE)
                        .setResource(Skill.Resource.COOLDOWN)
        ));
        skills.add(KATAJUTSU = modRegistry.build("katajutsu", KatajutsaPassive::new,
                KatajutsaPassive.createBuilder()
                        .setCategory(SkillCategories.PASSIVE)
                        .setResource(Skill.Resource.NONE)
        ));
        skills.add(ARBITERS_SLASH = modRegistry.build("arbiters_slash", ArbitersSlashSkill::new,
                ArbitersSlashSkill.createBuilder()
                        .setActivateType(Skill.ActivateType.HELD)
                        .setCategory(SkillCategories.WEAPON_INNATE)
                        .setResource(Skill.Resource.COOLDOWN)
        ));
        skills.add(SPLIT_TENRAI = modRegistry.build("split_tenrai", TenraiSkillInnate::new,
                WeaponInnateSkill.createWeaponInnateBuilder()
                        .setActivateType(Skill.ActivateType.DURATION)
                        .setCategory(SkillCategories.WEAPON_INNATE)
                        .setResource(Skill.Resource.COOLDOWN)
        ));
    }


}
