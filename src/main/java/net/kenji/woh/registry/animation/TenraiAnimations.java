package net.kenji.woh.registry.animation;
import net.kenji.woh.api.WOHAnimationUtils;
import net.kenji.woh.gameasset.AttackHand;
import net.kenji.woh.registry.WohSounds;
import net.minecraftforge.registries.RegistryObject;
import reascer.wom.gameasset.colliders.WOMWeaponColliders;
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


public class TenraiAnimations {
    public static AnimationManager.AnimationAccessor<StaticAnimation> TENRAI_HOLD;
    public static AnimationManager.AnimationAccessor<StaticAnimation> TENRAI_SKILL_HOLD;

    public static AnimationManager.AnimationAccessor<StaticAnimation> TENRAI_SKILL_ACTIVATE;
    public static AnimationManager.AnimationAccessor<StaticAnimation> TENRAI_SKILL_DEACTIVATE;

    public static AnimationManager.AnimationAccessor<AttackAnimation> TENRAI_AUTO_1;
    public static AnimationManager.AnimationAccessor<AttackAnimation> TENRAI_AUTO_2;
    public static AnimationManager.AnimationAccessor<AttackAnimation> TENRAI_AUTO_3;
    public static AnimationManager.AnimationAccessor<AttackAnimation> TENRAI_AUTO_4;
    public static AnimationManager.AnimationAccessor<AttackAnimation> TENRAI_AUTO_5;
    public static AnimationManager.AnimationAccessor<AttackAnimation> TENRAI_AUTO_6;
    public static AnimationManager.AnimationAccessor<AttackAnimation> TENRAI_AIRSLASH;

    public static AnimationManager.AnimationAccessor<AttackAnimation> TENRAI_SKILL_AUTO_1;
    public static AnimationManager.AnimationAccessor<AttackAnimation> TENRAI_SKILL_AUTO_2;
    public static AnimationManager.AnimationAccessor<AttackAnimation> TENRAI_SKILL_AUTO_3;
    public static AnimationManager.AnimationAccessor<AttackAnimation> TENRAI_SKILL_AUTO_4;


