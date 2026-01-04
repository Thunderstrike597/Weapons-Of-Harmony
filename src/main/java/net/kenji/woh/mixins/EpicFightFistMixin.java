package net.kenji.woh.mixins;


import net.kenji.woh.registry.animation.GenericAnimations;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import yesman.epicfight.api.animation.AnimationManager;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.world.capabilities.item.CapabilityItem;

import java.util.List;

@Mixin(value = CapabilityItem.class, remap = false)
public class EpicFightFistMixin {

    @Shadow
    protected static List<AnimationManager.AnimationAccessor<? extends AttackAnimation>> commonAutoAttackMotion;

    @Inject(method = "<clinit>", at = @At("TAIL"), remap = false)
    private static void replaceCommonAutoAttack(CallbackInfo ci) {
        // Clear the default fist animations
        commonAutoAttackMotion.clear();

        // Add your custom animations
        commonAutoAttackMotion.add(GenericAnimations.COMBAT_FIST_AUTO_1);
        commonAutoAttackMotion.add(GenericAnimations.COMBAT_FIST_AUTO_2);


        commonAutoAttackMotion.add(GenericAnimations.COMBAT_FIST_DASH);
        commonAutoAttackMotion.add(GenericAnimations.COMBAT_FIST_AIRKICK);


    }
}
