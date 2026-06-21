package net.kenji.woh.registry.animation;
import net.kenji.woh.api.WOHAnimationUtils;
import net.kenji.woh.gameasset.AttackHand;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.RegistryObject;
import yesman.epicfight.api.animation.AnimationManager;
import yesman.epicfight.api.animation.Joint;
import yesman.epicfight.api.animation.types.AirSlashAnimation;
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


public class OdachiAnimations {
    public static AnimationManager.AnimationAccessor<StaticAnimation> ODACHI_HOLD;
    public static AnimationManager.AnimationAccessor<StaticAnimation> ODACHI_RUN;


    public static AnimationManager.AnimationAccessor<StaticAnimation> ODACHI_GUARD;

    public static void build(AnimationManager.AnimationBuilder builder){
        AssetAccessor<? extends HumanoidArmature> biped = Armatures.BIPED;

        ODACHI_HOLD = builder.nextAccessor("biped/living/odachi/odachi_hold", accessor -> new StaticAnimation(true,accessor, biped));
        ODACHI_RUN = builder.nextAccessor("biped/living/odachi/odachi_run", accessor -> new StaticAnimation(true,accessor, biped));
        ODACHI_GUARD = builder.nextAccessor("biped/skill/odachi/odachi_guard", accessor -> new StaticAnimation(true,accessor, biped));



    }
}
