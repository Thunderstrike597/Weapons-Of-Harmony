package net.kenji.woh.events;

import net.kenji.woh.WeaponsOfHarmony;
import net.kenji.woh.WohConfigCommon;
import net.kenji.woh.registry.WohItems;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Pillager;
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

    private static final float weaponRepairModuleChance = 0.18F;

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
                    new ItemStack(WohItems.TESSEN.get())
            );
            pillager.setItemSlot(
                    EquipmentSlot.HEAD,
                    new ItemStack(WohItems.METAL_RONIN_HEADWEAR.get())
            );
            if(offhandChance < WohConfigCommon.WAR_FAN_PILLAGER_OFFHAND_CHANCE.get()) {
                pillager.setItemSlot(
                        EquipmentSlot.OFFHAND,
                        new ItemStack(WohItems.TESSEN.get())
                );
                pillager.getPersistentData().putBoolean(WAR_FAN_OFFHAND_TAG, true);
            }
            pillager.setDropChance(EquipmentSlot.MAINHAND, 0.0f);

            pillager.getPersistentData().putBoolean(WAR_FAN_TAG, true);
        }
    }
    @SubscribeEvent
    public static void onLivingDrops(LivingDropsEvent event) {
        if (!(event.getEntity() instanceof Pillager pillager)) return;
        if (pillager.level().isClientSide()) return;
        if (!pillager.getPersistentData().getBoolean(WAR_FAN_TAG)) return;

        double dropChance = !pillager.getPersistentData().getBoolean(WAR_FAN_OFFHAND_TAG) ? 0.38F : 0.48F;

        if (pillager.getRandom().nextFloat() < dropChance) {
            ItemStack drop = new ItemStack(WohItems.BROKEN_FAN_BLADE.get());
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
        if(pillager.getRandom().nextFloat() < weaponRepairModuleChance){
            ItemStack drop = new ItemStack(WohItems.WEAPON_REPAIR_MODULE.get());
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
