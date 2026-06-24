package net.kenji.woh.block;

import net.kenji.woh.WeaponsOfHarmony;
import net.kenji.woh.block.custom.SwordPedistoolBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, WeaponsOfHarmony.MODID);

    public static final RegistryObject<Block> SWORD_PEDISTOOL =
            BLOCKS.register("sword_pedistool",
                    () -> new SwordPedistoolBlock(
                            BlockBehaviour.Properties.of()
                                    .mapColor(MapColor.STONE)
                                    .strength(2.5F)
                                    .noOcclusion()
                                    .requiresCorrectToolForDrops()
                    ));


    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }

}
