package net.kenji.woh.registry.animation;
import net.kenji.woh.api.WOHAnimationUtils;
import net.minecraftforge.registries.RegistryObject;
import yesman.epicfight.api.animation.AnimationManager;
import yesman.epicfight.api.animation.Joint;
import yesman.epicfight.api.animation.types.AirSlashAnimation;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.animation.types.DashAttackAnimation;
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


public class GenericAnimations {
    public static AnimationManager.AnimationAccessor<StaticAnimation> DEFEAT_IDLE;
    public static AnimationManager.AnimationAccessor<StaticAnimation> DEFEAT_KNEEL;

    public static AnimationManager.AnimationAccessor<StaticAnimation> KATAJUTSU_IDLE;

    public static AnimationManager.AnimationAccessor<? extends AttackAnimation> KATAJUTSU_AUTO_1;
    public static AnimationManager.AnimationAccessor<? extends AttackAnimation> KATAJUTSU_AUTO_2;
    public static AnimationManager.AnimationAccessor<? extends AttackAnimation> KATAJUTSU_AUTO_3;

    public static AnimationManager.AnimationAccessor<? extends DashAttackAnimation> COMBAT_FIST_DASH;
    public static AnimationManager.AnimationAccessor<? extends AirSlashAnimation> COMBAT_FIST_AIRKICK;


    public static void build(AnimationManager.AnimationBuilder builder){
        AssetAccessor<? extends HumanoidArmature> biped = Armatures.BIPED;

        DEFEAT_IDLE = builder.nextAccessor("biped/living/generic/defeat_idle", accessor -> new StaticAnimation(true,accessor, biped));
        DEFEAT_KNEEL = builder.nextAccessor("biped/living/generic/defeat_kneel", accessor -> new StaticAnimation(true,accessor, biped));
        KATAJUTSU_IDLE = builder.nextAccessor("biped/living/katajutsu/katajutsu_idle", accessor -> new StaticAnimation(true,accessor, biped));

        KATAJUTSU_AUTO_1 = WOHAnimationUtils.createAttackAnimation(builder,
                WOHAnimationUtils.AttackAnimationType.BASIC_ATTACK,
                "biped/combat/katajutsu/katajutsu_auto_1",
                1,
                0.1F,
                1F,
                2F,
                0.25F,
                new float[]{0.0F},
                new float[]{0.15F},
                new float[]{0.23F},
                new float[]{0.4F},
                new float[]{0.62F},
                new Supplier[]{EpicFightSounds.WHOOSH},
                new Supplier[]{EpicFightSounds.BLUNT_HIT},
                new RegistryObject[]{EpicFightParticles.HIT_BLUNT},
                new Collider[]{ColliderPreset.FIST},
                new Joint[]{biped.get().toolR},
                StunType.SHORT,
                -1,
                -1
        );

        KATAJUTSU_AUTO_2 = WOHAnimationUtils.createAttackAnimation(builder,
                WOHAnimationUtils.AttackAnimationType.BASIC_ATTACK,
                "biped/combat/katajutsu/katajutsu_auto_2",
                1,
                0.1F,
                1F,
                2F,
                0.25F,
                new float[]{0.0F},
                new float[]{0.1F},
                new float[]{0.18F},
                new float[]{0.38F},
                new float[]{0.5F},
                new Supplier[]{EpicFightSounds.WHOOSH},
                new Supplier[]{EpicFightSounds.BLUNT_HIT},
                new RegistryObject[]{EpicFightParticles.HIT_BLUNT},
                new Collider[]{ColliderPreset.FIST},
                new Joint[]{biped.get().toolL},
                StunType.SHORT,
                -1,
                -1
        );
        KATAJUTSU_AUTO_3 = WOHAnimationUtils.createAttackAnimation(builder,
                WOHAnimationUtils.AttackAnimationType.BASIC_ATTACK,
                "biped/combat/katajutsu/katajutsu_auto_3",
                1,
                0.1F,
                1F,
                2F,
                0.25F,
                new float[]{0.0F},
                new float[]{0.25F},
                new float[]{0.38F},
                new float[]{0.60F},
                new float[]{1.05F},
                new Supplier[]{EpicFightSounds.WHOOSH},
                new Supplier[]{EpicFightSounds.BLUNT_HIT},
                new RegistryObject[]{EpicFightParticles.HIT_BLUNT},
                new Collider[]{ColliderPreset.FIST},
                new Joint[]{biped.get().legR},
                StunType.SHORT,
                -1,
                -1
        );
        COMBAT_FIST_DASH = WOHAnimationUtils.createDashAttackAnimation(builder,
                WOHAnimationUtils.AttackAnimationType.DASH_ATTACK,
                "biped/combat/generic/combat_fist_dash",
                2,
                0.05F,
                1F,
                2F,
                0.4F,
                new float[]{0.0F, 0.3F},
                new float[]{0.05F, 0.38F},
                new float[]{0.1F, 0.5F},
                new float[]{0.15F, 0.95F},
                new float[]{0.2F, 1F},
                new Supplier[]{EpicFightSounds.WHOOSH, EpicFightSounds.WHOOSH},
                new Supplier[]{EpicFightSounds.BLUNT_HIT, EpicFightSounds.BLUNT_HIT},
                new RegistryObject[]{EpicFightParticles.HIT_BLUNT, EpicFightParticles.HIT_BLUNT},
                new Collider[]{ColliderPreset.FIST, ColliderPreset.FIST},
                new Joint[]{biped.get().toolL, biped.get().toolR},
                StunType.LONG,
                null,
                -1,
                -1
        );
        COMBAT_FIST_AIRKICK = WOHAnimationUtils.createAirAttackAnimation(builder,
                "biped/combat/generic/combat_fist_airkick",
                1,
                0.1F,
                1F,
                2F,
                1.2F,
                new float[]{0.0F},
                new float[]{0.1F},
                new float[]{0.18F},
                new float[]{0.25F},
                new float[]{0.8F},
                new Supplier[]{EpicFightSounds.WHOOSH_ROD},
                new Supplier[]{EpicFightSounds.BLUNT_HIT},
                new RegistryObject[]{EpicFightParticles.HIT_BLUNT},
                StunType.SHORT,
                new Collider[]{ColliderPreset.FIST},
                new Joint[]{biped.get().toolR},
                new float[]{0.0F, 0.45F},
                -1,
                -1
        );
    }
}
