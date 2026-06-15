package net.kenji.woh.mixins;

import net.kenji.woh.api.basegameassets.HybridHoldableSkill;
import net.kenji.woh.api.interfaces.IHybridSkill;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.skill.SkillContainer;

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
        if(this.getSkill() instanceof IHybridSkill holdableSkill){
            if(!this.isActivated) {
                if (holdableSkill.getKeyMapping().isDown()) {
                    ci.cancel();
                }
            }

        }

    }
}