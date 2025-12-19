package net.kenji.woh.events;

import net.kenji.woh.WeaponsOfHarmony;
import net.kenji.woh.entities.ModEntities;
import net.kenji.woh.entities.custom.ExiledRoninEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jline.utils.Log;

@Mod.EventBusSubscriber(modid = WeaponsOfHarmony.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventBusEvents {

    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(ModEntities.EXILED_RONIN.get(),
                ExiledRoninEntity.createMobAttributes()
                        .add(Attributes.MAX_HEALTH, 80)
                        .build());
    }
}