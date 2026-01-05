package net.kenji.woh.api;

import net.kenji.woh.gameasset.animations.*;
import net.kenji.woh.render.ShotogatanaRender;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.registries.RegistryObject;
import org.jline.utils.Log;
import yesman.epicfight.api.animation.AnimationManager;
import yesman.epicfight.api.animation.Joint;
import yesman.epicfight.api.animation.LivingMotions;
import yesman.epicfight.api.animation.property.AnimationEvent;
import yesman.epicfight.api.animation.types.*;
import yesman.epicfight.api.collider.Collider;
import yesman.epicfight.gameasset.Armatures;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.particle.HitParticleType;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.damagesource.StunType;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Supplier;

public class WOHAnimationUtils {

    private static final List<Supplier<StaticAnimation>> DEFERRED_SETUP = new ArrayList<>();


    public enum AttackAnimationType{
        BASIC_ATTACK,
        BASIC_ATTACK_JUMP,
        BASIC_ATTACK_SHEATH,
        DASH_ATTACK,
        DASH_ATTACK_JUMP,
        AIR_ATTACK
    }


    public class ReusableEvents {

        public static final AnimationEvent.E0 UNSHEATH_E0 =
                (entityPatch, animation, params) -> {
                    if (entityPatch instanceof PlayerPatch<?> playerPatch) {
                        Player player = playerPatch.getOriginal();
                        ShotogatanaRender.sheathWeapon.put(player.getUUID(), false);
                    }
                };

        public static final AnimationEvent.E0 SHEATH_E0 =
                (entityPatch, animation, params) -> {
                    if (entityPatch instanceof PlayerPatch<?> playerPatch) {
                        Player player = playerPatch.getOriginal();
                        UUID id = player.getUUID();

                        if (player.level().isClientSide) {
                            player.playSound(
                                    EpicFightSounds.SWORD_IN.get(),
                                    1.0f,
                                    1.0f
                            );
                        }

                        ShotogatanaRender.sheathWeapon.put(id, true);
                    }
                };
    }


