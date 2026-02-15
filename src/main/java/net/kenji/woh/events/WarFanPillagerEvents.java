package net.kenji.woh.events;

import net.kenji.woh.WeaponsOfHarmony;
import net.kenji.woh.WohConfigCommon;
import net.kenji.woh.entities.WohEntities;
import net.kenji.woh.entities.custom.alt_entities.WarFanPillagerEntity;
import net.kenji.woh.registry.WohItems;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Pillager;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = WeaponsOfHarmony.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class WarFanPillagerEvents {


    private static final float weaponRepairModuleChance = 0.18F;

    @SubscribeEvent
    public static void onEntityJoin(EntityJoinLevelEvent event) {
        if (!(event.getEntity() instanceof Pillager pillager)) return;
        if(event.loadedFromDisk()) return;

        if (event.getLevel().isClientSide()) return;
        if(!(event.getLevel() instanceof ServerLevel serverLevel)) return;

        if (pillager.tickCount != 0) return;

        if (pillager.getRandom().nextFloat() < WohConfigCommon.WAR_FAN_PILLAGER_SPAWN_CHANCE.get()) {
            event.setCanceled(true);
            WohEntities.WAR_FAN_PILLAGER.get().spawn(serverLevel, event.getEntity().blockPosition(), MobSpawnType.NATURAL);
        }
    }
    @SubscribeEvent
    public static void onLivingDrops(LivingDropsEvent event) {
        if (!(event.getEntity() instanceof WarFanPillagerEntity pillager)) return;
        if (pillager.level().isClientSide()) return;

        double dropChance = !pillager.getPersistentData().getBoolean(WarFanPillagerEntity.WAR_FAN_OFFHAND_TAG) ? 0.38F : 0.48F;

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
