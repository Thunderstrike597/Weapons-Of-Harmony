package net.kenji.woh.registry.animation;
import net.kenji.woh.api.AnimationConfig;
import net.kenji.woh.api.WOHAnimationUtils;
import net.kenji.woh.api.WohAnimationBuilder;
import reascer.wom.gameasset.colliders.WOMWeaponColliders;
import yesman.epicfight.api.animation.AnimationManager;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.gameasset.Armatures;
import yesman.epicfight.gameasset.ColliderPreset;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.model.armature.HumanoidArmature;
import yesman.epicfight.particle.EpicFightParticles;


public class TenraiAnimations {
    public static AnimationManager.AnimationAccessor<StaticAnimation> TENRAI_HOLD;
    public static AnimationManager.AnimationAccessor<StaticAnimation> TENRAI_SKILL_HOLD;
    public static AnimationManager.AnimationAccessor<StaticAnimation> TENRAI_SKILL_WALK;
    public static AnimationManager.AnimationAccessor<StaticAnimation> TENRAI_SKILL_RUN;

    public static AnimationManager.AnimationAccessor<StaticAnimation> TENRAI_SKILL_ACTIVATE;
    public static AnimationManager.AnimationAccessor<StaticAnimation> TENRAI_SKILL_DEACTIVATE;

    public static AnimationManager.AnimationAccessor<? extends AttackAnimation> TENRAI_AUTO_1;
    public static AnimationManager.AnimationAccessor<? extends AttackAnimation> TENRAI_AUTO_2;
    public static AnimationManager.AnimationAccessor<? extends AttackAnimation> TENRAI_AUTO_3;
    public static AnimationManager.AnimationAccessor<? extends AttackAnimation> TENRAI_AUTO_4;
    public static AnimationManager.AnimationAccessor<? extends AttackAnimation> TENRAI_AUTO_5;
    public static AnimationManager.AnimationAccessor<? extends AttackAnimation> TENRAI_DASH;

    public static AnimationManager.AnimationAccessor<AttackAnimation> TENRAI_AIRSLASH;

    public static AnimationManager.AnimationAccessor<? extends AttackAnimation> TENRAI_SKILL_AUTO_1;
    public static AnimationManager.AnimationAccessor<? extends AttackAnimation> TENRAI_SKILL_AUTO_2;
    public static AnimationManager.AnimationAccessor<? extends AttackAnimation> TENRAI_SKILL_AUTO_3;
    public static AnimationManager.AnimationAccessor<? extends AttackAnimation> TENRAI_SKILL_AUTO_4;

    public static AnimationManager.AnimationAccessor<? extends AttackAnimation> TENRAI_SKILL_DASH;

    public static AnimationManager.AnimationAccessor<AttackAnimation> TENRAI_SKILL_COMBO_1;
    public static AnimationManager.AnimationAccessor<AttackAnimation> TENRAI_SKILL_COMBO_2;
    public static AnimationManager.AnimationAccessor<AttackAnimation> TENRAI_SKILL_COMBO_3;
    public static AnimationManager.AnimationAccessor<AttackAnimation> TENRAI_SKILL_COMBO_4;