    public static AnimationManager.AnimationAccessor<StaticAnimation> createLivingAnimation(
            AnimationManager.AnimationBuilder builder,
            String path,
            boolean isRepeat,
            float convertTime,
            float absStart,
            float absEnd,
            AnimationEvent<?,?>[] extraEvents
    ) {
        AnimationManager.AnimationAccessor<StaticAnimation> animation =
                builder.nextAccessor(path, accessor -> new StaticAnimation(isRepeat, accessor, Armatures.BIPED));

        Supplier<StaticAnimation> setupSupplier = () -> {
            StaticAnimation anim = animation.get();

            boolean stopEndEvent = absEnd <= -1;
            boolean stopStartEvent = absStart <= -1;

            // Register timestamp
            TimeStampManager.register(anim, absStart, absEnd);

            // Add events at those exact timestamps
            if (!stopEndEvent && !stopStartEvent) {
                anim.addEvents(new AnimationEvent[]{
                        AnimationEvent.InTimeEvent.create(absStart, ReusableEvents.UNSHEATH_E0, AnimationEvent.Side.CLIENT),
                        AnimationEvent.InTimeEvent.create(absEnd, ReusableEvents.SHEATH_E0, AnimationEvent.Side.CLIENT)
                });
            } else if (!stopEndEvent) {
                anim.addEvents(new AnimationEvent[]{
                        AnimationEvent.InTimeEvent.create(absEnd, ReusableEvents.SHEATH_E0, AnimationEvent.Side.CLIENT)
                });
            } else if (!stopStartEvent) {
                anim.addEvents(new AnimationEvent[]{
                        AnimationEvent.InTimeEvent.create(absStart, ReusableEvents.UNSHEATH_E0, AnimationEvent.Side.CLIENT)
                });
            }

            // Add extra events if provided
            if (extraEvents != null && extraEvents.length > 0) {
                anim.addEvents(extraEvents);
            }

            return anim;
        };

        // Add to deferred list instead of calling immediately
        DEFERRED_SETUP.add(setupSupplier);

        // Return the animation reference (will be populated later)
        return animation;
    }
    public static StaticAnimation createGuardAnimation(

            String path,
            float convertTime,
            AnimationEvent<?,?>[] extraEvents
    ) {
        StaticAnimation animation = (new WohStaticAnimation(convertTime, false, path, Armatures.BIPED, LivingMotions.BLOCK));

        if(extraEvents != null){
            animation.addEvents(extraEvents);
        }
        return animation;
    }
    public static AnimationManager.AnimationAccessor<StaticAnimation> createSheathAnimation(
            AnimationManager.AnimationBuilder builder,
            String path,
            float convertTime,
            float absEnd,
            AnimationEvent<?,?>[] extraEvents
    ) {
        AnimationManager.AnimationAccessor<StaticAnimation> animation = builder.nextAccessor(path, accessor -> new WohSheathAnimation(convertTime, accessor, Armatures.BIPED, absEnd));

        AnimationManager.AnimationAccessor<? extends StaticAnimation> finalAnimation = animation;

        Supplier<StaticAnimation> setupSupplier = () -> {
            StaticAnimation anim = finalAnimation.get();

            anim.addEvents(new AnimationEvent[]{
                    AnimationEvent.InTimeEvent.create(
                            absEnd,
                            ReusableEvents.SHEATH_E0,
                            AnimationEvent.Side.CLIENT
                    )
            });

            if(extraEvents != null){
                anim.addEvents(extraEvents);
            }
            return anim;
        };

        // ADD TO DEFERRED LIST - DON'T CALL IT!
        DEFERRED_SETUP.add(setupSupplier);

        // Return the accessor reference
        return animation;
    }



    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> createAttackAnimation(
            AnimationManager.AnimationBuilder builder,
            AttackAnimationType type,
            String path,
            int phaseCount,
            float convertTime,
            float attackSpeed,
            float attackDamage,
            float impact,
            float[] start,
            float[] antic,
            float[] contact,
            float[] recovery,
            float[] end,
            Supplier<SoundEvent>[] swingSound,
            Supplier<SoundEvent>[] hitSound,
            RegistryObject<HitParticleType>[] hitParticle,
            Collider[] colliders,
            Joint[] colliderJoints,
            StunType stunType,
            float normalizedStart,
            float normalizedEnd
    ) {
        AnimationManager.AnimationAccessor<BasicAttackAnimation> animation;
        switch(type) {
            case BASIC_ATTACK, BASIC_ATTACK_SHEATH:
                animation = builder.nextAccessor(path, accessor -> new WohAttackAnimation(
                        convertTime,        // convertTime first
                        accessor,           // PASS THE ACCESSOR!
                        type,
                        null,               // endAnimation
                        phaseCount,
                        attackSpeed,
                        attackDamage,
                        impact,
                        start,
                        antic,
                        contact,
                        recovery,
                        end,
                        swingSound,
                        hitSound,
                        hitParticle,
                        stunType,
                        colliders,
                        colliderJoints,
                        false
                ));
                break;
            case BASIC_ATTACK_JUMP:
                animation = builder.nextAccessor(path, accessor -> new WohAttackAnimation(
                        convertTime,        // convertTime first
                        accessor,           // PASS THE ACCESSOR!
                        type,
                        null,               // endAnimation
                        phaseCount,
                        attackSpeed,
                        attackDamage,
                        impact,
                        start,
                        antic,
                        contact,
                        recovery,
                        end,
                        swingSound,
                        hitSound,
                        hitParticle,
                        stunType,
                        colliders,
                        colliderJoints,
                        true
                ));
                break;
            default:
                throw new IllegalArgumentException("Unknown animation type: " + type);
        }

        AnimationManager.AnimationAccessor<? extends AttackAnimation> finalAnimation = animation;

        Supplier<StaticAnimation> setupSupplier = () -> {
            AttackAnimation anim = finalAnimation.get();

            boolean stopEndEvent = normalizedEnd <= -1;
            boolean stopStartEvent = normalizedStart <= -1;

            TimeStampManager.register(anim, normalizedStart, normalizedEnd);

            float absoluteStart = anim.getTotalTime() * normalizedStart;
            float absoluteEnd = anim.getTotalTime() * normalizedEnd;

            if (!stopEndEvent && !stopStartEvent) {
                anim.addEvents(new AnimationEvent[]{
                        AnimationEvent.InTimeEvent.create(absoluteStart, ReusableEvents.UNSHEATH_E0, AnimationEvent.Side.CLIENT),
                        AnimationEvent.InTimeEvent.create(absoluteEnd, ReusableEvents.SHEATH_E0, AnimationEvent.Side.CLIENT)
                });
            } else if (!stopEndEvent) {
                anim.addEvents(new AnimationEvent[]{
                        AnimationEvent.InTimeEvent.create(absoluteEnd, ReusableEvents.SHEATH_E0, AnimationEvent.Side.CLIENT)
                });
            } else if (!stopStartEvent) {
                anim.addEvents(new AnimationEvent[]{
                        AnimationEvent.InTimeEvent.create(absoluteStart, ReusableEvents.UNSHEATH_E0, AnimationEvent.Side.CLIENT)
                });
            }

            return anim;
        };

        DEFERRED_SETUP.add(setupSupplier);
        return animation;
    }

