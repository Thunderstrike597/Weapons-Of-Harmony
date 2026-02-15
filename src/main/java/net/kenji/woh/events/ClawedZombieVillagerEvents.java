package net.kenji.woh.events;

import net.kenji.woh.WeaponsOfHarmony;
import net.kenji.woh.WohConfigCommon;
import net.kenji.woh.entities.WohEntities;
import net.kenji.woh.entities.custom.alt_entities.ClawedZombieVillagerEntity;
import net.kenji.woh.registry.WohItems;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.ZombieVillager;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = WeaponsOfHarmony.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ClawedZombieVillagerEvents {

    private static final float weaponRepairModuleChance = 0.225F;

    @SubscribeEvent
    public static void onEntityJoin(EntityJoinLevelEvent event) {
        if (!(event.getEntity() instanceof ZombieVillager zombieVillager)) return;
        if(event.loadedFromDisk()) return;

        if (event.getLevel().isClientSide()) return;
        if(!(event.getLevel() instanceof ServerLevel serverLevel)) return;

        if (zombieVillager.tickCount != 0) return;

        if (zombieVillager.getRandom().nextFloat() < WohConfigCommon.CLAWED_ZOMBIE_VILLAGER_SPAWN_CHANCE.get()) {
            event.setCanceled(true);
            WohEntities.RONIN_SKELETON.get().spawn(serverLevel, event.getEntity().blockPosition(), MobSpawnType.NATURAL);
        }
    }
    @SubscribeEvent
    public static void onLivingDrops(LivingDropsEvent event) {
        if (!(event.getEntity() instanceof ClawedZombieVillagerEntity zombieVillager)) return;
        if (zombieVillager.level().isClientSide()) return;

        double dropChance = 0.4F;

        if (zombieVillager.getRandom().nextFloat() < dropChance) {
            ItemStack drop = new ItemStack(WohItems.BROKEN_CLAWS.get());
            event.getDrops().add(
                    new ItemEntity(
                            zombieVillager.level(),
                            zombieVillager.getX(),
                            zombieVillager.getY(),
                            zombieVillager.getZ(),
                            drop
                    )
            );
        }
        if(zombieVillager.getRandom().nextFloat() < weaponRepairModuleChance){
            ItemStack drop = new ItemStack(WohItems.WEAPON_REPAIR_MODULE.get());
            event.getDrops().add(
                    new ItemEntity(
                            zombieVillager.level(),
                            zombieVillager.getX(),
                            zombieVillager.getY(),
                            zombieVillager.getZ(),
                            drop
                    )
            );
        }

    }

}
