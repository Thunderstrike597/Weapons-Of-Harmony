package net.kenji.woh.gameasset;

import net.minecraft.resources.ResourceLocation;
import yesman.epicfight.main.EpicFightMod;
import yesman.epicfight.skill.SkillCategory;

public enum WohSkillCategories implements SkillCategory {
    CONSTANT(true, true, false);

    final boolean shouldSave;
    final boolean shouldSyncronize;
    final boolean modifiable;
    final int id;
    final ResourceLocation bookIcon;

    private WohSkillCategories(boolean shouldSave, boolean shouldSyncronizedAllPlayers, boolean modifiable) {
        this.shouldSave = shouldSave;
        this.shouldSyncronize = shouldSyncronizedAllPlayers;
        this.modifiable = modifiable;
        this.id = SkillCategory.ENUM_MANAGER.assign(this);
        this.bookIcon = SkillCategory.DEFAULT_BOOK_ICON;
    }

    public boolean shouldSave() {
        return this.shouldSave;
    }

    public boolean shouldSynchronize() {
        return this.shouldSyncronize;
    }

    public boolean learnable() {
        return this.modifiable;
    }

    public int universalOrdinal() {
        return this.id;
    }

    public ResourceLocation bookIcon() {
        return this.bookIcon;
    }
}
