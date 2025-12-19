package net.kenji.woh.gameasset.animations;

import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.model.Armature;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;

import java.util.UUID;

public class WohStaticAnimation extends StaticAnimation {

    public WohSheathAnimation sheathAnimation = null;
    public WohStaticAnimation(float convertTime, boolean repeat, String path, Armature armature){
        super(convertTime, repeat, path, armature);
        this.sheathAnimation = sheathAnimation;
    }
    public WohStaticAnimation(float convertTime, boolean repeat, String path, Armature armature, WohSheathAnimation sheathAnimation){
        super(convertTime, repeat, path, armature);
        this.sheathAnimation = sheathAnimation;
    }

    public boolean isSheathedAnimation(){
        return sheathAnimation != null;
    }
}
