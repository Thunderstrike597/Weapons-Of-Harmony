package net.kenji.woh.registry.animation;
import net.kenji.woh.api.AnimationConfig;
import net.kenji.woh.api.WOHAnimationUtils;
import net.kenji.woh.api.WohAnimationBuilder;
import net.kenji.woh.api.animation_types.ShotogatanaAttackAnimation;
import net.kenji.woh.gameasset.AttackHand;
import net.kenji.woh.gameasset.WohColliderPreset;
import net.kenji.woh.registry.WohSounds;
import net.minecraft.sounds.SoundEvents;
import net.minecraftforge.registries.RegistryObject;
import yesman.epicfight.api.animation.AnimationManager;
import yesman.epicfight.api.animation.Joint;
import yesman.epicfight.api.animation.types.AirSlashAnimation;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.animation.types.BasicAttackAnimation;
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


public class ShotogatanaAnimations {

    public static AnimationManager.AnimationAccessor<StaticAnimation> SHOTOGATANA_UNSHEATHED_IDLE;
    public static AnimationManager.AnimationAccessor<StaticAnimation> SHOTOGATANA_UNSHEATHED_WALK;
    public static AnimationManager.AnimationAccessor<StaticAnimation> SHOTOGATANA_UNSHEATHED_RUN;
    public static AnimationManager.AnimationAccessor<StaticAnimation> SHOTOGATANA_IDLE;
    public static AnimationManager.AnimationAccessor<StaticAnimation> SHOTOGATANA_WALK;
    public static AnimationManager.AnimationAccessor<StaticAnimation> SHOTOGATANA_RUN;
    public static AnimationManager.AnimationAccessor<StaticAnimation> SHOTOGATANA_GUARD;
    public static AnimationManager.AnimationAccessor<StaticAnimation> SHOTOGATANA_UNSHEATHED_GUARD;
    public static AnimationManager.AnimationAccessor<StaticAnimation> SHOTOGATANA_SHEATH;
    public static AnimationManager.AnimationAccessor<StaticAnimation> SHOTOGATANA_SHEATH_ALT1;
    public static AnimationManager.AnimationAccessor<StaticAnimation> SHOTOGATANA_SHEATH_ALT2;
    public static AnimationManager.AnimationAccessor<StaticAnimation> SHOTOGATANA_NEW_SHEATH;

    public static AnimationManager.AnimationAccessor<StaticAnimation> SHOTOGATANA_UNSHEATH;

    public static AnimationManager.AnimationAccessor<? extends AttackAnimation> SHOTOGATANA_AUTO_1;
    public static AnimationManager.AnimationAccessor<? extends AttackAnimation> SHOTOGATANA_AUTO_2;
    public static AnimationManager.AnimationAccessor<? extends AttackAnimation> SHOTOGATANA_AUTO_3;
    public static AnimationManager.AnimationAccessor<? extends AttackAnimation> SHOTOGATANA_AUTO_4;
    public static AnimationManager.AnimationAccessor<? extends AttackAnimation> SHOTOGATANA_AUTO_4_SPIN;

    public static AnimationManager.AnimationAccessor<? extends AttackAnimation> SHOTOGATANA_AUTO_5;
    public static AnimationManager.AnimationAccessor<? extends AttackAnimation> SHOTOGATANA_AUTO_6;

    public static AnimationManager.AnimationAccessor<? extends AttackAnimation> SHOTOGATANA_SKILL_COMBO_1;
    public static AnimationManager.AnimationAccessor<? extends AttackAnimation> SHOTOGATANA_SKILL_COMBO_2;
    public static AnimationManager.AnimationAccessor<? extends AttackAnimation> SHOTOGATANA_SKILL_COMBO_3;






