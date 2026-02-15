package net.kenji.woh.events;

import net.kenji.woh.WeaponsOfHarmony;
import net.kenji.woh.entities.WohEntities;
import net.kenji.woh.entities.custom.ExiledRoninEntity;
import net.kenji.woh.entities.custom.alt_entities.RoninSkeletonEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.DefaultAttributes;
import net.minecraft.world.entity.monster.Pillager;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.entity.monster.ZombieVillager;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = WeaponsOfHarmony.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventBusEvents {


    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(WohEntities.EXILED_RONIN.get(),
                ExiledRoninEntity.createMobAttributes()
                        .add(Attributes.MAX_HEALTH, 80)
                        .build());

        // Get the exact vanilla attributes
        event.put(WohEntities.RONIN_SKELETON.get(),
                DefaultAttributes.getSupplier(EntityType.SKELETON));

        event.put(WohEntities.WAR_FAN_PILLAGER.get(),
                DefaultAttributes.getSupplier(EntityType.PILLAGER));

        event.put(WohEntities.CLAWED_ZOMBIE_VILLAGER.get(),
                DefaultAttributes.getSupplier(EntityType.ZOMBIE_VILLAGER));
    }


}