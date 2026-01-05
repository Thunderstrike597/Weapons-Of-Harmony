package net.kenji.woh.gameasset.animations;

import yesman.epicfight.api.animation.AnimationManager;
import yesman.epicfight.api.animation.types.AirSlashAnimation;
import yesman.epicfight.api.animation.types.DynamicAnimation;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.api.model.Armature;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class BasisAirAttackAnimation extends AirSlashAnimation {
    public static final Map<UUID, Boolean> isAttacking = new HashMap<>();


    public BasisAirAttackAnimation(float convertTime, AnimationManager.AnimationAccessor<? extends AirSlashAnimation> accessor, AssetAccessor<? extends Armature> armature, Phase... phases) {
        super(convertTime, accessor, armature, phases);
    }

    @Override
    public void end(LivingEntityPatch<?> entitypatch, AssetAccessor<? extends DynamicAnimation> nextAnimation, boolean isEnd) {
       if(entitypatch instanceof PlayerPatch<?> playerPatch) {
          UUID playerID = playerPatch.getOriginal().getUUID();
           isAttacking.put(playerID, false);
       }
        super.end(entitypatch, nextAnimation, isEnd);
    }

    @Override
    public void begin(LivingEntityPatch<?> entitypatch) {
        if(entitypatch instanceof PlayerPatch<?> playerPatch) {
            UUID playerID = playerPatch.getOriginal().getUUID();
            isAttacking.put(playerID, true);
        }
        super.begin(entitypatch);
    }
}

