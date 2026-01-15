package net.kenji.woh.gameasset.animation_types;

import yesman.epicfight.api.animation.LivingMotions;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.model.Armature;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

public class WohStaticAnimation extends StaticAnimation {

    public WohSheathAnimation sheathAnimation = null;
    public LivingMotions motionType = LivingMotions.NONE;
    public WohStaticAnimation(float convertTime, boolean repeat, String path, Armature armature, LivingMotions livingMotion){
        super(convertTime, repeat, path, armature);
        motionType = livingMotion;
    }
    public WohStaticAnimation(float convertTime, boolean repeat, String path, Armature armature, WohSheathAnimation sheathAnimation){
        super(convertTime, repeat, path, armature);
        this.sheathAnimation = sheathAnimation;
    }

    public boolean isSheathedAnimation(){
        return sheathAnimation != null;
    }

    @Override
    public void begin(LivingEntityPatch<?> entitypatch) {
        if(motionType == LivingMotions.BLOCK){
            if(WohSheathAnimation.shouldAnimReplay.get(entitypatch.getOriginal().getUUID()) == null) {
                WohSheathAnimation.shouldAnimReplay.put(entitypatch.getOriginal().getUUID(), false);
            }
            else{
                WohSheathAnimation.shouldAnimReplay.replace(entitypatch.getOriginal().getUUID(), false);
            }
        }
        super.begin(entitypatch);
    }
}
