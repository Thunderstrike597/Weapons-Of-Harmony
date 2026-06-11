package net.kenji.woh.gameasset.skills;

import yesman.epicfight.skill.SkillCategories;
import yesman.epicfight.skill.SkillCategory;
import yesman.epicfight.skill.SkillSlot;

public enum WohWeaponSkillSlot implements SkillSlot {
    SECONDARY_SKILL(SkillCategories.WEAPON_INNATE);

    final SkillCategory category;

    WohWeaponSkillSlot(SkillCategory category){
        this.category = category;
    }

    @Override
    public SkillCategory category() {
        return category;
    }

    @Override
    public int universalOrdinal() {
        return 0;
    }
}
