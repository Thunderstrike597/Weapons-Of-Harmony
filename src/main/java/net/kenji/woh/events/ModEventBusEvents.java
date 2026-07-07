package net.kenji.woh.events;

import net.kenji.woh.WeaponsOfHarmony;
import net.kenji.woh.entities.WohEntities;
import net.kenji.woh.entities.custom.ExiledRoninEntity;
import net.kenji.woh.entities.custom.alt_entities.RoninSkeletonEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.DefaultAttributes;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import yesman.epicfight.api.forgeevent.EntityPatchRegistryEvent;
import yesman.epicfight.gameasset.Armatures;
import yesman.epicfight.world.capabilities.entitypatch.EntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.Factions;

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
    @SubscribeEvent
    public static void registerPatchedEntities(EntityPatchRegistryEvent event) {
        // Disabled: RoninSkeletonPatch breaks client-side entity construction.
        // The Ronin skeleton is handled by data/woh/epicfight_mobpatch/ronin_skeleton.json instead.
    }
    @SubscribeEvent
    public static void commonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(ModEventBusEvents::registerEntityTypeArmatures);
    }

    private static void registerEntityTypeArmatures() {
        Armatures.registerEntityTypeArmature(WohEntities.RONIN_SKELETON.get(), Armatures.BIPED);
    }

}