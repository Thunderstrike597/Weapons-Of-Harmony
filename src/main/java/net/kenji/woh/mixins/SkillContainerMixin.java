package net.kenji.woh.mixins;

import net.kenji.woh.api.basegameassets.HybridHoldableSkill;
import net.kenji.woh.gameasset.skills.ArbitersSlashSkill;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.player.LocalPlayer;
import org.jetbrains.annotations.NotNull;
import org.jline.utils.Log;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import yesman.epicfight.api.client.input.InputManager;
import yesman.epicfight.api.client.input.action.EpicFightInputAction;
import yesman.epicfight.api.client.input.action.InputAction;
import yesman.epicfight.client.events.engine.ControlEngine;
import yesman.epicfight.client.input.EpicFightKeyMappings;
import yesman.epicfight.client.world.capabilites.entitypatch.player.LocalPlayerPatch;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.skill.SkillSlot;
import yesman.epicfight.skill.SkillSlots;
import yesman.epicfight.skill.modules.HoldableSkill;

@Mixin(value = SkillContainer.class, remap = false)
public abstract class SkillContainerMixin {

    @Shadow
    public abstract Skill getSkill();

    @Shadow
    protected boolean isActivated;

    @Shadow
    public abstract int getRemainDuration();

    @Inject(method = "deactivate", at = @At("HEAD"), cancellable = true, remap = false)
    private void woh$deactivate(CallbackInfo ci) {
        if(this.getSkill() instanceof HybridHoldableSkill holdableSkill){
            if(!this.isActivated) {
                if (holdableSkill.getKeyMapping().isDown()) {
                    ci.cancel();
                }
            }

        }

    }
}