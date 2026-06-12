package net.kenji.woh.registry.animation;
import net.kenji.woh.api.AnimationConfig;
import net.kenji.woh.api.WohAnimationBuilder;
import yesman.epicfight.api.animation.AnimationManager;
import yesman.epicfight.api.animation.types.AirSlashAnimation;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.gameasset.Armatures;
import yesman.epicfight.gameasset.ColliderPreset;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.model.armature.HumanoidArmature;
import yesman.epicfight.particle.EpicFightParticles;


public class ArbitersBladeAnimations {
    public static AnimationManager.AnimationAccessor<StaticAnimation> ARBITERS_BLADE_HOLD;
    public static AnimationManager.AnimationAccessor<StaticAnimation> ARBITERS_BLADE_RUN;

    public static AnimationManager.AnimationAccessor<? extends AttackAnimation> ARBITERS_BLADE_AUTO_1;
    public static AnimationManager.AnimationAccessor<? extends AttackAnimation> ARBITERS_BLADE_AUTO_2;
    public static AnimationManager.AnimationAccessor<? extends AttackAnimation> ARBITERS_BLADE_AUTO_3;
    public static AnimationManager.AnimationAccessor<? extends AttackAnimation> ARBITERS_BLADE_AUTO_4;

    public static AnimationManager.AnimationAccessor<? extends AttackAnimation> ARBITERS_BLADE_SKILL_AUTO_1;
    public static AnimationManager.AnimationAccessor<? extends AttackAnimation> ARBITERS_BLADE_SKILL_AUTO_2;
    public static AnimationManager.AnimationAccessor<? extends AttackAnimation> ARBITERS_BLADE_SKILL_AUTO_3;
    public static AnimationManager.AnimationAccessor<? extends AttackAnimation> ARBITERS_BLADE_SKILL_AIM_ATTACK;

    public static AnimationManager.AnimationAccessor<StaticAnimation> ARBITERS_BLADE_SKILL_ACTIVATE_START;
    public static AnimationManager.AnimationAccessor<StaticAnimation> ARBITERS_BLADE_SKILL_ACTIVATE_MID;
    public static AnimationManager.AnimationAccessor<StaticAnimation> ARBITERS_BLADE_SKILL_ACTIVATE_END;
    public static AnimationManager.AnimationAccessor<StaticAnimation> ARBITERS_BLADE_SKILL_DEACTIVATE;

    public static AnimationManager.AnimationAccessor<StaticAnimation> ARBITERS_BLADE_AIM;
    public static AnimationManager.AnimationAccessor<? extends AttackAnimation> ARBITERS_BLADE_SPIN_ATTACK_RIGHT;
    public static AnimationManager.AnimationAccessor<? extends AttackAnimation> ARBITERS_BLADE_SPIN_ATTACK_LEFT;


