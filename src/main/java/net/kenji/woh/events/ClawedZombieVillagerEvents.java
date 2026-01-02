package net.kenji.woh.events;

import net.kenji.woh.WeaponsOfHarmony;
import net.kenji.woh.WohConfigCommon;
import net.kenji.woh.registry.WohItems;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.ZombieVillager;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = WeaponsOfHarmony.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ClawedZombieVillagerEvents {
    private static final String WAS_EXISTING_TAG = "was_existing_entity";
    private static final String CLAWED_TAG = "woh_clawed_zombie_villager";

    private static final float weaponRepairModuleChance = 0.225F;

    @SubscribeEvent
    public static void onEntityJoin(EntityJoinLevelEvent event) {
        if (!(event.getEntity() instanceof ZombieVillager zombieVillager)) return;

        if(zombieVillager.getPersistentData().getBoolean(WAS_EXISTING_TAG))
            return;
        zombieVillager.getPersistentData().putBoolean(WAS_EXISTING_TAG, true);

        if (event.getLevel().isClientSide()) return;
        if (zombieVillager.tickCount != 0) return;

        if (zombieVillager.getRandom().nextFloat() < WohConfigCommon.CLAWED_ZOMBIE_VILLAGER_SPAWN_CHANCE.get()) {
            zombieVillager.setItemSlot(
                    EquipmentSlot.MAINHAND,
                    new ItemStack(WohItems.TSUME.get())
            );
            zombieVillager.setItemSlot(
                    EquipmentSlot.HEAD,
                    new ItemStack(WohItems.METAL_RONIN_HEADWEAR.get())
            );
            zombieVillager.setDropChance(EquipmentSlot.MAINHAND, 0.0f);

            zombieVillager.getPersistentData().putBoolean(CLAWED_TAG, true);
        }
    }
    @SubscribeEvent
    public static void onLivingDrops(LivingDropsEvent event) {
        if (!(event.getEntity() instanceof ZombieVillager zombieVillager)) return;
        if (zombieVillager.level().isClientSide()) return;
        if (!zombieVillager.getPersistentData().getBoolean(CLAWED_TAG)) return;

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
