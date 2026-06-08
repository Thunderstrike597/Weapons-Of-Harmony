package net.kenji.woh.mixins;

import net.kenji.woh.api.interfaces.IPhase;
import net.minecraft.world.InteractionHand;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import yesman.epicfight.api.animation.Joint;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.collider.Collider;

@Mixin(value = AttackAnimation.Phase.class, remap = false)
public class PhaseMixin implements IPhase {

    @Shadow @Mutable @Final
    public float recovery;

    // Store original so we can restore it
    private float woh$originalRecovery;
    private boolean woh$recoveryOverridden = false;


    @Inject(method = "<init>(FFFFFFLyesman/epicfight/api/animation/Joint;Lyesman/epicfight/api/collider/Collider;)V", at = @At("RETURN"))
    public void onInit(float start, float antic, float preDelay, float contact, float recovery, float end, Joint joint, Collider collider, CallbackInfo ci){
        this.woh$originalRecovery = recovery;
    }
    @Inject(method = "<init>(FFFFFLnet/minecraft/world/InteractionHand;Lyesman/epicfight/api/animation/Joint;Lyesman/epicfight/api/collider/Collider;)V", at = @At("RETURN"))
    public void onInit(float start, float antic, float contact, float recovery, float end, InteractionHand hand, Joint joint, Collider collider, CallbackInfo ci){
        this.woh$originalRecovery = recovery;
    }
    @Inject(method = "<init>(FFFFFFZLnet/minecraft/world/InteractionHand;Lyesman/epicfight/api/animation/Joint;Lyesman/epicfight/api/collider/Collider;)V", at = @At("RETURN"))
    public void onInit(float start, float antic, float preDelay, float contact, float recovery, float end, boolean noStateBind, InteractionHand hand, Joint joint, Collider collider, CallbackInfo ci){
        this.woh$originalRecovery = recovery;
    }
    @Inject(method = "<init>(FFFFFFZLnet/minecraft/world/InteractionHand;[Lyesman/epicfight/api/animation/types/AttackAnimation$JointColliderPair;)V", at = @At("RETURN"))
    public void onInit(float start, float antic, float preDelay, float contact, float recovery, float end, boolean noStateBind, InteractionHand hand, AttackAnimation.JointColliderPair[] colliders, CallbackInfo ci){
        this.woh$originalRecovery = recovery;
    }
    @Inject(method = "<init>(FFFFFFLnet/minecraft/world/InteractionHand;[Lyesman/epicfight/api/animation/types/AttackAnimation$JointColliderPair;)V", at = @At("RETURN"))
    public void onInit(float start, float antic, float preDelay, float contact, float recovery, float end, InteractionHand hand, AttackAnimation.JointColliderPair[] colliders, CallbackInfo ci){
        this.woh$originalRecovery = recovery;
    }
    @Inject(method = "<init>(FFFFFFLnet/minecraft/world/InteractionHand;Lyesman/epicfight/api/animation/Joint;Lyesman/epicfight/api/collider/Collider;)V", at = @At("RETURN"))
    public void onInit(float start, float antic, float preDelay, float contact, float recovery, float end, InteractionHand hand, Joint joint, Collider collider, CallbackInfo ci){
        this.woh$originalRecovery = recovery;
    }

    public void woh$setRecoveryOverride(float newRecovery) {
        if (!woh$recoveryOverridden) {
            this.woh$originalRecovery = this.recovery; // safety: capture current
        }
        this.recovery = newRecovery;
        this.woh$recoveryOverridden = true;
    }

    public void woh$restoreRecovery() {
        if (woh$recoveryOverridden) {
            this.recovery = this.woh$originalRecovery;
            this.woh$recoveryOverridden = false;
        }
    }

    public boolean woh$isRecoveryOverridden() {
        return woh$recoveryOverridden;
    }
}
