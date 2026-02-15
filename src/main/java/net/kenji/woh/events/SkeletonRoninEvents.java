package net.kenji.woh.events;

import net.kenji.woh.WeaponsOfHarmony;
import net.kenji.woh.WohConfigCommon;
import net.kenji.woh.entities.WohEntities;
import net.kenji.woh.entities.custom.alt_entities.RoninSkeletonEntity;
import net.kenji.woh.registry.WohItems;
import net.kenji.woh.registry.animation.GenericAnimations;
import net.minecraft.server.TickTask;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import yesman.epicfight.api.animation.AnimationPlayer;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Mod.EventBusSubscriber(modid = WeaponsOfHarmony.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class SkeletonRoninEvents {

    private static Map<UUID, Float> defeatYRot = new HashMap<>();
    private static Map<UUID, Boolean> isTransition = new HashMap<>();

    private static final String SURRENDER_ATTEMPTED_TAG = "surrender_attempted";
    private static final String SURRENDER_SUCCESS_TAG = "surrender_succeeded";

    private static final float weaponRepairModuleChance = 0.52F;


    @SubscribeEvent
    public static void onEntityJoin(EntityJoinLevelEvent event) {
        if (!(event.getEntity() instanceof Skeleton skeleton)) return;
        if(event.loadedFromDisk()) return;

        if (event.getLevel().isClientSide()) return;
        if(!(event.getLevel() instanceof ServerLevel serverLevel)) return;
        if (skeleton.tickCount != 0) return;

        if (skeleton.getRandom().nextFloat() < WohConfigCommon.RONIN_SKELETON_SPAWN_CHANCE.get()) {
            event.setCanceled(true);
            WohEntities.RONIN_SKELETON.get().spawn(serverLevel, event.getEntity().blockPosition(), MobSpawnType.NATURAL);
        }
    }
    @SubscribeEvent
    public static void onLivingDrops(LivingDropsEvent event) {
        if (!(event.getEntity() instanceof RoninSkeletonEntity ronin)) return;
        if (ronin.level().isClientSide()) return;

        // Only our custom skeletons

        double dropChance = ronin.getPersistentData().getBoolean(SURRENDER_ATTEMPTED_TAG) ? 1 : 0.45F;

        // 25% chance to drop the special item
        if (ronin.getRandom().nextFloat() < dropChance) {
            double chance = Math.random();
            ItemStack drop = new ItemStack(WohItems.WEAPON_REPAIR_MODULE.get());

            ItemStack drop2 = new ItemStack(WohItems.BLADE_CURVE_MODULE.get());
            ItemStack drop3 = new ItemStack(WohItems.BROKEN_BLADE_AND_SHEATH.get());

            if (chance < 0.5F) {
                double chance2 = Math.random();
                if(chance2 < weaponRepairModuleChance) {
                    event.getDrops().add(
                            new ItemEntity(
                                    ronin.level(),
                                    ronin.getX(),
                                    ronin.getY(),
                                    ronin.getZ(),
                                    drop
                            )
                    );
                }else {
                    event.getDrops().add(
                            new ItemEntity(
                                    ronin.level(),
                                    ronin.getX(),
                                    ronin.getY(),
                                    ronin.getZ(),
                                    drop2
                            )
                    );
                }
            } else {
                event.getDrops().add(
                        new ItemEntity(
                                ronin.level(),
                                ronin.getX(),
                                ronin.getY(),
                                ronin.getZ(),
                                drop3
                        )
                );
            }
        }
        if (ronin.getRandom().nextFloat() < 0.8f) {
            int itemDropIndex = (int) Mth.randomBetween(RandomSource.create(), 0, 3);

            ItemStack drop1 = new ItemStack(WohItems.METAL_RONIN_HEADWEAR.get());
            ItemStack drop2 = new ItemStack(WohItems.RONIN_TUNIC.get());
            ItemStack drop3 = new ItemStack(WohItems.RONIN_LEGGINGS.get());
            ItemStack drop4 = new ItemStack(WohItems.RONIN_BOOTS.get());


            switch (itemDropIndex){
                case 0:
                    if(ronin.getRandom().nextFloat() < 0.58F) {
                        event.getDrops().add(
                                new ItemEntity(
                                        ronin.level(),
                                        ronin.getX(),
                                        ronin.getY(),
                                        ronin.getZ(),
                                        drop1
                                )
                        );
                    }
                break;
                case 1:
                    if(ronin.getRandom().nextFloat() < 0.4F) {
                        event.getDrops().add(
                                new ItemEntity(
                                        ronin.level(),
                                        ronin.getX(),
                                        ronin.getY(),
                                        ronin.getZ(),
                                        drop2
                                )
                        );
                    }
                    break;
                case 2:
                    if(ronin.getRandom().nextFloat() < 0.35F) {
                        event.getDrops().add(
                                new ItemEntity(
                                        ronin.level(),
                                        ronin.getX(),
                                        ronin.getY(),
                                        ronin.getZ(),
                                        drop3
                                )
                        );
                    }
                    break;
                case 3:
                    if(ronin.getRandom().nextFloat() < 0.28F) {
                        event.getDrops().add(
                                new ItemEntity(
                                        ronin.level(),
                                        ronin.getX(),
                                        ronin.getY(),
                                        ronin.getZ(),
                                        drop4
                                )
                        );
                    }
            }
        }
    }
    @SubscribeEvent
    public static void onLivingHurt(LivingDamageEvent event) {
        if (event.getEntity() instanceof RoninSkeletonEntity ronin) {

            if (event.getSource().getEntity() instanceof Player player) {

                float postDamageHealth = ronin.getHealth() - event.getAmount();
                UUID skeletonId = ronin.getUUID();

                boolean hasAttempted = ronin.getPersistentData().getBoolean(SURRENDER_ATTEMPTED_TAG);
                if (postDamageHealth < ronin.getMaxHealth() / 3 && !hasAttempted) {
                    ronin.getPersistentData().putBoolean(SURRENDER_ATTEMPTED_TAG, true);
                    double chance = Math.random();
                    if (chance < WohConfigCommon.RONIN_SKELETON_SURRENDER_CHANCE.get()) {
                        ronin.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY).ifPresent(cap -> {
                            if (cap instanceof LivingEntityPatch<?> livingPatch) {

                                livingPatch.playAnimationSynchronized(GenericAnimations.DEFEAT_KNEEL, 0.0F);
                                if (player.level().isClientSide) {
                                    player.playSound(EpicFightSounds.CLASH.get());
                                    player.playSound(SoundEvents.PLAYER_ATTACK_CRIT);
                                }
                                defeatYRot.putIfAbsent(skeletonId, ronin.getYRot());
                                isTransition.put(skeletonId, true);
                                scheduleAfterAnimation(livingPatch, 30);}
                        });
                    }
                }
            }
        }
    }

    private static void scheduleAfterAnimation(LivingEntityPatch patch, int delayTicks) {
        ServerLevel level = (ServerLevel) patch.getOriginal().level();
        level.getServer().execute(() -> {
            level.getServer().tell(new TickTask(
                    level.getServer().getTickCount() + delayTicks,
                    () -> {
                        if (patch.getOriginal().isAlive()) {
                            patch.getOriginal().getPersistentData().putBoolean(SURRENDER_SUCCESS_TAG, true);
                        }
                    }
            ));
        });
    }
    @SubscribeEvent
    public static void onLivingTick(LivingEvent.LivingTickEvent event) {
        if(event.getEntity() instanceof Skeleton skeleton) {
            if (!skeleton.getPersistentData().getBoolean(SURRENDER_SUCCESS_TAG))
                return;
            if(event.getEntity().level().isClientSide)
                return;
            if(!skeleton.isAlive()){
                skeleton.kill();
                return;
            }

            skeleton.setNoAi(true);
            skeleton.setYRot(defeatYRot.getOrDefault(skeleton.getUUID(), 0F));
            skeleton.setAggressive(false);
            skeleton.setTarget(null);
            skeleton.getNavigation().stop();
            skeleton.setDeltaMovement(0, skeleton.getDeltaMovement().y, 0);

            skeleton.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY).ifPresent(cap -> {
                if (cap instanceof LivingEntityPatch<?> livingPatch) {

                    AnimationPlayer serverAnimator = livingPatch.getServerAnimator().animationPlayer;
                    AnimationPlayer defeatAnimPlayer = livingPatch.getAnimator().getPlayerFor(GenericAnimations.DEFEAT_KNEEL);
                    if(isTransition.getOrDefault(skeleton.getUUID(), false)){
                      float maxTime = defeatAnimPlayer.getAnimation().getTotalTime();
                       if(defeatAnimPlayer.getElapsedTime() / maxTime > 0.95){
                            isTransition.remove(skeleton.getUUID());
                        }
                        return;
                    }

                    if(serverAnimator.getAnimation() != GenericAnimations.DEFEAT_KNEEL) {
                      if (serverAnimator.getAnimation() != GenericAnimations.DEFEAT_IDLE) {
                          livingPatch.playAnimationSynchronized(GenericAnimations.DEFEAT_IDLE, 0.08F);
                      }
                    }
                }
            });
        }
    }


}
