package net.kenji.woh.entities;

import net.kenji.woh.WeaponsOfHarmony;
import net.kenji.woh.entities.custom.BeamSlashEntity;
import net.kenji.woh.entities.custom.ExiledRoninEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITYTYPES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, WeaponsOfHarmony.MODID);

    public static final RegistryObject<EntityType<ExiledRoninEntity>> EXILED_RONIN = ENTITYTYPES.register("exiled_ronin", () -> EntityType.Builder.of(ExiledRoninEntity::new, MobCategory.MONSTER)
            .sized(0.6f, 1.95f).build("exiled_ronin"));
    public static final RegistryObject<EntityType<BeamSlashEntity>> BEAM_SLASH =
            ENTITYTYPES.register("beam_slash",
                    () -> EntityType.Builder.<BeamSlashEntity>of(
                                    BeamSlashEntity::new,
                                    MobCategory.MISC
                            )
                            .sized(1.5F, 0.25F) // hitbox size
                            .clientTrackingRange(64)
                            .updateInterval(1)
                            .build("beam_slash")
            );

    public static void register(IEventBus eventBus) {
        ENTITYTYPES.register(eventBus);
    }
}
