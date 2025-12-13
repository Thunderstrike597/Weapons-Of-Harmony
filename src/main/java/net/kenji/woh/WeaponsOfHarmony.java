package net.kenji.woh;

import com.mojang.logging.LogUtils;
import net.kenji.woh.entities.ModEntities;
import net.kenji.woh.registry.WOHSkills;
import net.kenji.woh.registry.WOHSounds;
import net.kenji.woh.registry.animation.WOHAnimations;
import net.kenji.woh.registry.item.WOHItems;
import net.kenji.woh.tabs.WOHTabs;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(WeaponsOfHarmony.MODID)
public class WeaponsOfHarmony {

    public static final String MODID = "woh";
    private static final Logger LOGGER = LogUtils.getLogger();


    public WeaponsOfHarmony() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // (Optional) register lifecycle listeners
        modEventBus.addListener(this::commonSetup);

        WOHItems.register(modEventBus);
        WOHTabs.register(modEventBus);
        ModEntities.register(modEventBus);
        WOHSounds.SOUNDS.register(modEventBus);

        modEventBus.addListener(WOHAnimations::registerAnimations);
        modEventBus.addListener(WOHSkills::buildSkillEvent);
    }

    private void commonSetup(final net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent event) {

    }

    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientEvents {
        @net.minecraftforge.eventbus.api.SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
        }
    }
}
