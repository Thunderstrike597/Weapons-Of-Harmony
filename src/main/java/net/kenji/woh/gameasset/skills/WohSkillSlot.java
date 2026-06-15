package net.kenji.woh.gameasset.skills;

import net.kenji.woh.gameasset.WohSkillCategories;
import yesman.epicfight.skill.SkillCategory;
import yesman.epicfight.skill.SkillSlot;

public enum WohSkillSlot implements SkillSlot {
    WEAPON_SECONDARY_SKILL(WohSkillCategories.WEAPON_SECONDARY);

    final SkillCategory category;

    final int id;

    WohSkillSlot(SkillCategory category){
        this.category = category;
        this.id = SkillSlot.ENUM_MANAGER.assign(this);
    }

    @Override
    public SkillCategory category() {
        return category;
    }

    @Override
    public int universalOrdinal() {
        return id;
    }
}
