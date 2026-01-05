package net.kenji.woh.registry.animation;
import net.kenji.woh.api.WOHAnimationUtils;
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

    public static AnimationManager.AnimationAccessor<? extends AttackAnimation> ODACHI_AUTO_1;
    public static AnimationManager.AnimationAccessor<? extends AttackAnimation> ODACHI_AUTO_2;
    public static AnimationManager.AnimationAccessor<? extends AttackAnimation> ODACHI_AUTO_3;
    public static AnimationManager.AnimationAccessor<? extends AttackAnimation> ODACHI_AUTO_4;
    public static AnimationManager.AnimationAccessor<? extends AttackAnimation> ODACHI_AUTO_5;

    public static AnimationManager.AnimationAccessor<? extends AttackAnimation> ODACHI_DASH;
    public static AnimationManager.AnimationAccessor<? extends AirSlashAnimation> ODACHI_AIRSLASH;

    public static AnimationManager.AnimationAccessor<StaticAnimation> ODACHI_GUARD;

    public static void build(AnimationManager.AnimationBuilder builder){
        AssetAccessor<? extends HumanoidArmature> biped = Armatures.BIPED;

        ODACHI_HOLD = builder.nextAccessor("biped/living/odachi/odachi_hold", accessor -> new StaticAnimation(true,accessor, biped));
        ODACHI_RUN = builder.nextAccessor("biped/living/odachi/odachi_run", accessor -> new StaticAnimation(true,accessor, biped));
        ODACHI_GUARD = builder.nextAccessor("biped/skill/odachi/odachi_guard", accessor -> new StaticAnimation(true,accessor, biped));


        ODACHI_AUTO_1 = WOHAnimationUtils.createAttackAnimation(builder,
               WOHAnimationUtils.AttackAnimationType.BASIC_ATTACK,
                "biped/combat/odachi/odachi_auto_1",
                1,
                0.35F,
                0.6F,
                7F,
                0.45F,
                new float[]{0.0F},
                new float[]{0.4F},
                new float[]{0.5F},
                new float[]{0.88f},
                new float[]{1f},
                new Supplier[]{EpicFightSounds.WHOOSH_BIG},
                new Supplier[]{EpicFightSounds.BLADE_HIT},
                new RegistryObject[]{EpicFightParticles.HIT_BLADE},
                new Collider[]{ColliderPreset.GREATSWORD},
                new Joint[]{biped.get().toolR},
                StunType.SHORT,
                -1F,
                -1F

        );

        ODACHI_AUTO_2 = WOHAnimationUtils.createAttackAnimation(builder,
                WOHAnimationUtils.AttackAnimationType.BASIC_ATTACK,
                "biped/combat/odachi/odachi_auto_2",
                1,
                0.35F,
                0.6F,
                8F,
                0.45F,
                new float[]{0.0F},
                new float[]{0.48F},
                new float[]{0.55F},
                new float[]{0.96f},
                new float[]{1.22f},
                new Supplier[]{EpicFightSounds.WHOOSH_BIG},
                new Supplier[]{EpicFightSounds.BLADE_HIT},
                new RegistryObject[]{EpicFightParticles.HIT_BLADE},
                new Collider[]{ColliderPreset.GREATSWORD},
                new Joint[]{biped.get().toolR},
                StunType.SHORT,
                -1F,
                -1F

        );
        ODACHI_AUTO_3 = WOHAnimationUtils.createAttackAnimation(builder,
                WOHAnimationUtils.AttackAnimationType.BASIC_ATTACK,
                "biped/combat/odachi/odachi_auto_3",
                1,
                0.35F,
                0.6F,
                7F,
                0.45F,
                new float[]{0.0F},
                new float[]{0.54F},
                new float[]{0.65F},
                new float[]{1.1f},
                new float[]{1.35f},
                new Supplier[]{EpicFightSounds.WHOOSH_BIG},
                new Supplier[]{EpicFightSounds.BLADE_HIT},
                new RegistryObject[]{EpicFightParticles.HIT_BLADE},
                new Collider[]{ColliderPreset.GREATSWORD},
                new Joint[]{biped.get().toolR},
                StunType.SHORT,
                -1F,
                -1F
        );
        ODACHI_AUTO_4 = WOHAnimationUtils.createAttackAnimation(builder,
                WOHAnimationUtils.AttackAnimationType.BASIC_ATTACK,
                "biped/combat/odachi/odachi_auto_4",
                1,
                0.35F,
                0.6F,
                7F,
                0.45F,
                new float[]{0.0F},
                new float[]{0.4F},
                new float[]{0.54F},
                new float[]{1.07f},
                new float[]{1.32f},
                new Supplier[]{EpicFightSounds.WHOOSH_BIG},
                new Supplier[]{EpicFightSounds.BLADE_HIT},
                new RegistryObject[]{EpicFightParticles.HIT_BLADE},
                new Collider[]{ColliderPreset.GREATSWORD},
                new Joint[]{biped.get().toolR},
                StunType.SHORT,
                -1F,
                -1F
        );
        ODACHI_AUTO_5 = WOHAnimationUtils.createAttackAnimation(builder,
                WOHAnimationUtils.AttackAnimationType.BASIC_ATTACK,
                "biped/combat/odachi/odachi_auto_5",
                1,
                0.35F,
                0.6F,
                7F,
                0.45F,
                new float[]{0.0F},
                new float[]{0.42F},
                new float[]{0.58F},
                new float[]{0.9f},
                new float[]{1.32f},
                new Supplier[]{EpicFightSounds.WHOOSH_BIG},
                new Supplier[]{EpicFightSounds.BLADE_HIT},
                new RegistryObject[]{EpicFightParticles.HIT_BLADE},
                new Collider[]{ColliderPreset.GREATSWORD},
                new Joint[]{biped.get().toolR},
                StunType.SHORT,
                -1F,
                -1F
        );
        ODACHI_DASH = WOHAnimationUtils.createDashAttackAnimation(builder,
                WOHAnimationUtils.AttackAnimationType.DASH_ATTACK,
                "biped/combat/odachi/odachi_dash",
                1,
                0.05F,
                1F,
                6F,
                2F,
                new float[]{0.0F},
                new float[]{0.45F},
                new float[]{0.57F},
                new float[]{0.96F},
                new float[]{1.52F},
                new Supplier[]{EpicFightSounds.WHOOSH_BIG},
                new Supplier[]{EpicFightSounds.BLADE_HIT},
                new RegistryObject[]{EpicFightParticles.HIT_BLADE},
                new Collider[]{ColliderPreset.GREATSWORD},
                new Joint[]{biped.get().toolR},
                StunType.LONG,
                null,
                -1F,
                -1F
        );
        ODACHI_AIRSLASH = WOHAnimationUtils.createAirAttackAnimation(builder,
                "biped/combat/odachi/odachi_airslash",
                1,
                0.1F,
                1F,
                5.75F,
                2F,
                new float[]{0.0F},
                new float[]{0.35F},
                new float[]{0.52F},
                new float[]{0.95F},
                new float[]{1.18F},
                new Supplier[]{EpicFightSounds.WHOOSH_SHARP},
                new Supplier[]{EpicFightSounds.BLADE_HIT},
                new RegistryObject[]{EpicFightParticles.HIT_BLADE},
                StunType.LONG,
                new Collider[]{ColliderPreset.GREATSWORD},
                new Joint[]{biped.get().toolR},
                new float[]{0.05F, 1.10F},
                -1,
                1
        );

    }
}
