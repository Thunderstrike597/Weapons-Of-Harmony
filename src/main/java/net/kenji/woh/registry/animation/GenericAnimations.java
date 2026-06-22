package net.kenji.woh.registry.animation;
import net.kenji.woh.api.AnimationConfig;
import net.kenji.woh.api.WOHAnimationUtils;
import net.kenji.woh.api.WohAnimationBuilder;
import net.kenji.woh.gameasset.AttackHand;
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
    public static AnimationManager.AnimationAccessor<? extends StaticAnimation> ARBITERS_SHIELD_BLOCK;


    public static AnimationManager.AnimationAccessor<? extends AttackAnimation> KATAJUTSU_AUTO_1;
    public static AnimationManager.AnimationAccessor<? extends AttackAnimation> KATAJUTSU_AUTO_2;
    public static AnimationManager.AnimationAccessor<? extends AttackAnimation> KATAJUTSU_AUTO_3;

    public static AnimationManager.AnimationAccessor<? extends AttackAnimation> COMBAT_FIST_DASH;
    public static AnimationManager.AnimationAccessor<? extends AttackAnimation> COMBAT_FIST_AIRKICK;


    public static void build(AnimationManager.AnimationBuilder builder){
        AssetAccessor<? extends HumanoidArmature> biped = Armatures.BIPED;

        DEFEAT_IDLE = builder.nextAccessor("biped/living/generic/defeat_idle", accessor -> new StaticAnimation(0.1F, true,accessor, biped));
        DEFEAT_KNEEL = builder.nextAccessor("biped/living/generic/defeat_kneel", accessor -> new StaticAnimation(0.1F, true,accessor, biped));
        KATAJUTSU_IDLE = builder.nextAccessor("biped/living/katajutsu/katajutsu_idle", accessor -> new StaticAnimation(0.1F, true,accessor, biped));
        ARBITERS_SHIELD_BLOCK = builder.nextAccessor("biped/living/generic/arbiters_shield_block", accessor -> new StaticAnimation(0.1F, true, accessor, biped));

        KATAJUTSU_AUTO_1 = WohAnimationBuilder.createAttackAnimation(builder,
                AnimationConfig.of("biped/combat/katajutsu/katajutsu_auto_1")
                        .speed(0.15F)
                        .phases(0.0F, 0.15F, 0.23F, 0.4F, 0.62F)
                        .hit(EpicFightSounds.BLUNT_HIT)
                        .swing(EpicFightSounds.WHOOSH)
                        .particle(EpicFightParticles.HIT_BLUNT)
                        .collider(ColliderPreset.FIST, biped.get().toolR)
                        .build());

        KATAJUTSU_AUTO_2 = WohAnimationBuilder.createAttackAnimation(builder,
                AnimationConfig.of("biped/combat/katajutsu/katajutsu_auto_2")
                        .speed(0.15F)
                        .phases(0.0F, 0.1F, 0.18F, 0.38F, 0.5F)
                        .hit(EpicFightSounds.BLUNT_HIT)
                        .swing(EpicFightSounds.WHOOSH)
                        .particle(EpicFightParticles.HIT_BLUNT)
                        .collider(ColliderPreset.FIST, biped.get().toolL)
                        .build());

        KATAJUTSU_AUTO_3 = WohAnimationBuilder.createAttackAnimation(builder,
                AnimationConfig.of("biped/combat/katajutsu/katajutsu_auto_3")
                        .speed(0.15F)
                        .phases(0.0F, 0.25F, 0.38F, 0.60F, 1.05F)
                        .hit(EpicFightSounds.BLUNT_HIT)
                        .swing(EpicFightSounds.WHOOSH)
                        .particle(EpicFightParticles.HIT_BLUNT)
                        .collider(ColliderPreset.FIST, biped.get().legR)
                        .build());

        COMBAT_FIST_DASH = WohAnimationBuilder.createAttackAnimation(builder,
                AnimationConfig.of("biped/combat/generic/combat_fist_dash")
                        .attackType(WOHAnimationUtils.AttackAnimationType.DASH_ATTACK)
                        .convert(0.05F)
                        .speed(1F)
                        .phases(0.0F, 0.05F, 0.1F, 0.15F, 0.2F)
                        .phases(0.3F, 0.38F, 0.5F, 0.95F, 1F)
                        .stun(StunType.LONG)
                        .hit(EpicFightSounds.BLUNT_HIT)
                        .swing(EpicFightSounds.WHOOSH)
                        .particle(EpicFightParticles.HIT_BLUNT)
                        .collider(ColliderPreset.FIST, biped.get().toolL, 0)
                        .collider(ColliderPreset.FIST, biped.get().toolR, 1)
                        .build());

        COMBAT_FIST_AIRKICK = WohAnimationBuilder.createAttackAnimation(builder,
                AnimationConfig.of("biped/combat/generic/combat_fist_airkick")
                        .attackType(WOHAnimationUtils.AttackAnimationType.BASIC_ATTACK_JUMP)
                        .speed(1F)
                        .phases(0.0F, 0.1F, 0.18F, 0.25F, 0.8F)
                        .hit(EpicFightSounds.BLUNT_HIT)
                        .swing(EpicFightSounds.WHOOSH_ROD)
                        .particle(EpicFightParticles.HIT_BLUNT)
                        .collider(ColliderPreset.FIST, biped.get().toolR)
                        .airTime(0.0F, 0.45F)
                        .build());
    }
}
