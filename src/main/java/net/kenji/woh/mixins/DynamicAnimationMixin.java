package net.kenji.woh.mixins;

import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.animation.types.DynamicAnimation;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

@Mixin(value = DynamicAnimation.class, remap = false)
public class DynamicAnimationMixin {

    @Mutable
    @Shadow
    @Final
    protected float transitionTime;

    @Unique
    private float weaponsOfHarmony_Versions$originalConvetTime;
    @Unique
    private float weaponsOfHarmony_Versions$originalModifiedConvetTime;


    @Inject(method = "<init>*", at = @At("TAIL"))
    private void woh$reAdjustConvertTime(float transitionTime, boolean isRepeat, CallbackInfo ci) {
       DynamicAnimation animation = (DynamicAnimation) (Object)this;
        if(transitionTime < 0.08)
            this.transitionTime = 0.16F;
        weaponsOfHarmony_Versions$originalConvetTime = transitionTime;
        weaponsOfHarmony_Versions$originalModifiedConvetTime = this.transitionTime;
    }
    @Inject(method = "begin", at = @At("HEAD"))
    public void onAttackBegin(LivingEntityPatch<?> entitypatch, CallbackInfo ci) {
        DynamicAnimation animation = (DynamicAnimation) (Object) this;
        if(animation instanceof AttackAnimation){
            this.woh$setConvertTime(this.weaponsOfHarmony_Versions$originalModifiedConvetTime);
        }
    }
    @Inject(method = "linkTick", at = @At("HEAD"))
    private void woh$reAdjustConvertTime(LivingEntityPatch<?> entitypatch, AssetAccessor<? extends DynamicAnimation> linkAnimation, CallbackInfo ci) {
        DynamicAnimation animation = (DynamicAnimation) (Object)this;
        DynamicAnimation nextLivingMotion = entitypatch.getAnimator().getLivingAnimation(entitypatch.currentLivingMotion, Animations.BIPED_IDLE).get();
        if(animation instanceof StaticAnimation staticAnimation) {
            if (linkAnimation instanceof AttackAnimation) {
                ((DynamicAnimationMixin) (Object) staticAnimation).woh$setConvertTime(weaponsOfHarmony_Versions$originalConvetTime);
            }
        }
    }
    // Add a helper method to set convertTime
    @Unique
    public void woh$setConvertTime(float newConvertTime) {
        this.transitionTime = newConvertTime;
    }
}