        public static AnimationManager.AnimationAccessor<BasicAttackAnimation> createAttackAnimation(
            AnimationManager.AnimationBuilder builder,
            AttackAnimationType type,
            String path,
            int phaseCount,
            float convertTime,
            float attackSpeed,
            float attackDamage,
            float impact,
            float[] start,
            float[] antic,
            float[] contact,
            float[] recovery,
            float[] end,
            Supplier<SoundEvent>[] swingSound,
            Supplier<SoundEvent>[] hitSound,
            RegistryObject<HitParticleType>[] hitParticle,
            Collider[] colliders,
            Joint[] colliderJoints,
            StunType stunType,
            AnimationManager.AnimationAccessor<StaticAnimation> endAnimation,
            float normalizedStart,
            float normalizedEnd
        ) {
            AnimationManager.AnimationAccessor<BasicAttackAnimation> animation;
            switch(type) {
                case BASIC_ATTACK:
                case BASIC_ATTACK_SHEATH:
                    animation = builder.nextAccessor(path, accessor -> new WohAttackAnimation(
                            convertTime,        // convertTime first
                            accessor,           // PASS THE ACCESSOR!
                            type,
                            endAnimation,               // endAnimation
                            phaseCount,
                            attackSpeed,
                            attackDamage,
                            impact,
                            start,
                            antic,
                            contact,
                            recovery,
                            end,
                            swingSound,
                            hitSound,
                            hitParticle,
                            stunType,
                            colliders,
                            colliderJoints,
                            false
                    ));
                    break;
                case BASIC_ATTACK_JUMP:
                    animation = builder.nextAccessor(path, accessor -> new WohAttackAnimation(
                            convertTime,        // convertTime first
                            accessor,           // PASS THE ACCESSOR!
                            type,
                            endAnimation,               // endAnimation
                            phaseCount,
                            attackSpeed,
                            attackDamage,
                            impact,
                            start,
                            antic,
                            contact,
                            recovery,
                            end,
                            swingSound,
                            hitSound,
                            hitParticle,
                            stunType,
                            colliders,
                            colliderJoints,
                            true
                    ));
                    break;
                default:
                    throw new IllegalArgumentException("Unknown animation type: " + type);
            }

            AnimationManager.AnimationAccessor<? extends AttackAnimation> finalAnimation = animation;

            Supplier<StaticAnimation> setupSupplier = () -> {
                AttackAnimation anim = finalAnimation.get();

                boolean stopEndEvent = normalizedEnd <= -1;
                boolean stopStartEvent = normalizedStart <= -1;

                TimeStampManager.register(anim, normalizedStart, normalizedEnd);

                float absoluteStart = anim.getTotalTime() * normalizedStart;
                float absoluteEnd = anim.getTotalTime() * normalizedEnd;

                if (!stopEndEvent && !stopStartEvent) {
                    anim.addEvents(new AnimationEvent[]{
                            AnimationEvent.InTimeEvent.create(absoluteStart, ReusableEvents.UNSHEATH_E0, AnimationEvent.Side.CLIENT),
                            AnimationEvent.InTimeEvent.create(absoluteEnd, ReusableEvents.SHEATH_E0, AnimationEvent.Side.CLIENT)
                    });
                } if (!stopEndEvent) {
                    anim.addEvents(new AnimationEvent[]{
                            AnimationEvent.InTimeEvent.create(absoluteEnd, ReusableEvents.SHEATH_E0, AnimationEvent.Side.CLIENT)
                    });
                } if (!stopStartEvent) {
                    Log.info("IS ADDING EVENT");
                    anim.addEvents(new AnimationEvent[]{
                            AnimationEvent.InTimeEvent.create(absoluteStart, ReusableEvents.UNSHEATH_E0, AnimationEvent.Side.CLIENT)
                    });
                }

                return anim;
            };

            DEFERRED_SETUP.add(setupSupplier);
            return animation;
        }
    public static AnimationManager.AnimationAccessor<AirSlashAnimation> createAirAttackAnimation(
            AnimationManager.AnimationBuilder builder,
            String path,
            int phaseCount,
            float convertTime,
            float attackSpeed,
            float attackDamage,
            float impact,
            float[] start,
            float[] antic,
            float[] contact,
            float[] recovery,
            float[] end,
            Supplier<SoundEvent>[] hitSound,
            Supplier<SoundEvent>[] swingSound,
            RegistryObject<HitParticleType>[] hitParticle,
            StunType stunType,
            Collider[] colliders,
            Joint[] colliderJoints,
            float[] airTime,
            float normalizedStart,
            float normalizedEnd
    ) {
        AnimationManager.AnimationAccessor<AirSlashAnimation> animation = builder.nextAccessor(path, accessor ->
                new WohAirAttackAnimation(
                        phaseCount, convertTime, accessor,attackSpeed, attackDamage, impact,
                        start, antic, contact, recovery, end,
                        hitSound, swingSound, hitParticle, stunType, colliders, colliderJoints, airTime
                )
        );

        // Create a supplier that will execute the setup logic when called
        AnimationManager.AnimationAccessor<? extends AttackAnimation> finalAnimation = animation;

        Supplier<StaticAnimation> setupSupplier = () -> {
            AttackAnimation anim = finalAnimation.get();

            boolean stopEndEvent = normalizedEnd <= -1;
            boolean stopStartEvent = normalizedStart <= -1;

            // Register timestamp
            TimeStampManager.register(anim, normalizedStart, normalizedEnd);

            float absoluteStart = anim.getTotalTime() * normalizedStart;
            float absoluteEnd = anim.getTotalTime() * normalizedEnd;

            // Add events at those exact timestamps
            if (!stopEndEvent && !stopStartEvent) {
                anim.addEvents(new AnimationEvent[]{
                        AnimationEvent.InTimeEvent.create(absoluteStart, ReusableEvents.UNSHEATH_E0, AnimationEvent.Side.CLIENT),
                        AnimationEvent.InTimeEvent.create(absoluteEnd, ReusableEvents.SHEATH_E0, AnimationEvent.Side.CLIENT)
                });
            } else if (!stopEndEvent) {
                anim.addEvents(new AnimationEvent[]{
                        AnimationEvent.InTimeEvent.create(absoluteEnd, ReusableEvents.SHEATH_E0, AnimationEvent.Side.CLIENT)
                });
            } else if (!stopStartEvent) {
                anim.addEvents(new AnimationEvent[]{
                        AnimationEvent.InTimeEvent.create(absoluteStart, ReusableEvents.UNSHEATH_E0, AnimationEvent.Side.CLIENT)
                });
            }

            return anim;
        };

        // Store this supplier somewhere to be called later, OR call it immediately if you have a hook
        // For now, we'll call it wrapped in a try-catch or you can store it

        // If you need to call it later, store it in a list:
        // DEFERRED_SETUP_TASKS.add(setupSupplier);

        DEFERRED_SETUP.add(setupSupplier);
        return animation;
    }
    public static AnimationManager.AnimationAccessor<DashAttackAnimation> createDashAttackAnimation(
            AnimationManager.AnimationBuilder builder,
            AttackAnimationType type,
            String path,
            int phaseCount,
            float convertTime,
            float attackSpeed,
            float attackDamage,
            float impact,
            float[] start,
            float[] antic,
            float[] contact,
            float[] recovery,
            float[] end,
            Supplier<SoundEvent>[] swingSound,
            Supplier<SoundEvent>[] hitSound,
            RegistryObject<HitParticleType>[] hitParticle,
            Collider[] colliders,
            Joint[] colliderJoints,
            StunType stunType,
            AnimationManager.AnimationAccessor<StaticAnimation> endAnimation,
            float normalizedStart,
            float normalizedEnd
    ) {
        AnimationManager.AnimationAccessor<DashAttackAnimation> animation;
                animation = builder.nextAccessor(path, accessor -> new WohDashAttackAnimation(
                        convertTime,        // convertTime first
                        accessor,           // PASS THE ACCESSOR!
                        type,
                        endAnimation,               // endAnimation
                        phaseCount,
                        attackSpeed,
                        attackDamage,
                        impact,
                        start,
                        antic,
                        contact,
                        recovery,
                        end,
                        swingSound,
                        hitSound,
                        hitParticle,
                        stunType,
                        colliders,
                        colliderJoints,
                        true
                ));

        AnimationManager.AnimationAccessor<? extends AttackAnimation> finalAnimation = animation;

        Supplier<StaticAnimation> setupSupplier = () -> {
            AttackAnimation anim = finalAnimation.get();

            boolean stopEndEvent = normalizedEnd <= -1;
            boolean stopStartEvent = normalizedStart <= -1;

            TimeStampManager.register(anim, normalizedStart, normalizedEnd);

            float absoluteStart = anim.getTotalTime() * normalizedStart;
            float absoluteEnd = anim.getTotalTime() * normalizedEnd;

            if (!stopEndEvent && !stopStartEvent) {
                anim.addEvents(new AnimationEvent[]{
                        AnimationEvent.InTimeEvent.create(absoluteStart, ReusableEvents.UNSHEATH_E0, AnimationEvent.Side.CLIENT),
                        AnimationEvent.InTimeEvent.create(absoluteEnd, ReusableEvents.SHEATH_E0, AnimationEvent.Side.CLIENT)
                });
            } else if (!stopEndEvent) {
                anim.addEvents(new AnimationEvent[]{
                        AnimationEvent.InTimeEvent.create(absoluteEnd, ReusableEvents.SHEATH_E0, AnimationEvent.Side.CLIENT)
                });
            } else if (!stopStartEvent) {
                anim.addEvents(new AnimationEvent[]{
                        AnimationEvent.InTimeEvent.create(absoluteStart, ReusableEvents.UNSHEATH_E0, AnimationEvent.Side.CLIENT)
                });
            }

            return anim;
        };

        DEFERRED_SETUP.add(setupSupplier);
        return animation;
    }

    public static void initializeAnimations() {
        DEFERRED_SETUP.forEach(Supplier::get);
        DEFERRED_SETUP.clear();
    }
}
