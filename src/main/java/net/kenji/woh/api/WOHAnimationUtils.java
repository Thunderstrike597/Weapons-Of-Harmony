package net.kenji.woh.api;

import net.kenji.woh.api.animation_types.ShotogatanaAttackAnimation;
import net.kenji.woh.api.animation_types.TessenThrowAttackAnimation;
import net.kenji.woh.api.manager.ShotogatanaManager;
import net.kenji.woh.gameasset.animation_types.*;
import net.kenji.woh.network.SheathStatePacket;
import net.kenji.woh.network.WohPacketHandler;
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
import yesman.epicfight.client.world.capabilites.entitypatch.player.LocalPlayerPatch;
import yesman.epicfight.gameasset.Armatures;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.particle.HitParticleType;
import yesman.epicfight.world.capabilities.entitypatch.EntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.item.ShieldCapability;
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
                    if(entityPatch instanceof PlayerPatch<?> playerPatch) {

                        Player player = playerPatch.getOriginal();
                        UUID playerId = player.getUUID();
                        ShotogatanaManager.sheathWeapon.put(playerId, false);
                        if (player.level().isClientSide) {
                            WohPacketHandler.sendToServer(new SheathStatePacket(player.getUUID(), false));
                            ShotogatanaManager.renderSheathMap.put(playerId, false);
                        }
                    }
        };

        public static final AnimationEvent.E0 SHEATH_E0 =
                (entityPatch, animation, params) -> {
                    if (entityPatch instanceof PlayerPatch<?> playerPatch) {
                        Player player = playerPatch.getOriginal();
                        UUID playerId = player.getUUID();

                        if (player.level().isClientSide) {
                            player.playSound(
                                    EpicFightSounds.SWORD_IN.get(),
                                    1.0f,
                                    1.0f
                            );

                        }
                        ShotogatanaManager.sheathWeapon.remove(playerId);
                        ShotogatanaManager.sheathWeapon.put(playerId, true);
                        if (player.level().isClientSide) {
                            WohPacketHandler.sendToServer(new SheathStatePacket(player.getUUID(), true));
                            ShotogatanaManager.renderSheathMap.put(playerId, true);
                        }
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
                        AnimationEvent.InTimeEvent.create(absStart, ReusableEvents.UNSHEATH_E0, AnimationEvent.Side.BOTH),
                        AnimationEvent.InTimeEvent.create(absEnd, ReusableEvents.SHEATH_E0, AnimationEvent.Side.BOTH)
                });
            } else if (!stopEndEvent) {
                anim.addEvents(new AnimationEvent[]{
                        AnimationEvent.InTimeEvent.create(absEnd, ReusableEvents.SHEATH_E0, AnimationEvent.Side.BOTH)
                });
            } else if (!stopStartEvent) {
                anim.addEvents(new AnimationEvent[]{
                        AnimationEvent.InTimeEvent.create(absStart, ReusableEvents.UNSHEATH_E0, AnimationEvent.Side.BOTH)
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
                            AnimationEvent.Side.BOTH
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

    public static AnimationManager.AnimationAccessor<AttackAnimation> createTessenThrowAttackAnimation(
            AnimationManager.AnimationBuilder builder,
            AttackAnimationType attackType,
            String path,
            int phaseCount,
            float convertTime,
            float start,
            float antic,
            float contact,
            float recovery,
            float end,
            float attackSpeed,
            Supplier<SoundEvent> swingSound,
            Supplier<SoundEvent> hitSound,
            RegistryObject<HitParticleType> hitParticle,
            Collider colliders,
            TessenThrowAttackAnimation.ThrowType throwType,
            StunType stunType,
            float throwStart,
            float ThrowEnd,
            boolean useMovement
    ) {
        AnimationManager.AnimationAccessor<AttackAnimation> animation = null;

        animation = builder.nextAccessor(path, accessor -> new TessenThrowAttackAnimation(convertTime, path,
               accessor, attackSpeed, throwStart, ThrowEnd, phaseCount,
                start, antic, contact, recovery, end,
                swingSound, hitSound, hitParticle, stunType, colliders, throwType, attackType == AttackAnimationType.BASIC_ATTACK_JUMP || attackType == AttackAnimationType.DASH_ATTACK_JUMP, useMovement
        ));
        return animation;
    }

    public static AnimationManager.AnimationAccessor<AttackAnimation> createTessenThrowAirAttackAnimation(
            AnimationManager.AnimationBuilder builder,
            String path,
            int phaseCount,
            float convertTime,
            float start,
            float antic,
            float contact,
            float recovery,
            float end,
            float attackSpeed,
            Supplier<SoundEvent> swingSound,
            Supplier<SoundEvent> hitSound,
            RegistryObject<HitParticleType> hitParticle,
            Collider colliders,
            TessenThrowAttackAnimation.ThrowType throwType,
            StunType stunType,
            float throwStart,
            float ThrowEnd,
            float[] airTime
    ) {
        AnimationManager.AnimationAccessor<AttackAnimation> animation = null;

        animation = builder.nextAccessor(path, accessor -> new TessenThrowAttackAnimation(convertTime, path,
                accessor, attackSpeed, throwStart, ThrowEnd, phaseCount,
                start, antic, contact, recovery, end,
                swingSound, hitSound, hitParticle, stunType, colliders, throwType, true, airTime
        ));
        return animation;
    }

    public static AnimationManager.AnimationAccessor<? extends BasicAttackAnimation> createShotogatanaAttackAnimation(
            AnimationManager.AnimationBuilder builder,
            AttackAnimationType type,
            String path,
            int phaseCount,
            float convertTime,
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
            float unsheatheTime,
            float sheathTime
    ) {
        AnimationManager.AnimationAccessor<BasicAttackAnimation> animation = null;
        switch(type) {
            case BASIC_ATTACK, BASIC_ATTACK_SHEATH, DASH_ATTACK:
                animation = builder.nextAccessor(path, accessor -> new ShotogatanaAttackAnimation(convertTime,
                        accessor, unsheatheTime, sheathTime, phaseCount,
                        start, antic, contact, recovery, end,
                        swingSound, hitSound, hitParticle, stunType, colliders, colliderJoints, false
                ));
                break;
            case BASIC_ATTACK_JUMP:
                animation = builder.nextAccessor(path, accessor -> new ShotogatanaAttackAnimation(convertTime,
                        accessor, unsheatheTime, sheathTime, phaseCount,
                        start, antic, contact, recovery, end,
                        swingSound, hitSound, hitParticle, stunType, colliders, colliderJoints, true
                ));
                break;
        }
        AnimationManager.AnimationAccessor<? extends BasicAttackAnimation> finalAnimation = animation;

        Supplier<StaticAnimation> setupSupplier = () -> {
            AttackAnimation anim = finalAnimation.get();

            anim.addEvents(new AnimationEvent[]{
                    AnimationEvent.InTimeEvent.create(unsheatheTime, ReusableEvents.UNSHEATH_E0, AnimationEvent.Side.BOTH),
                    AnimationEvent.InTimeEvent.create(sheathTime, ReusableEvents.SHEATH_E0, AnimationEvent.Side.BOTH)
            });

            return anim;
        };
        DEFERRED_SETUP.add(setupSupplier);
        return animation;
    }
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> createShotogatanaAirAttackAnimation(
            AnimationManager.AnimationBuilder builder,
            String path,
            int phaseCount,
            float convertTime,
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
            float[] airTime,
            boolean ignoreFallDamage,
            float unsheatheTime,
            float sheathTime
    ) {
        AnimationManager.AnimationAccessor<BasicAttackAnimation> animation = null;

        animation = builder.nextAccessor(path, accessor -> new ShotogatanaAttackAnimation(convertTime,
                accessor, unsheatheTime, sheathTime, phaseCount,
                start, antic, contact, recovery, end,
                swingSound, hitSound, hitParticle, stunType, colliders, colliderJoints, ignoreFallDamage, airTime
        ));

        AnimationManager.AnimationAccessor<? extends AttackAnimation> finalAnimation = animation;

        Supplier<StaticAnimation> setupSupplier = () -> {
            AttackAnimation anim = finalAnimation.get();

            anim.addEvents(new AnimationEvent[]{
                    AnimationEvent.InTimeEvent.create(unsheatheTime, ReusableEvents.UNSHEATH_E0, AnimationEvent.Side.BOTH),
                    AnimationEvent.InTimeEvent.create(sheathTime, ReusableEvents.SHEATH_E0, AnimationEvent.Side.BOTH)
            });

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
                        AnimationEvent.InTimeEvent.create(absoluteStart, ReusableEvents.UNSHEATH_E0, AnimationEvent.Side.BOTH),
                        AnimationEvent.InTimeEvent.create(absoluteEnd, ReusableEvents.SHEATH_E0, AnimationEvent.Side.BOTH)
                });
            } else if (!stopEndEvent) {
                anim.addEvents(new AnimationEvent[]{
                        AnimationEvent.InTimeEvent.create(absoluteEnd, ReusableEvents.SHEATH_E0, AnimationEvent.Side.BOTH)
                });
            } else if (!stopStartEvent) {
                anim.addEvents(new AnimationEvent[]{
                        AnimationEvent.InTimeEvent.create(absoluteStart, ReusableEvents.UNSHEATH_E0, AnimationEvent.Side.BOTH)
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
            float movementEnd,
            Supplier<SoundEvent>[] swingSound,
            Supplier<SoundEvent>[] hitSound,
            RegistryObject<HitParticleType>[] hitParticle,
            Collider[] colliders,
            Joint[] colliderJoints,
            StunType stunType,
            float normalizedStart,
            float normalizedEnd,
            float slashAngle
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
                        false,
                        slashAngle,
                        movementEnd
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
                        true,
                        slashAngle,
                        movementEnd
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
                        AnimationEvent.InTimeEvent.create(absoluteStart, ReusableEvents.UNSHEATH_E0, AnimationEvent.Side.BOTH),
                        AnimationEvent.InTimeEvent.create(absoluteEnd, ReusableEvents.SHEATH_E0, AnimationEvent.Side.BOTH)
                });
            } else if (!stopEndEvent) {
                anim.addEvents(new AnimationEvent[]{
                        AnimationEvent.InTimeEvent.create(absoluteEnd, ReusableEvents.SHEATH_E0, AnimationEvent.Side.BOTH)
                });
            } else if (!stopStartEvent) {
                anim.addEvents(new AnimationEvent[]{
                        AnimationEvent.InTimeEvent.create(absoluteStart, ReusableEvents.UNSHEATH_E0, AnimationEvent.Side.BOTH)
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
                            AnimationEvent.InTimeEvent.create(absoluteStart, ReusableEvents.UNSHEATH_E0, AnimationEvent.Side.BOTH),
                            AnimationEvent.InTimeEvent.create(absoluteEnd, ReusableEvents.SHEATH_E0, AnimationEvent.Side.BOTH)
                    });
                } if (!stopEndEvent) {
                    anim.addEvents(new AnimationEvent[]{
                            AnimationEvent.InTimeEvent.create(absoluteEnd, ReusableEvents.SHEATH_E0, AnimationEvent.Side.BOTH)
                    });
                } if (!stopStartEvent) {
                    anim.addEvents(new AnimationEvent[]{
                            AnimationEvent.InTimeEvent.create(absoluteStart, ReusableEvents.UNSHEATH_E0, AnimationEvent.Side.BOTH)
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
                        AnimationEvent.InTimeEvent.create(absoluteStart, ReusableEvents.UNSHEATH_E0, AnimationEvent.Side.BOTH),
                        AnimationEvent.InTimeEvent.create(absoluteEnd, ReusableEvents.SHEATH_E0, AnimationEvent.Side.BOTH)
                });
            } else if (!stopEndEvent) {
                anim.addEvents(new AnimationEvent[]{
                        AnimationEvent.InTimeEvent.create(absoluteEnd, ReusableEvents.SHEATH_E0, AnimationEvent.Side.BOTH)
                });
            } else if (!stopStartEvent) {
                anim.addEvents(new AnimationEvent[]{
                        AnimationEvent.InTimeEvent.create(absoluteStart, ReusableEvents.UNSHEATH_E0, AnimationEvent.Side.BOTH)
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
                        AnimationEvent.InTimeEvent.create(absoluteStart, ReusableEvents.UNSHEATH_E0, AnimationEvent.Side.BOTH),
                        AnimationEvent.InTimeEvent.create(absoluteEnd, ReusableEvents.SHEATH_E0, AnimationEvent.Side.BOTH)
                });
            } else if (!stopEndEvent) {
                anim.addEvents(new AnimationEvent[]{
                        AnimationEvent.InTimeEvent.create(absoluteEnd, ReusableEvents.SHEATH_E0, AnimationEvent.Side.BOTH)
                });
            } else if (!stopStartEvent) {
                anim.addEvents(new AnimationEvent[]{
                        AnimationEvent.InTimeEvent.create(absoluteStart, ReusableEvents.UNSHEATH_E0, AnimationEvent.Side.BOTH)
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
