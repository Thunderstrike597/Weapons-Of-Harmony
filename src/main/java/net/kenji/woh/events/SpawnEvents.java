package net.kenji.woh.events;

import net.kenji.woh.WeaponsOfHarmony;
import net.kenji.woh.registry.item.WOHItems;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.living.MobSpawnEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = WeaponsOfHarmony.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class SpawnEvents {

    @SubscribeEvent
    public static void onEntityJoin(EntityJoinLevelEvent event) {
        if (!(event.getEntity() instanceof Skeleton skeleton)) return;
        if (event.getLevel().isClientSide()) return;

        // Only run once (first tick after spawn)
        if (skeleton.tickCount != 0) return;

        if (skeleton.getRandom().nextFloat() < 0.20f) {
            skeleton.setItemSlot(
                    EquipmentSlot.MAINHAND,
                    new ItemStack(WOHItems.SHOTOGATANA.get())
            );

            // Optional: prevent bow drops interfering
            skeleton.setDropChance(EquipmentSlot.MAINHAND, 0.0f);
        }
    }
}
