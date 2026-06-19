package net.kenji.woh.gameasset;

import net.kenji.woh.WeaponsOfHarmony;
import net.kenji.woh.gameasset.skills.*;
import net.kenji.woh.gameasset.skills.TenraiSkillInnate;
import net.kenji.woh.gameasset.skills.combos.ArbitersBladeCombos;
import net.kenji.woh.gameasset.skills.combos.ShotogatanaCombos;
import net.kenji.woh.gameasset.skills.combos.TenraiCombos;
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
    public static Skill FAN_STANCE;
    public static Skill ENRAGED_CLAWS;
    public static Skill KATAJUTSU;
    public static Skill ARBITERS_SLASH;
    public static Skill SPLIT_TENRAI;

    public static Skill SHOTOGATANA_COMBO;
    public static Skill TENRAI_COMBO;
    public static Skill ARBITERS_SLASH_COMBO;

    @SubscribeEvent
    public static void buildSkillEvent(SkillBuildEvent build){
        SkillBuildEvent.ModRegistryWorker modRegistry = build.createRegistryWorker(WeaponsOfHarmony.MODID);

        skills.add(SHOTOGATANA_SKILL = modRegistry.build("shotogatana_two_hand_stance", ShotogatanaSkillInnate::new,
                ShotogatanaSkillInnate.createBuilder()
                        .setActivateType(Skill.ActivateType.DURATION)
                        .setCategory(WohSkillCategories.WEAPON_SECONDARY)
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
        skills.add(ARBITERS_SLASH = modRegistry.build("arbiters_slash", (builder) -> new ArbitersSlashSkill(builder, 1.0F),
                ArbitersSlashSkill.createBuilder()
                        .setActivateType(Skill.ActivateType.HELD)
                        .setCategory(WohSkillCategories.WEAPON_SECONDARY)
                        .setResource(Skill.Resource.COOLDOWN)
        ));
        skills.add(SPLIT_TENRAI = modRegistry.build("split_tenrai", (builder) -> new TenraiSkillInnate(builder, 8.0F),
                TenraiSkillInnate.createBuilder()
                        .setActivateType(Skill.ActivateType.DURATION)
                        .setCategory(WohSkillCategories.WEAPON_SECONDARY)
                        .setResource(Skill.Resource.COOLDOWN)
        ));

        skills.add(SHOTOGATANA_COMBO = ShotogatanaCombos.buildSkills(modRegistry));
        skills.add(TENRAI_COMBO = TenraiCombos.buildTenraiSkills(modRegistry));
        skills.add(ARBITERS_SLASH_COMBO = ArbitersBladeCombos.buildSkills(modRegistry));



    }


}
