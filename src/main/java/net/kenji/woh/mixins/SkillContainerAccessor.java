package net.kenji.woh.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import yesman.epicfight.skill.SkillContainer;

@Mixin(value = SkillContainer.class, remap = false)
public interface SkillContainerAccessor {

    @Accessor("duration")
    void accessSetDuration(int value);
}