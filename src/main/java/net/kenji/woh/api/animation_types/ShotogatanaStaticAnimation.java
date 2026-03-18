package net.kenji.woh.api.animation_types;

import net.kenji.woh.api.manager.ShotogatanaManager;
import yesman.epicfight.api.animation.AnimationManager;
import yesman.epicfight.api.animation.types.DynamicAnimation;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.gameasset.Armatures;
import yesman.epicfight.model.armature.HumanoidArmature;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;

import java.util.UUID;

public class ShotogatanaStaticAnimation extends StaticAnimation {

    public ShotogatanaStaticAnimation(boolean isRepeat, AnimationManager.AnimationAccessor<StaticAnimation> accessor, Armatures.ArmatureAccessor<HumanoidArmature> armature){
        super(isRepeat, accessor, armature);
    }

    @Override
    public void begin(LivingEntityPatch<?> entitypatch) {
        super.begin(entitypatch);
        UUID playerId = entitypatch.getOriginal().getUUID();
        ShotogatanaManager.sheathPauseMap.put(playerId, true);
    }

    @Override
    public void end(LivingEntityPatch<?> entitypatch, AssetAccessor<? extends DynamicAnimation> nextAnimation, boolean isEnd) {
        super.end(entitypatch, nextAnimation, isEnd);
        UUID playerId = entitypatch.getOriginal().getUUID();
        ShotogatanaManager.sheathPauseMap.put(playerId, false);
        if(entitypatch instanceof ServerPlayerPatch serverPlayerPatch){
            serverPlayerPatch.modifyLivingMotionByCurrentItem();
        }
    }
}