    public static void build(AnimationManager.AnimationBuilder builder){
        AssetAccessor<? extends HumanoidArmature> biped = Armatures.BIPED;

        SHOTOGATANA_UNSHEATHED_IDLE = builder.nextAccessor("biped/living/shotogatana/shotogatana_unsheathed_idle", accessor -> new StaticAnimation(true,accessor, biped));
        SHOTOGATANA_UNSHEATHED_WALK = builder.nextAccessor("biped/living/shotogatana/shotogatana_unsheathed_walk", accessor -> new StaticAnimation(true,accessor, biped));
        SHOTOGATANA_UNSHEATHED_GUARD = builder.nextAccessor("biped/skill/shotogatana/shotogatana_guard_unsheathed", accessor -> new StaticAnimation(true,accessor, biped));
        SHOTOGATANA_GUARD = builder.nextAccessor("biped/skill/shotogatana/shotogatana_guard", accessor -> new StaticAnimation(true,accessor, biped));

        SHOTOGATANA_UNSHEATHED_RUN = builder.nextAccessor("biped/living/shotogatana/shotogatana_unsheathed_run", accessor -> new StaticAnimation(true,accessor, biped));
        SHOTOGATANA_SHEATH = WOHAnimationUtils.createSheathAnimation(builder,"biped/skill/shotogatana/shotogatana_sheathe", 0.1f, 1.73f, null);
        SHOTOGATANA_SHEATH_ALT1 = WOHAnimationUtils.createSheathAnimation(builder,"biped/skill/shotogatana/shotogatana_sheathe_alt1",   0.1f, 2.5f, null);
        SHOTOGATANA_SHEATH_ALT2 = WOHAnimationUtils.createSheathAnimation(builder,"biped/skill/shotogatana/shotogatana_sheathe_alt2",  0.1f, 1.93f, null);
        SHOTOGATANA_NEW_SHEATH = WOHAnimationUtils.createSheathAnimation(builder,"biped/skill/shotogatana/shotogatana_new_sheathe", 0.1f, 1.10f, null);

        SHOTOGATANA_UNSHEATH = WOHAnimationUtils.createShotogatanaLivingAnimation(builder,"biped/skill/shotogatana/shotogatana_unsheathe", false, 0.1f, 0.5f, -1, null);

        SHOTOGATANA_IDLE = WOHAnimationUtils.createLivingAnimation(builder,"biped/living/shotogatana/shotogatana_idle", true, 0.1f, -1f, -1, null);
        SHOTOGATANA_WALK = WOHAnimationUtils.createLivingAnimation(builder,"biped/living/shotogatana/shotogatana_walk", true, 0.1f, -1f, -1, null);
        SHOTOGATANA_RUN = WOHAnimationUtils.createLivingAnimation(builder,"biped/living/shotogatana/shotogatana_run", true, 0.1f, -1f, -1, null);


        SHOTOGATANA_AUTO_1 = WohAnimationBuilder.shotogatana(builder,
                AnimationConfig.of("biped/combat/shotogatana/auto_1")
                        .phases(0.0F, 0.28F, 0.34F, 0.52F, 1.40F)
                        .swing(EpicFightSounds.WHOOSH)
                        .speed(0.2F)
                        .hit(EpicFightSounds.BLUNT_HIT)
                        .particle(EpicFightParticles.HIT_BLUNT)
                        .collider(WohColliderPreset.SHEATH, biped.get().toolL)
                        .build()
        );
        SHOTOGATANA_AUTO_2 = WohAnimationBuilder.shotogatana(builder,
                AnimationConfig.of("biped/combat/shotogatana/auto_2")
                        .phases(0.0F, 0.46F, 0.53F, 0.64F, 1.82F)
                        .swing(EpicFightSounds.WHOOSH)
                        .speed(0.2F)
                        .hit(EpicFightSounds.BLUNT_HIT)
                        .particle(EpicFightParticles.HIT_BLUNT)
                        .collider(ColliderPreset.DAGGER, biped.get().toolR)
                        .build()
        );
        SHOTOGATANA_AUTO_3 = WohAnimationBuilder.shotogatana(builder,
                AnimationConfig.of("biped/combat/shotogatana/auto_3")
                        .phases(0.0F, 0.40F, 0.47F, 0.60F, 1.70F)
                        .swing(EpicFightSounds.WHOOSH_SHARP)
                        .speed(0.2F)
                        .hit(EpicFightSounds.BLADE_HIT)
                        .particle(EpicFightParticles.HIT_BLADE)
                        .collider(WohColliderPreset.SHOTOGATANA, biped.get().toolR)
                        .sheathe(0.36F, 1.23F)
                        .build()
        );
        SHOTOGATANA_AUTO_4 = WohAnimationBuilder.shotogatana(builder,
                AnimationConfig.of("biped/combat/shotogatana/auto_4")
                        .phases(0.0F, 0.62F, 0.70F, 0.84F, 1.20F)
                        .swing(EpicFightSounds.WHOOSH)
                        .hit(EpicFightSounds.BLADE_HIT)
                        .particle(EpicFightParticles.HIT_BLADE)
                        .collider(WohColliderPreset.SHOTOGATANA, biped.get().toolR)
                        .sheathe(0.0F, 1.58F)
                        .build()
        );
        SHOTOGATANA_AUTO_4_SPIN = WohAnimationBuilder.shotogatana(builder,
                AnimationConfig.of("biped/combat/shotogatana/auto_spin_4")
                        .phases(0.0F, 0.62F, 0.70F, 0.84F, 1.20F)
                        .swing(EpicFightSounds.WHOOSH)
                        .hit(EpicFightSounds.BLADE_HIT)
                        .particle(EpicFightParticles.HIT_BLADE)
                        .collider(WohColliderPreset.SHOTOGATANA, biped.get().toolR)
                        .sheathe(0.0F, 1.58F)
                        .build()
        );
        SHOTOGATANA_AUTO_5 = WohAnimationBuilder.shotogatana(builder,
                AnimationConfig.of("biped/combat/shotogatana/auto_5")
                        .phases(0.0F, 0.40F, 0.50F, 0.73F, 1.20F)
                        .phases(0.75F, 0.80F, 0.95F, 1.38F, 1.20F)
                        .swing(EpicFightSounds.WHOOSH)
                        .hit(EpicFightSounds.BLADE_HIT, 0)
                        .hit(EpicFightSounds.BLUNT_HIT, 1)
                        .particle(EpicFightParticles.HIT_BLADE, 0)
                        .particle(EpicFightParticles.HIT_BLUNT, 1)
                        .collider(WohColliderPreset.SHOTOGATANA, biped.get().toolR, 0)
                        .collider(WohColliderPreset.SHEATH, biped.get().toolL, 1)
                        .sheathe(0.0F, 2.22F)
                        .build()
        );
        SHOTOGATANA_AUTO_6 = WohAnimationBuilder.shotogatana(builder,
                AnimationConfig.of("biped/combat/shotogatana/auto_6")
                        .phases(0.0F, 0.54F, 0.61F, 1.58F, 1.52F)
                        .swing(EpicFightSounds.WHOOSH)
                        .hit(EpicFightSounds.BLADE_HIT, 0)
                        .particle(EpicFightParticles.HIT_BLADE, 0)
                        .collider(WohColliderPreset.SHOTOGATANA, biped.get().toolR, 0)
                        .sheathe(0.0F, 1.50F)
                        .build()
        );
    }
}