    public static void build(AnimationManager.AnimationBuilder builder){
        AssetAccessor<? extends HumanoidArmature> biped = Armatures.BIPED;

        TENRAI_HOLD = builder.nextAccessor("biped/living/tenrai/hold", accessor -> new StaticAnimation(true, accessor, biped));
        TENRAI_SKILL_HOLD = builder.nextAccessor("biped/living/tenrai/skill_hold", accessor -> new StaticAnimation(true, accessor, biped));
        TENRAI_SKILL_WALK = builder.nextAccessor("biped/living/tenrai/skill_walk", accessor -> new StaticAnimation(true, accessor, biped));
        TENRAI_SKILL_RUN = builder.nextAccessor("biped/living/tenrai/skill_run", accessor -> new StaticAnimation(true, accessor, biped));

        TENRAI_SKILL_ACTIVATE = WOHAnimationUtils.createSplitAnimation(builder,"biped/skill/tenrai/tenrai_skill_activate", 0.1f, 1.0F, -1,null);
        TENRAI_SKILL_DEACTIVATE = WOHAnimationUtils.createSplitAnimation(builder,"biped/skill/tenrai/tenrai_skill_deactivate", 0.1f, -1, 1.0F,null);

        TENRAI_AUTO_1 = WohAnimationBuilder.createAttackAnimation(builder,
                AnimationConfig.of("biped/combat/tenrai/auto_1")
                        .phases(0.0F, 0.20F, 0.40F, 1.0F, 0.42F)
                        .phases(0.44F, 0.52F, 0.62F, 1.0F, 0.63F)
                        .phases(0.68F, 0.72F, 0.76F, 1.0F, 2.26F)
                        .hit(EpicFightSounds.BLADE_HIT)
                        .swing(EpicFightSounds.WHOOSH_ROD)
                        .particle(EpicFightParticles.HIT_BLADE)
                        .collider(WOMWeaponColliders.STAFF, biped.get().toolR)
                        .movementMultiplier(1.75F)

                        .build());
        TENRAI_AUTO_2 = WohAnimationBuilder.createAttackAnimation(builder,
                AnimationConfig.of("biped/combat/tenrai/auto_2")
                        .phases(0.0F, 0.28F, 0.31F, 1.60F, 0.32F)
                        .phases(0.33F, 0.34F, 0.35F, 1.60F, 0.40F)
                        .phases(0.45F, 0.55F, 0.82F, 1.60F, 0.85F)
                        .phases(1.0F, 1.20F, 1.30F, 1.60F, 2.80F)
                        .hit(EpicFightSounds.BLADE_HIT)
                        .hit(EpicFightSounds.BLUNT_HIT, 3)
                        .swing(EpicFightSounds.WHOOSH_ROD)
                        .swing(EpicFightSounds.WHOOSH, 3)
                        .particle(EpicFightParticles.HIT_BLADE)
                        .particle(EpicFightParticles.HIT_BLUNT, 3)
                        .collider(WOMWeaponColliders.STAFF, biped.get().toolR)
                        .collider(ColliderPreset.DAGGER, biped.get().toolL, 3)
                        .movementMultiplier(1.5F)
                        .build());
        TENRAI_AUTO_3 = WohAnimationBuilder.createAttackAnimation(builder,
                AnimationConfig.of("biped/combat/tenrai/auto_3")
                        .phases(0.0F, 0.28F, 0.44F, 1.38F, 0.45F)
                        .phases(0.46F, 0.52F, 0.57F, 1.38F, 0.58F)
                        .phases(0.60F, 0.70F, 0.80F, 1.38F, 0.82F)
                        .phases(0.85F, 0.90F, 0.98F, 1.38F, 3.20F)
                        .hit(EpicFightSounds.BLADE_HIT)
                        .swing(EpicFightSounds.WHOOSH_ROD)
                        .particle(EpicFightParticles.HIT_BLADE)
                        .collider(WOMWeaponColliders.STAFF, biped.get().toolR)
                        .build());
        TENRAI_AUTO_4 = WohAnimationBuilder.createAttackAnimation(builder,
                AnimationConfig.of("biped/combat/tenrai/auto_4")
                        .phases(0.0F, 0.28F, 0.58F, 1.18F, 0.60F)
                        .phases(0.62F, 0.65F, 0.69F, 1.18F, 0.70F)
                        .phases(0.72F, 0.75F, 0.86F, 1.18F, 0.88F)
                        .phases(0.90F, 0.95F, 0.98F, 1.18F, 1.95F)
                        .hit(EpicFightSounds.BLADE_HIT)
                        .hit(EpicFightSounds.BLUNT_HIT, 3)
                        .swing(EpicFightSounds.WHOOSH_ROD)
                        .swing(EpicFightSounds.WHOOSH, 3)
                        .particle(EpicFightParticles.HIT_BLADE)
                        .particle(EpicFightParticles.HIT_BLUNT, 3)
                        .collider(WOMWeaponColliders.STAFF, biped.get().toolR)
                        .collider(ColliderPreset.DAGGER, biped.get().toolL, 3)
                        .build());
        TENRAI_AUTO_5 = WohAnimationBuilder.createAttackAnimation(builder,
                AnimationConfig.of("biped/combat/tenrai/auto_5")
                        .convert(0.32F)
                        .phases(0.0F, 0.28F, 0.35F, 1.12F, 0.40F)
                        .phases(0.42F, 0.45F, 0.50F, 1.12F, 2.35F)
                        .hit(EpicFightSounds.BLADE_HIT)
                        .swing(EpicFightSounds.WHOOSH_ROD)
                        .particle(EpicFightParticles.HIT_BLADE)
                        .collider(WOMWeaponColliders.STAFF, biped.get().toolR)
                        .build());
        TENRAI_SKILL_AUTO_1 = WohAnimationBuilder.createAttackAnimation(builder,
                AnimationConfig.of("biped/combat/tenrai/skill_auto_1")
                        .convert(0.32F)
                        .phases(0.0F, 0.28F, 0.48F, 0.65F, 2.10F)
                        .hit(EpicFightSounds.BLADE_HIT)
                        .swing(EpicFightSounds.WHOOSH)
                        .particle(EpicFightParticles.HIT_BLADE)
                        .collider(ColliderPreset.SWORD, biped.get().toolL)
                        .movementMultiplier(2.2F)
                        .build());
        TENRAI_SKILL_AUTO_2 = WohAnimationBuilder.createAttackAnimation(builder,
                AnimationConfig.of("biped/combat/tenrai/skill_auto_2")
                        .convert(0.1F)
                        .phases(0.0F, 0.36F, 0.42F, 0.62F, 0.45F)
                        .phases(0.46F, 0.48F, 0.56F, 0.62F, 2.10F)
                        .movementMultiplier(2.2F)
                        .hit(EpicFightSounds.BLADE_HIT)
                        .swing(EpicFightSounds.WHOOSH)
                        .particle(EpicFightParticles.HIT_BLADE)
                        .collider(ColliderPreset.SWORD, biped.get().toolL, 0)
                        .collider(ColliderPreset.LONGSWORD, biped.get().toolR, 1)
                        .build());
        TENRAI_SKILL_AUTO_3 = WohAnimationBuilder.createAttackAnimation(builder,
                AnimationConfig.of("biped/combat/tenrai/skill_auto_3")
                        .convert(0.1F)
                        .phases(0.0F, 0.44F, 0.63F, 0.85F, 0.64F)
                        .phases(0.65F, 0.68F, 0.78F, 0.85F, 2.30F)
                        .movementMultiplier(2.2F)
                        .hit(EpicFightSounds.BLADE_HIT)
                        .swing(EpicFightSounds.WHOOSH)
                        .particle(EpicFightParticles.HIT_BLADE)
                        .collider(ColliderPreset.LONGSWORD, biped.get().toolR, 0)
                        .collider(ColliderPreset.SWORD, biped.get().toolL, 1)
                        .build());
        TENRAI_SKILL_AUTO_4 = WohAnimationBuilder.createAttackAnimation(builder,
                AnimationConfig.of("biped/combat/tenrai/skill_auto_4")
                        .convert(0.1F)
                        .speed(0.025F)
                        .phases(0.0F, 0.50F, 0.64F, 1.26F, 0.65F)
                        .phases(0.66F, 0.68F, 0.83F, 1.26F, 0.84F)
                        .phases(0.86F, 0.88F, 0.96F, 1.26F, 2.60F)


                        .hit(EpicFightSounds.BLADE_HIT)
                        .swing(EpicFightSounds.WHOOSH)
                        .particle(EpicFightParticles.HIT_BLADE)
                        .collider(ColliderPreset.LONGSWORD, biped.get().toolR, 0)
                        .collider(ColliderPreset.SWORD, biped.get().toolL, 1)
                        .collider(ColliderPreset.LONGSWORD, biped.get().toolR, 2)
                        .build());

        TENRAI_DASH = WohAnimationBuilder.createAttackAnimation(builder,
                AnimationConfig.of("biped/combat/tenrai/dash")
                        .phases(0.0F, 0.20F, 0.40F, 1.0F, 0.42F)
                        .phases(0.44F, 0.52F, 0.62F, 1.0F, 0.63F)
                        .phases(0.68F, 0.72F, 0.76F, 1.0F, 2.26F)
                        .hit(EpicFightSounds.BLADE_HIT)
                        .swing(EpicFightSounds.WHOOSH_ROD)
                        .particle(EpicFightParticles.HIT_BLADE)
                        .collider(WOMWeaponColliders.STAFF, biped.get().toolR)
                        .movementMultiplier(2.35F)
                        .build());
        TENRAI_SKILL_DASH = WohAnimationBuilder.createAttackAnimation(builder,
                AnimationConfig.of("biped/combat/tenrai/skill_dash")
                        .convert(0.1F)
                        .speed(0.075F)
                        .phases(0.0F, 0.48F, 0.52F, 1.20F, 0.54F)
                        .phases(0.56F, 0.58F, 0.60F, 1.20F, 0.62F)
                        .phases(0.65F, 0.70F, 0.77F, 1.20F, 0.78F)
                        .phases(0.80F, 0.85F, 0.90F, 1.20F, 2.45F)
                        .hit(EpicFightSounds.BLADE_HIT)
                        .particle(EpicFightParticles.HIT_BLADE)
                        .collider(ColliderPreset.SWORD, biped.get().toolL, 0)
                        .collider(ColliderPreset.LONGSWORD, biped.get().toolR, 1)
                        .collider(ColliderPreset.SWORD, biped.get().toolL, 2)
                        .collider(ColliderPreset.LONGSWORD, biped.get().toolR, 3)
                        .movementMultiplier(1.75F)
                        .build());

        TENRAI_AIRSLASH = WohAnimationBuilder.createAttackAnimation(builder,
                AnimationConfig.of("biped/combat/old/tenrai/tenrai_airslash")
                        .attackType(WOHAnimationUtils.AttackAnimationType.BASIC_ATTACK_JUMP) // see note below
                        .phases(0.0F, 0.30F, 0.40F, 1.55F, 0.34F)
                        .phases(0.40F, 0.60F, 0.67F, 1.55F, 0.75F)
                        .hit(EpicFightSounds.BLADE_HIT)
                        .swing(EpicFightSounds.WHOOSH_ROD)
                        .particle(EpicFightParticles.HIT_BLADE)
                        .collider(WOMWeaponColliders.STAFF, biped.get().toolR)
                        .airTime(0.05F, 1.15F)
                        .build());

        TENRAI_SKILL_COMBO_1 = WohAnimationBuilder.createAttackAnimation(builder,
                AnimationConfig.of("biped/combat/tenrai/skill_combo_1")
                        .phases(0.0F, 0.28F, 0.34F, 2.10F, 0.36F)
                        .phases(0.75F, 0.80F, 0.93F, 2.10F, 2.50F)
                        .hit(EpicFightSounds.BLADE_HIT)
                        .swing(EpicFightSounds.WHOOSH)
                        .particle(EpicFightParticles.HIT_BLADE)
                        .collider(ColliderPreset.LONGSWORD, biped.get().toolR, 0)
                        .collider(ColliderPreset.SWORD, biped.get().toolL, 1)
                        .startEvent(WOHAnimationUtils.ReusableEvents.SPLIT_E0)
                        .endEvent(WOHAnimationUtils.ReusableEvents.UNSPLIT_E0)
                        .eventStartEnd(0.1F, 1.9F)
                        .build());

        TENRAI_SKILL_COMBO_2 = WohAnimationBuilder.createAttackAnimation(builder,
                AnimationConfig.of("biped/combat/tenrai/skill_combo_2")
                        .phases(0.0F, 0.35F, 0.45F, 1.12F, 0.46F)
                        .phases(0.47F, 0.48F, 0.49F, 1.12F, 2.10F)
                        .hit(EpicFightSounds.BLADE_HIT)
                        .swing(EpicFightSounds.WHOOSH)
                        .particle(EpicFightParticles.HIT_BLADE)
                        .collider(ColliderPreset.SWORD, biped.get().toolL, 0)
                        .collider(ColliderPreset.LONGSWORD, biped.get().toolR, 1)
                        .startEvent(WOHAnimationUtils.ReusableEvents.SPLIT_E0)
                        .endEvent(WOHAnimationUtils.ReusableEvents.UNSPLIT_E0)
                        .eventStartEnd(0.12F, 1.75F)
                        .build());

        TENRAI_SKILL_COMBO_3 = WohAnimationBuilder.createAttackAnimation(builder,
                AnimationConfig.of("biped/combat/tenrai/skill_combo_3")
                        .phases(0.0F, 0.22F, 0.30F, 1.30F, 0.48F)
                        .phases(0.50F, 0.60F, 0.77F, 1.30F, 2.52F)
                        .phases(0.50F, 0.60F, 0.77F, 1.30F, 2.52F)
                        .hit(EpicFightSounds.BLADE_HIT)
                        .swing(EpicFightSounds.WHOOSH)
                        .particle(EpicFightParticles.HIT_BLADE)
                        .collider(ColliderPreset.LONGSWORD, biped.get().toolR, 0)
                        .collider(ColliderPreset.SWORD, biped.get().toolL, 1)
                        .collider(ColliderPreset.LONGSWORD, biped.get().toolR, 2)
                        .startEvent(WOHAnimationUtils.ReusableEvents.SPLIT_E0)
                        .endEvent(WOHAnimationUtils.ReusableEvents.UNSPLIT_E0)
                        .eventStartEnd(0.12F, 1.9F)
                        .build());

        TENRAI_SKILL_COMBO_4 = WohAnimationBuilder.createAttackAnimation(builder,
                AnimationConfig.of("biped/combat/tenrai/skill_combo_4")
                        .phases(0.0F, 0.22F, 0.47F, 1.30F, 0.48F)
                        .phases(0.50F, 0.60F, 0.72F, 1.30F, 0.74F)
                        .phases(0.75F, 0.76F, 0.83F, 1.30F, 0.84F)
                        .phases(0.85F, 0.88F, 0.98F, 1.30F, 2.68F)
                        .hit(EpicFightSounds.BLADE_HIT)
                        .swing(EpicFightSounds.WHOOSH)
                        .particle(EpicFightParticles.HIT_BLADE)
                        .collider(ColliderPreset.SWORD, biped.get().toolL, 0)
                        .collider(ColliderPreset.SWORD, biped.get().toolL, 1)
                        .collider(ColliderPreset.LONGSWORD, biped.get().toolR, 2)
                        .collider(ColliderPreset.LONGSWORD, biped.get().toolR, 3)
                        .startEvent(WOHAnimationUtils.ReusableEvents.SPLIT_E0)
                        .endEvent(WOHAnimationUtils.ReusableEvents.UNSPLIT_E0)
                        .eventStartEnd(0.25F, 2.25F)
                        .build());
    }
}
