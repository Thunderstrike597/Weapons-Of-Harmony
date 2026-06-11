package net.kenji.woh.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;
import yesman.epicfight.api.client.input.action.InputAction;
import yesman.epicfight.client.events.engine.ControlEngine;
import yesman.epicfight.skill.SkillSlot;

@Mixin(value = ControlEngine.class, remap = false)
public interface ControlEngineAccessor {
    @Invoker("reserveKey")
    void invokeReserveKey(SkillSlot slot, InputAction action);
    @Accessor("weaponInnatePressCounter")
    int getPressCounter();

}