    public static void build(AnimationManager.AnimationBuilder builder){
        AssetAccessor<? extends HumanoidArmature> biped = Armatures.BIPED;

        TENRAI_HOLD = builder.nextAccessor("biped/living/tenrai/tenrai_hold", accessor -> new StaticAnimation(true, accessor, biped));
        TENRAI_SKILL_HOLD = builder.nextAccessor("biped/living/tenrai/tenrai_skill_hold", accessor -> new StaticAnimation(true, accessor, biped));

        TENRAI_SKILL_ACTIVATE = builder.nextAccessor("biped/skill/tenrai/tenrai_skill_activate", accessor -> new StaticAnimation(false, accessor, biped));
        TENRAI_SKILL_DEACTIVATE = builder.nextAccessor("biped/skill/tenrai/tenrai_skill_deactivate", accessor -> new StaticAnimation(false, accessor, biped));

        TENRAI_AUTO_1 = WOHAnimationUtils.createAttackAnimation(builder,
                WOHAnimationUtils.AttackAnimationType.BASIC_ATTACK,
                "biped/combat/tenrai/tenrai_auto_1",
                2,
                0.1F,
                0.1F,
                3F,
                0.45F,
                new float[]{0.0F, 0.68F},
                new float[]{0.45F, 0.72F},
                new float[]{0.59F, 0.79F},
                new float[]{1.0F, 1.0F},
                new float[]{0.60f, 1.32F},
                new Supplier[]{EpicFightSounds.WHOOSH_ROD, EpicFightSounds.WHOOSH_ROD},
                new Supplier[]{EpicFightSounds.BLADE_HIT, EpicFightSounds.BLADE_HIT},
                new RegistryObject[]{EpicFightParticles.HIT_BLADE, EpicFightParticles.HIT_BLADE},
                new Collider[]{WOMWeaponColliders.STAFF, WOMWeaponColliders.STAFF},
                new AttackHand[]{AttackHand.RIGHT_HAND, AttackHand.RIGHT_HAND},
                StunType.SHORT,
                -1F,
                -1F
        );

        TENRAI_AUTO_2 = WOHAnimationUtils.createAttackAnimation(builder,
                WOHAnimationUtils.AttackAnimationType.BASIC_ATTACK,
                "biped/combat/tenrai/tenrai_auto_2",
                3,
                0.1F,
                0.05F,
                3F,
                0.45F,
                new float[]{0.0F, 0.28F, 0.50F},
                new float[]{0.10F, 0.30F, 0.55F},
                new float[]{0.25F, 0.36F, 0.62F},
                new float[]{1.0F, 1.0F, 1.0F},
                new float[]{0.26f, 0.50F, 0.76F},
                new Supplier[]{EpicFightSounds.WHOOSH_ROD, EpicFightSounds.WHOOSH_ROD, EpicFightSounds.WHOOSH_ROD},
                new Supplier[]{EpicFightSounds.BLADE_HIT, EpicFightSounds.BLADE_HIT, EpicFightSounds.BLADE_HIT},
                new RegistryObject[]{EpicFightParticles.HIT_BLADE, EpicFightParticles.HIT_BLADE, EpicFightParticles.HIT_BLADE,},
                new Collider[]{WOMWeaponColliders.STAFF, WOMWeaponColliders.STAFF, WOMWeaponColliders.STAFF},
                new AttackHand[]{AttackHand.RIGHT_HAND, AttackHand.RIGHT_HAND, AttackHand.RIGHT_HAND},
                StunType.SHORT,
                -1F,
                -1F

        );
        TENRAI_AUTO_3 = WOHAnimationUtils.createAttackAnimation(builder,
                WOHAnimationUtils.AttackAnimationType.BASIC_ATTACK,
                "biped/combat/tenrai/tenrai_auto_3",
                4,
                0.1F,
                0.1F,
                3F,
                0.45F,
                new float[]{0.0F, 0.48F, 0.68F, 0.82F},
                new float[]{0.16F, 0.50F, 0.72F, 0.84F},
                new float[]{0.43F, 0.52F, 0.73F, 0.88F},
                new float[]{1.0F, 1.0F, 1.0F, 1.25F},
                new float[]{0.45f, 0.65F, 0.80F, 1.60F},
                new Supplier[]{EpicFightSounds.WHOOSH_ROD, EpicFightSounds.WHOOSH_ROD, EpicFightSounds.WHOOSH_ROD, EpicFightSounds.WHOOSH_ROD},
                new Supplier[]{EpicFightSounds.BLADE_HIT, EpicFightSounds.BLADE_HIT, EpicFightSounds.BLADE_HIT, EpicFightSounds.BLADE_HIT},
                new RegistryObject[]{EpicFightParticles.HIT_BLADE, EpicFightParticles.HIT_BLADE, EpicFightParticles.HIT_BLADE, EpicFightParticles.HIT_BLADE},
                new Collider[]{WOMWeaponColliders.STAFF, WOMWeaponColliders.STAFF, WOMWeaponColliders.STAFF, WOMWeaponColliders.STAFF},
                new AttackHand[]{AttackHand.RIGHT_HAND, AttackHand.RIGHT_HAND, AttackHand.RIGHT_HAND, AttackHand.RIGHT_HAND},
                StunType.SHORT,
                -1F,
                -1F

        );
        TENRAI_AUTO_4 = WOHAnimationUtils.createAttackAnimation(builder,
                WOHAnimationUtils.AttackAnimationType.BASIC_ATTACK,
                "biped/combat/tenrai/tenrai_auto_4",
                3,
                0.1F,
                0.1F,
                3F,
                0.45F,
                new float[]{0.0F, 0.55F, 0.62F},
                new float[]{0.15F, 0.58F, 0.64F},
                new float[]{0.25F, 0.60F, 0.65F},
                new float[]{1.10F, 1.10F, 1.10F},
                new float[]{0.50f, 0.61F, 0.8F},
                new Supplier[]{EpicFightSounds.WHOOSH_ROD, EpicFightSounds.WHOOSH_ROD, EpicFightSounds.WHOOSH_ROD},
                new Supplier[]{EpicFightSounds.BLADE_HIT, EpicFightSounds.BLADE_HIT, EpicFightSounds.BLADE_HIT},
                new RegistryObject[]{EpicFightParticles.HIT_BLADE, EpicFightParticles.HIT_BLADE, EpicFightParticles.HIT_BLADE},
                new Collider[]{WOMWeaponColliders.STAFF, WOMWeaponColliders.STAFF, WOMWeaponColliders.STAFF},
                new AttackHand[]{AttackHand.RIGHT_HAND, AttackHand.RIGHT_HAND, AttackHand.RIGHT_HAND},
                StunType.SHORT,
                -1F,
                -1F

        );
        TENRAI_AUTO_5 = WOHAnimationUtils.createAttackAnimation(builder,
                WOHAnimationUtils.AttackAnimationType.BASIC_ATTACK,
                "biped/combat/tenrai/tenrai_auto_5",
                1,
                0.1F,
                0.1F,
                3F,
                0.45F,
                new float[]{0.0F},
                new float[]{0.65F},
                new float[]{0.77F},
                new float[]{1.40F},
                new float[]{2.38f},
                new Supplier[]{EpicFightSounds.WHOOSH_ROD},
                new Supplier[]{EpicFightSounds.BLADE_HIT},
                new RegistryObject[]{EpicFightParticles.HIT_BLADE},
                new Collider[]{WOMWeaponColliders.STAFF},
                new AttackHand[]{AttackHand.RIGHT_HAND},
                StunType.SHORT,
                -1F,
                -1F
        );
        TENRAI_AUTO_6 = WOHAnimationUtils.createAttackAnimation(builder,
                WOHAnimationUtils.AttackAnimationType.BASIC_ATTACK,
                "biped/combat/tenrai/tenrai_auto_6",
                2,
                0.1F,
                0.1F,
                3F,
                0.45F,
                new float[]{0.0F, 0.84F},
                new float[]{0.70F, 0.88F},
                new float[]{0.80F, 0.95F},
                new float[]{1.55F, 1.55F},
                new float[]{0.82F, 2.80F},
                new Supplier[]{EpicFightSounds.WHOOSH_ROD, EpicFightSounds.WHOOSH_ROD},
                new Supplier[]{EpicFightSounds.BLADE_HIT, EpicFightSounds.BLADE_HIT},
                new RegistryObject[]{EpicFightParticles.HIT_BLADE, EpicFightParticles.HIT_BLADE},
                new Collider[]{WOMWeaponColliders.STAFF, WOMWeaponColliders.STAFF},
                new AttackHand[]{AttackHand.RIGHT_HAND, AttackHand.RIGHT_HAND},
                StunType.SHORT,
                -1F,
                -1F
        );
        TENRAI_AIRSLASH = WOHAnimationUtils.createAirAttackAnimation(builder,
                "biped/combat/tenrai/tenrai_airslash",
                2,
                0.1F,
                0.1F,
                3F,
                0.45F,
                new float[]{0.0F, 0.40F},
                new float[]{0.30F, 0.60F},
                new float[]{0.40F, 0.67F},
                new float[]{1.55F, 1.55F},
                new float[]{0.34F, 0.75F},
                new Supplier[]{EpicFightSounds.WHOOSH_ROD, EpicFightSounds.WHOOSH_ROD},
                new Supplier[]{EpicFightSounds.BLADE_HIT, EpicFightSounds.BLADE_HIT},
                new RegistryObject[]{EpicFightParticles.HIT_BLADE, EpicFightParticles.HIT_BLADE},
                StunType.SHORT,
                new Collider[]{WOMWeaponColliders.STAFF, WOMWeaponColliders.STAFF},
                new Joint[]{biped.get().toolR, biped.get().toolR},
                new float[]{0.05F, 1.15F},
                -1F,
                -1F
        );
        TENRAI_SKILL_AUTO_1 = WOHAnimationUtils.createAttackAnimation(builder,
                WOHAnimationUtils.AttackAnimationType.BASIC_ATTACK,
                "biped/combat/tenrai/tenrai_skill_auto_1",
                2,
                0.1F,
                0.1F,
                3F,
                0.45F,
                new float[]{0.0F, 0.38F},
                new float[]{0.28F, 0.40F},
                new float[]{0.32F, 0.43F},
                new float[]{0.72F, 0.72F},
                new float[]{0.36f, 1.48F},
                new Supplier[]{EpicFightSounds.WHOOSH_ROD, EpicFightSounds.WHOOSH_ROD},
                new Supplier[]{EpicFightSounds.BLADE_HIT, EpicFightSounds.BLADE_HIT},
                new RegistryObject[]{EpicFightParticles.HIT_BLADE, EpicFightParticles.HIT_BLADE},
                new Collider[]{ColliderPreset.LONGSWORD, ColliderPreset.SWORD},
                new AttackHand[]{AttackHand.RIGHT_HAND, AttackHand.LEFT_HAND},
                StunType.SHORT,
                -1F,
                -1F
        );
        TENRAI_SKILL_AUTO_2 = WOHAnimationUtils.createAttackAnimation(builder,
                WOHAnimationUtils.AttackAnimationType.BASIC_ATTACK,
                "biped/combat/tenrai/tenrai_skill_auto_2",
                2,
                0.1F,
                0.1F,
                3F,
                0.45F,
                new float[]{0.0F, 0.70F},
                new float[]{0.28F, 0.75F},
                new float[]{0.52F, 0.82F},
                new float[]{1.08F, 1.08F},
                new float[]{0.60f, 2.10F},
                new Supplier[]{EpicFightSounds.WHOOSH_ROD, EpicFightSounds.WHOOSH_ROD},
                new Supplier[]{EpicFightSounds.BLADE_HIT, EpicFightSounds.BLADE_HIT},
                new RegistryObject[]{EpicFightParticles.HIT_BLADE, EpicFightParticles.HIT_BLADE},
                new Collider[]{ColliderPreset.SWORD, ColliderPreset.LONGSWORD},
                new AttackHand[]{AttackHand.LEFT_HAND, AttackHand.RIGHT_HAND},
                StunType.SHORT,
                -1F,
                -1F
        );
        TENRAI_SKILL_AUTO_3 = WOHAnimationUtils.createAttackAnimation(builder,
                WOHAnimationUtils.AttackAnimationType.BASIC_ATTACK,
                "biped/combat/tenrai/tenrai_skill_auto_3",
                2,
                0.1F,
                0.1F,
                3F,
                0.45F,
                new float[]{0.0F, 0.65F},
                new float[]{0.50F, 0.68F},
                new float[]{0.60F, 0.73F},
                new float[]{1.35F, 1.35F},
                new float[]{0.62f, 2.33F},
                new Supplier[]{EpicFightSounds.WHOOSH_ROD, EpicFightSounds.WHOOSH_ROD},
                new Supplier[]{EpicFightSounds.BLADE_HIT, EpicFightSounds.BLADE_HIT},
                new RegistryObject[]{EpicFightParticles.HIT_BLADE, EpicFightParticles.HIT_BLADE},
                new Collider[]{ColliderPreset.LONGSWORD, ColliderPreset.SWORD},
                new AttackHand[]{AttackHand.RIGHT_HAND, AttackHand.LEFT_HAND},
                StunType.SHORT,
                -1F,
                -1F
        );
        TENRAI_SKILL_AUTO_4 = WOHAnimationUtils.createAttackAnimation(builder,
                WOHAnimationUtils.AttackAnimationType.BASIC_ATTACK,
                "biped/combat/tenrai/tenrai_skill_auto_4",
                4,
                0.1F,
                0.1F,
                3F,
                0.45F,
                new float[]{0.0F, 0.0F, 0.40F, 0.54F},
                new float[]{0.28F, 0.28F, 0.45F, 0.55F},
                new float[]{0.35F, 0.35F, 0.50F, 0.68F},
                new float[]{1.35F, 1.35F, 1.35F, 1.35F},
                new float[]{0.36F, 0.36F, 0.52F, 0.70F},
                new Supplier[]{EpicFightSounds.WHOOSH_ROD, EpicFightSounds.WHOOSH_ROD, EpicFightSounds.WHOOSH_ROD, EpicFightSounds.WHOOSH_ROD},
                new Supplier[]{EpicFightSounds.BLADE_HIT, EpicFightSounds.BLADE_HIT, EpicFightSounds.BLADE_HIT, EpicFightSounds.BLADE_HIT},
                new RegistryObject[]{EpicFightParticles.HIT_BLADE, EpicFightParticles.HIT_BLADE, EpicFightParticles.HIT_BLADE, EpicFightParticles.HIT_BLADE},
                new Collider[]{ColliderPreset.LONGSWORD, ColliderPreset.SWORD, ColliderPreset.LONGSWORD, ColliderPreset.SWORD},
                new AttackHand[]{AttackHand.RIGHT_HAND, AttackHand.LEFT_HAND, AttackHand.RIGHT_HAND, AttackHand.LEFT_HAND},
                StunType.SHORT,
                -1F,
                -1F
        );
    }
}
