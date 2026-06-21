package net.kenji.woh.registry.animation;
import net.kenji.woh.api.WOHAnimationUtils;
import net.kenji.woh.gameasset.AttackHand;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.RegistryObject;
import yesman.epicfight.api.animation.AnimationManager;
import yesman.epicfight.api.animation.Joint;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.api.collider.Collider;
import yesman.epicfight.gameasset.Armatures;
import yesman.epicfight.gameasset.ColliderPreset;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.model.armature.HumanoidArmature;
import yesman.epicfight.particle.EpicFightParticles;
import yesman.epicfight.world.damagesource.StunType;

import java.util.function.Supplier;


public class WakizashiAnimations {
    public static AnimationManager.AnimationAccessor<StaticAnimation> WAKIZASHI_HOLD;
    public static AnimationManager.AnimationAccessor<StaticAnimation> WAKIZASHI_DUAL_HOLD;


    public static AnimationManager.AnimationAccessor<StaticAnimation> WAKIZASHI_GUARD;

    public static AnimationManager.AnimationAccessor<StaticAnimation> WAKIZASHI_DUAL_GUARD;

    public static void build(AnimationManager.AnimationBuilder builder){
        AssetAccessor<? extends HumanoidArmature> biped = Armatures.BIPED;

        WAKIZASHI_HOLD = WOHAnimationUtils.createLivingAnimation(builder,"biped/living/wakizashi/wakizashi_hold", false, 0.1f, -1, -1,null);
        WAKIZASHI_DUAL_HOLD = WOHAnimationUtils.createLivingAnimation(builder,"biped/living/wakizashi/wakizashi_dual_hold", false, 0.1f, -1, -1,null);

        WAKIZASHI_DUAL_GUARD = WOHAnimationUtils.createLivingAnimation(builder,"biped/skill/wakizashi/wakizashi_dual_guard", false, 0.1f, -1, -1,null);
        WAKIZASHI_GUARD =WOHAnimationUtils.createLivingAnimation(builder,"biped/skill/wakizashi/wakizashi_guard", false, 0.1f, -1, -1,null);

    }
}
