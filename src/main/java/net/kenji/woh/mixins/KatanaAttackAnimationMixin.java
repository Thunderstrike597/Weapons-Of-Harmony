package net.kenji.woh.mixins;

import net.corruptdog.cdm.api.animation.types.KatanaAttackanimation;
import org.jline.utils.Log;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import yesman.epicfight.api.animation.JointTransform;
import yesman.epicfight.api.animation.Keyframe;
import yesman.epicfight.api.animation.Pose;
import yesman.epicfight.api.animation.TransformSheet;
import yesman.epicfight.api.animation.property.AnimationProperty;
import yesman.epicfight.api.animation.types.*;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.api.client.animation.Layer;
import yesman.epicfight.api.client.animation.property.ClientAnimationProperties;
import yesman.epicfight.api.client.animation.property.JointMaskEntry;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Mixin(value = KatanaAttackanimation.class, remap = false)
public class KatanaAttackAnimationMixin {


    @Inject(method = "setLinkAnimation", at = @At("HEAD"), cancellable = true)
    public void onSetLinkAnimation(AssetAccessor<? extends DynamicAnimation> fromAnimation, Pose startPose, boolean isOnSameLayer, float transitionTimeModifier, LivingEntityPatch<?> entitypatch, LinkAnimation dest, CallbackInfo ci) {
        ci.cancel(); // ← MUST be first

        KatanaAttackanimation self = (KatanaAttackanimation)(Object)this;
        if (!entitypatch.isLogicalClient()) {
            startPose = Animations.EMPTY_ANIMATION.getPoseByTime(entitypatch, 0.0F, 1.0F);
        }

        dest.resetNextStartTime();
        float playTime = self.getPlaySpeed(entitypatch, dest);
        AnimationProperty.PlaybackSpeedModifier playSpeedModifier = (AnimationProperty.PlaybackSpeedModifier)((StaticAnimation)self.getRealAnimation().get()).getProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER).orElse(null);
        if (playSpeedModifier != null) {
            playTime = playSpeedModifier.modify(dest, entitypatch, playTime, 0.0F, playTime);
        }

        playTime = Math.abs(playTime);
        playTime *= 0.05F;
        float linkTime = transitionTimeModifier > 0.0F ? transitionTimeModifier + self.getTransitionTime() : self.getTransitionTime();
        float totalTime = playTime * (float)((int)Math.ceil((double)(linkTime / playTime)));
        float nextStartTime = Math.max(0.0F, -transitionTimeModifier);
        nextStartTime += totalTime - linkTime;
        dest.setNextStartTime(nextStartTime);
        //dest.getTransfroms().clear();
        dest.setTotalTime(totalTime);
        dest.setConnectedAnimations(fromAnimation, self.getAccessor());
        Map<String, JointTransform> data1 = startPose.getJointTransformData();
        Map<String, JointTransform> data2 = self.getPoseByTime(entitypatch, nextStartTime, 0.0F).getJointTransformData();
        Set<String> joint1 = new HashSet<>(isOnSameLayer ? data1.keySet() : Set.of());
        Set<String> joint2 = new HashSet<>(data2.keySet());
        if (entitypatch.isLogicalClient()) {
            JointMaskEntry entry = (JointMaskEntry)((DynamicAnimation)fromAnimation.get()).getJointMaskEntry(entitypatch, false).orElse(null);
            JointMaskEntry entry2 = (JointMaskEntry)self.getJointMaskEntry(entitypatch, true).orElse(null);
            if (entry != null) {
                joint1.removeIf((jointNamex) -> entry.isMasked(((DynamicAnimation)fromAnimation.get()).getProperty(ClientAnimationProperties.LAYER_TYPE).orElse(Layer.LayerType.BASE_LAYER) == Layer.LayerType.BASE_LAYER ? entitypatch.getClientAnimator().currentMotion() : entitypatch.getClientAnimator().currentCompositeMotion(), jointNamex));
            }

            if (entry2 != null) {
                joint2.removeIf((jointNamex) -> entry2.isMasked(self.getProperty(ClientAnimationProperties.LAYER_TYPE).orElse(Layer.LayerType.BASE_LAYER) == Layer.LayerType.BASE_LAYER ? entitypatch.getCurrentLivingMotion() : entitypatch.currentCompositeMotion, jointNamex));
            }
        }

        joint1.addAll(joint2);
        if (linkTime != totalTime) {
            Map<String, JointTransform> firstPose = self.getPoseByTime(entitypatch, 0.0F, 0.0F).getJointTransformData();

            for(String jointName : joint1) {
                Keyframe[] keyframes = new Keyframe[3];
                keyframes[0] = new Keyframe(0.0F, (JointTransform)data1.get(jointName));
                keyframes[1] = new Keyframe(linkTime, (JointTransform)firstPose.get(jointName));
                keyframes[2] = new Keyframe(totalTime, (JointTransform)data2.get(jointName));
                TransformSheet sheet = new TransformSheet(keyframes);
                dest.getAnimationClip().addJointTransform(jointName, sheet);
            }
        } else {
            for(String jointName : joint1) {
                Keyframe[] keyframes = new Keyframe[2];
                keyframes[0] = new Keyframe(0.0F, (JointTransform)data1.get(jointName));
                keyframes[1] = new Keyframe(totalTime, (JointTransform)data2.get(jointName));
                TransformSheet sheet = new TransformSheet(keyframes);
                dest.getAnimationClip().addJointTransform(jointName, sheet);
            }
        }
    }
}
