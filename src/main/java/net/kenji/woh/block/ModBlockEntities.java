package net.kenji.woh.block;

import net.kenji.woh.WeaponsOfHarmony;
import net.kenji.woh.block.custom.entity.SwordPedistoolBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, WeaponsOfHarmony.MODID);

    public static final RegistryObject<BlockEntityType<SwordPedistoolBlockEntity>> SWORD_PEDISTOOL_BE =
            BLOCK_ENTITIES.register("sword_pedistool",
                    () -> BlockEntityType.Builder.of(
                            SwordPedistoolBlockEntity::new,
                            ModBlocks.SWORD_PEDISTOOL.get()
                    ).build(null));
    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }

}
