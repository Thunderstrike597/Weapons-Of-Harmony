package net.kenji.woh.gameasset.animation_types;

import yesman.epicfight.api.animation.AnimationManager;
import yesman.epicfight.api.animation.AnimationPlayer;
import yesman.epicfight.api.animation.types.DynamicAnimation;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.api.model.Armature;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class WohSheathAnimation extends StaticAnimation {
    public static Map<UUID, Boolean> shouldAnimReplay = new HashMap<>();

    public float endTimeStamp;

    public WohSheathAnimation(float convertTime, AnimationManager.AnimationAccessor<StaticAnimation> accessor, AssetAccessor<? extends Armature> armature, float endTimeStamp){
        super(convertTime, false, accessor, armature);
        this.endTimeStamp = endTimeStamp;
    }

    @Override
    public void begin(LivingEntityPatch<?> entitypatch) {
        if(entitypatch instanceof PlayerPatch<?> playerPatch) {
            UUID playerId = playerPatch.getOriginal().getUUID();
            shouldAnimReplay.put(playerId, false);
        }
        super.begin(entitypatch);
    }


    @Override
    public void tick(LivingEntityPatch<?> entitypatch) {
        if (entitypatch instanceof PlayerPatch<?> playerPatch) {
            AnimationPlayer animationPlayer = playerPatch.getAnimator().getPlayerFor(this.accessor);
            if (animationPlayer != null) {
                if (animationPlayer.getElapsedTime() >= endTimeStamp) {
                    shouldAnimReplay.remove(playerPatch.getOriginal().getUUID());
                }
            }
        }
        super.tick(entitypatch);
    }

    @Override
    public void end(LivingEntityPatch<?> entitypatch, AssetAccessor<? extends DynamicAnimation> nextAnimation, boolean isEnd) {
        if (entitypatch instanceof PlayerPatch<?> playerPatch) {
            shouldAnimReplay.remove(playerPatch.getOriginal().getUUID());

            super.end(entitypatch, nextAnimation, isEnd);
        }
    }
}
