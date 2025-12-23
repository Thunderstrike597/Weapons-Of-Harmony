package net.kenji.woh.events;

import net.kenji.woh.WeaponsOfHarmony;
import net.kenji.woh.WohConfigCommon;
import net.kenji.woh.registry.item.WOHItems;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.MobSpawnEvent;
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
        if (skeleton.getRandom().nextFloat() < 0.1f) {
            ItemStack drop = new ItemStack(WOHItems.BLADE_CURVE_MODULE.get());

            event.getDrops().add(
                    new ItemEntity(
                            skeleton.level(),
                            skeleton.getX(),
                            skeleton.getY(),
                            skeleton.getZ(),
                            drop
                    )
            );
        }
    }

}
