package net.kenji.woh.events;

import net.kenji.woh.WeaponsOfHarmony;
import net.kenji.woh.WohConfigCommon;
import net.kenji.woh.registry.WOHItems;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Pillager;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = WeaponsOfHarmony.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class WarFanPillagerEvents {

    private static final String WAS_EXISTING_TAG = "was_existing_entity";

    private static final String WAR_FAN_TAG = "woh_war_fan_pillager";

    private static final String WAR_FAN_OFFHAND_TAG = "woh_war_fan_pillager_offhand";

    @SubscribeEvent
    public static void onEntityJoin(EntityJoinLevelEvent event) {
        if (!(event.getEntity() instanceof Pillager pillager)) return;

        if(pillager.getPersistentData().getBoolean(WAS_EXISTING_TAG))
            return;
        pillager.getPersistentData().putBoolean(WAS_EXISTING_TAG, true);

        if (event.getLevel().isClientSide()) return;
        if (pillager.tickCount != 0) return;

        if (pillager.getRandom().nextFloat() < WohConfigCommon.WAR_FAN_PILLAGER_SPAWN_CHANCE.get()) {
            double offhandChance = Math.random();
            pillager.setItemSlot(
                    EquipmentSlot.MAINHAND,
                    new ItemStack(WOHItems.TESSEN.get())
            );
            if(offhandChance < 0.45) {
                pillager.setItemSlot(
                        EquipmentSlot.OFFHAND,
                        new ItemStack(WOHItems.TESSEN.get())
                );
            }
            pillager.setDropChance(EquipmentSlot.MAINHAND, 0.0f);

            pillager.getPersistentData().putBoolean(WAR_FAN_TAG, true);
            pillager.getPersistentData().putBoolean(WAR_FAN_OFFHAND_TAG, true);

        }
    }
    @SubscribeEvent
    public static void onLivingDrops(LivingDropsEvent event) {
        if (!(event.getEntity() instanceof Pillager pillager)) return;
        if (pillager.level().isClientSide()) return;
        if (!pillager.getPersistentData().getBoolean(WAR_FAN_TAG)) return;

        double dropChance = !pillager.getPersistentData().getBoolean(WAR_FAN_OFFHAND_TAG) ? 0.4F : 0.6F;

        if (pillager.getRandom().nextFloat() < dropChance) {
            ItemStack drop = new ItemStack(WOHItems.BROKEN_FAN_BLADE.get());
            event.getDrops().add(
                    new ItemEntity(
                            pillager.level(),
                            pillager.getX(),
                            pillager.getY(),
                            pillager.getZ(),
                            drop
                    )
            );
        }
    }

}
