package net.kenji.woh.events;

import net.kenji.woh.WeaponsOfHarmony;
import net.kenji.woh.WohConfigCommon;
import net.kenji.woh.registry.WOHItems;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = WeaponsOfHarmony.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class SpawnEvents {

    private static final String RONIN_TAG = "woh_ronin_skeleton";

    @SubscribeEvent
    public static void onEntityJoin(EntityJoinLevelEvent event) {
        if (!(event.getEntity() instanceof Skeleton skeleton)) return;
        if (event.getLevel().isClientSide()) return;
        if (skeleton.tickCount != 0) return;

        if (skeleton.getRandom().nextFloat() < WohConfigCommon.RONIN_SKELETON_SPAWN_CHANCE.get()) {
            skeleton.setItemSlot(
                    EquipmentSlot.MAINHAND,
                    new ItemStack(WOHItems.SHOTOGATANA.get())
            );
            skeleton.setItemSlot(
                    EquipmentSlot.HEAD,
                    new ItemStack(WOHItems.METAL_RONIN_HEADWEAR.get())
            );
            skeleton.setItemSlot(
                    EquipmentSlot.CHEST,
                    new ItemStack(WOHItems.RONIN_TUNIC.get())
            );
            skeleton.setItemSlot(
                    EquipmentSlot.LEGS,
                    new ItemStack(WOHItems.RONIN_LEGGINGS.get())
            );

            skeleton.setDropChance(EquipmentSlot.MAINHAND, 0.0f);
            skeleton.setDropChance(EquipmentSlot.HEAD, 0.35f);

            // ✅ Mark this skeleton
            skeleton.getPersistentData().putBoolean(RONIN_TAG, true);
        }
    }@SubscribeEvent
    public static void onLivingDrops(LivingDropsEvent event) {
        if (!(event.getEntity() instanceof Skeleton skeleton)) return;
        if (skeleton.level().isClientSide()) return;

        // Only our custom skeletons
        if (!skeleton.getPersistentData().getBoolean(RONIN_TAG)) return;

        // 25% chance to drop the special item
        if (skeleton.getRandom().nextFloat() < 0.36f) {
            double chance = Math.random();
            ItemStack drop = new ItemStack(WOHItems.BLADE_CURVE_MODULE.get());
            ItemStack drop2 = new ItemStack(WOHItems.BROKEN_BLADE_AND_SHEATH.get());

            if (chance < 0.5F) {
                event.getDrops().add(
                        new ItemEntity(
                                skeleton.level(),
                                skeleton.getX(),
                                skeleton.getY(),
                                skeleton.getZ(),
                                drop
                        )
                );
            } else {
                event.getDrops().add(
                        new ItemEntity(
                                skeleton.level(),
                                skeleton.getX(),
                                skeleton.getY(),
                                skeleton.getZ(),
                                drop2
                        )
                );
            }
        }
        if (skeleton.getRandom().nextFloat() < 0.8f) {
            int itemDropIndex = (int) Mth.randomBetween(RandomSource.create(), 0, 2);

            ItemStack drop1 = new ItemStack(WOHItems.METAL_RONIN_HEADWEAR.get());
            ItemStack drop2 = new ItemStack(WOHItems.RONIN_TUNIC.get());
            ItemStack drop3 = new ItemStack(WOHItems.RONIN_LEGGINGS.get());


            switch (itemDropIndex){
                case 0:
                    if(skeleton.getRandom().nextFloat() < 0.58F) {
                        event.getDrops().add(
                                new ItemEntity(
                                        skeleton.level(),
                                        skeleton.getX(),
                                        skeleton.getY(),
                                        skeleton.getZ(),
                                        drop1
                                )
                        );
                    }
                break;
                case 1:
                    if(skeleton.getRandom().nextFloat() < 0.35F) {
                        event.getDrops().add(
                                new ItemEntity(
                                        skeleton.level(),
                                        skeleton.getX(),
                                        skeleton.getY(),
                                        skeleton.getZ(),
                                        drop2
                                )
                        );
                    }
                    break;
                case 2:
                    if(skeleton.getRandom().nextFloat() < 0.25F) {
                        event.getDrops().add(
                                new ItemEntity(
                                        skeleton.level(),
                                        skeleton.getX(),
                                        skeleton.getY(),
                                        skeleton.getZ(),
                                        drop3
                                )
                        );
                    }
            }
        }
    }

}
