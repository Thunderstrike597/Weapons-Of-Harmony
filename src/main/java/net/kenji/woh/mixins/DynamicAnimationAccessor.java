package net.kenji.woh.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;
import yesman.epicfight.api.animation.types.DynamicAnimation;

@Mixin(value = DynamicAnimation.class, remap = false)
public interface DynamicAnimationAccessor {
    @Mutable
    @Accessor("convertTime")
    void woh$setConvertTime(float convertTime);
    
    @Accessor("convertTime")
    float woh$getConvertTime();
}