    public static void build(AnimationManager.AnimationBuilder builder){
        AssetAccessor<HumanoidArmature> biped = Armatures.BIPED;

        ARBITERS_BLADE_HOLD = builder.nextAccessor("biped/living/arbiters_blade/arbiters_blade_hold", accessor -> new StaticAnimation(true,accessor, biped));

        ARBITERS_BLADE_RUN = builder.nextAccessor("biped/living/arbiters_blade/arbiters_blade_run", accessor -> new StaticAnimation(true,accessor, biped));

        ARBITERS_BLADE_SKILL_ACTIVATE_START = builder.nextAccessor("biped/skill/arbiters_blade/arbiters_blade_skill_activate_start", accessor -> new StaticAnimation(false, accessor, biped));
        ARBITERS_BLADE_SKILL_ACTIVATE_MID = builder.nextAccessor("biped/skill/arbiters_blade/arbiters_blade_skill_activate_mid", accessor -> new StaticAnimation(false, accessor, biped));
        ARBITERS_BLADE_SKILL_ACTIVATE_END = builder.nextAccessor("biped/skill/arbiters_blade/arbiters_blade_skill_activate_end", accessor -> new StaticAnimation(false, accessor, biped));
        ARBITERS_BLADE_SKILL_DEACTIVATE = builder.nextAccessor("biped/skill/arbiters_blade/arbiters_blade_skill_deactivate", accessor -> new StaticAnimation(false, accessor, biped));

        ARBITERS_BLADE_AIM = builder.nextAccessor("biped/skill/arbiters_blade/arbiters_blade_aim", accessor -> new StaticAnimation(true,accessor, biped));

        ARBITERS_BLADE_SKILL_AIM_ATTACK = WohAnimationBuilder.generic(builder,
                AnimationConfig.of("biped/combat/arbiters_blade/arbiters_blade_aim_attack")
                        .phases(0.0F, 0.27F, 0.37F, 0.64F, 1.50F)
                        .speed(0.1F)
                        .swing(EpicFightSounds.WHOOSH)
                        .hit(EpicFightSounds.BLADE_HIT)
                        .particle(EpicFightParticles.HIT_BLADE)
                        .collider(ColliderPreset.LONGSWORD, biped.get().toolR)
                        .build()
        );

        ARBITERS_BLADE_AUTO_1 = WohAnimationBuilder.generic(builder,
                AnimationConfig.of("biped/combat/arbiters_blade/auto_1")
                        .phases(0.0F, 0.44F, 0.53F, 0.62F, 2.15F)
                        .speed(0.17F)
                        .swing(EpicFightSounds.WHOOSH)
                        .hit(EpicFightSounds.BLADE_HIT)
                        .particle(EpicFightParticles.HIT_BLADE)
                        .collider(ColliderPreset.LONGSWORD, biped.get().toolR)
                        .build()
        );
        ARBITERS_BLADE_AUTO_2 = WohAnimationBuilder.generic(builder,
                AnimationConfig.of("biped/combat/arbiters_blade/auto_2")
                        .phases(0.0F, 0.39F, 0.49F, 0.68F, 2.0F)
                        .speed(0.13F)
                        .swing(EpicFightSounds.WHOOSH)
                        .hit(EpicFightSounds.BLADE_HIT)
                        .particle(EpicFightParticles.HIT_BLADE)
                        .collider(ColliderPreset.LONGSWORD, biped.get().toolR)
                        .build()
        );
        ARBITERS_BLADE_AUTO_3 = WohAnimationBuilder.generic(builder,
                AnimationConfig.of("biped/combat/arbiters_blade/auto_3")
                        .phases(0.0F, 0.36F, 0.42F, 0.73F, 0.54F)
                        .phases(0.56F, 0.61F, 0.67F, 0.73F, 2.10F)
                        .speed(0.12F)
                        .swing(EpicFightSounds.WHOOSH)
                        .hit(EpicFightSounds.BLADE_HIT)
                        .particle(EpicFightParticles.HIT_BLADE)
                        .collider(ColliderPreset.LONGSWORD, biped.get().toolR)
                        .build()
        );
        ARBITERS_BLADE_AUTO_4 = WohAnimationBuilder.generic(builder,
                AnimationConfig.of("biped/combat/arbiters_blade/auto_4")
                        .phases(0.0F, 0.39F, 0.48F, 1.46F, 0.75F)
                        .phases(0.84F, 0.96F, 1.04F, 1.46F, 3.0F)
                        .speed(0.125F)
                        .swing(EpicFightSounds.WHOOSH)
                        .hit(EpicFightSounds.BLADE_HIT, 0)
                        .hit(EpicFightSounds.BLUNT_HIT, 1)
                        .particle(EpicFightParticles.HIT_BLADE, 0)
                        .particle(EpicFightParticles.HIT_BLUNT, 1)
                        .collider(ColliderPreset.LONGSWORD, biped.get().toolR, 0)
                        .collider(ColliderPreset.DAGGER, biped.get().toolL, 1)
                        .build()
        );
        ARBITERS_BLADE_SPIN_ATTACK_RIGHT = WohAnimationBuilder.generic(builder,
                AnimationConfig.of("biped/combat/arbiters_blade/right_spin_attack")
                        .phases(0.0F, 0.68F, 0.83F, 0.90F, 2.40F)
                        .speed(0.125F)
                        .swing(EpicFightSounds.WHOOSH)
                        .hit(EpicFightSounds.BLADE_HIT)
                        .particle(EpicFightParticles.HIT_BLADE)
                        .collider(ColliderPreset.LONGSWORD, biped.get().toolR)
                        .build()
        );
        ARBITERS_BLADE_SPIN_ATTACK_LEFT = WohAnimationBuilder.generic(builder,
                AnimationConfig.of("biped/combat/arbiters_blade/left_spin_attack")
                        .phases(0.0F, 0.29F, 0.36F, 1.10F, 0.58F)
                        .phases(0.65F, 0.70F, 0.74F, 1.10F, 2.30F)
                        .speed(0.125F)
                        .swing(EpicFightSounds.WHOOSH)
                        .hit(EpicFightSounds.BLADE_HIT)
                        .particle(EpicFightParticles.HIT_BLADE)
                        .collider(ColliderPreset.LONGSWORD, biped.get().toolR)
                        .build()
        );

        ARBITERS_BLADE_SKILL_AUTO_1 = WohAnimationBuilder.generic(builder,
                AnimationConfig.of("biped/combat/arbiters_blade/beam_slash_auto_1")
                        .phases(0.0F, 0.44F, 0.53F, 0.62F, 2.15F)
                        .speed(0.1F)
                        .swing(EpicFightSounds.WHOOSH)
                        .hit(EpicFightSounds.BLADE_HIT)
                        .particle(EpicFightParticles.HIT_BLADE)
                        .collider(ColliderPreset.LONGSWORD, biped.get().toolR)
                        .build()
        );
        ARBITERS_BLADE_SKILL_AUTO_2 = WohAnimationBuilder.generic(builder,
                AnimationConfig.of("biped/combat/arbiters_blade/beam_slash_auto_2")
                        .phases(0.0F, 0.34F, 0.42F, 0.62F, 1.85F)
                        .speed(0.1F)
                        .swing(EpicFightSounds.WHOOSH)
                        .hit(EpicFightSounds.BLADE_HIT)
                        .particle(EpicFightParticles.HIT_BLADE)
                        .collider(ColliderPreset.LONGSWORD, biped.get().toolR)
                        .build()
        );
        ARBITERS_BLADE_SKILL_AUTO_3 = WohAnimationBuilder.generic(builder,
                AnimationConfig.of("biped/combat/arbiters_blade/beam_slash_auto_3")
                        .phases(0.0F, 0.46F, 0.55F, 0.78F, 1.85F)
                        .speed(0.1F)
                        .swing(EpicFightSounds.WHOOSH)
                        .hit(EpicFightSounds.BLADE_HIT)
                        .particle(EpicFightParticles.HIT_BLADE)
                        .collider(ColliderPreset.LONGSWORD, biped.get().toolR)
                        .build()
        );
    }
}
