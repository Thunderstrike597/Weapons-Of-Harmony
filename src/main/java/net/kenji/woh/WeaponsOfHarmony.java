package net.kenji.woh;

import com.mojang.logging.LogUtils;
import net.kenji.woh.entities.ModEntities;
import net.kenji.woh.gameasset.WohWeaponCapabilityPresets;
import net.kenji.woh.gameasset.WohWeaponCategories;
import net.kenji.woh.gameasset.WohSkills;
import net.kenji.woh.network.WohPacketHandler;
import net.kenji.woh.registry.WohSounds;
import net.kenji.woh.registry.WohColliderPreset;
import net.kenji.woh.registry.animation.WohAnimations;
import net.kenji.woh.registry.WohItems;
import net.kenji.woh.tabs.WOHTabs;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import yesman.epicfight.api.client.forgeevent.WeaponCategoryIconRegisterEvent;
import yesman.epicfight.api.forgeevent.WeaponCapabilityPresetRegistryEvent;
import yesman.epicfight.world.capabilities.item.WeaponCategory;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(WeaponsOfHarmony.MODID)
public class WeaponsOfHarmony {

    public static final String MODID = "woh";
    private static final Logger LOGGER = LogUtils.getLogger();


    public WeaponsOfHarmony() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // (Optional) register lifecycle listeners
        modEventBus.addListener(this::commonSetup);

        WohItems.register(modEventBus);
        WOHTabs.register(modEventBus);
        ModEntities.register(modEventBus);
        WohSounds.SOUNDS.register(modEventBus);

        modEventBus.addListener(WeaponsOfHarmony::RegisterWeaponType);
        WeaponCategory.ENUM_MANAGER.registerEnumCls(MODID, WohWeaponCategories.class);

        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> modEventBus.addListener(WeaponsOfHarmony::regIcon));

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, WohConfigCommon.SPEC, "WeaponsOfHarmony-Common.toml");
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, WohConfigClient.SPEC, "WeaponsOfHarmony-Client.toml");


        modEventBus.addListener(WohAnimations::registerAnimations);
        modEventBus.addListener(WohSkills::buildSkillEvent);
        MinecraftForge.EVENT_BUS.addListener(this::addReloadListnerEvent);

    }

    private void commonSetup(final net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent event) {
        event.enqueueWork(WohPacketHandler::register);
    }

    public static void RegisterWeaponType(WeaponCapabilityPresetRegistryEvent event) {
        event.getTypeEntry().put(new ResourceLocation(MODID, "shotogatana"), WohWeaponCapabilityPresets.SHOTOGATANA);
        event.getTypeEntry().put(new ResourceLocation(MODID, "tessen"), WohWeaponCapabilityPresets.TESSEN);
        event.getTypeEntry().put(new ResourceLocation(MODID, "tsume"), WohWeaponCapabilityPresets.TSUME);

        event.getTypeEntry().put(new ResourceLocation(MODID, "wakizashi"), WohWeaponCapabilityPresets.WAKIZASHI);
        event.getTypeEntry().put(new ResourceLocation(MODID, "odachi"), WohWeaponCapabilityPresets.ODACHI);

    }
    @OnlyIn(Dist.CLIENT)
    public static void regIcon(WeaponCategoryIconRegisterEvent event) {
        event.registerCategory(WohWeaponCategories.SHOTOGATANA, new ItemStack((ItemLike) WohItems.SHOTOGATANA.get()));
        event.registerCategory(WohWeaponCategories.TESSEN, new ItemStack((ItemLike) WohItems.TESSEN.get()));
        event.registerCategory(WohWeaponCategories.TSUME, new ItemStack((ItemLike) WohItems.TSUME.get()));

        event.registerCategory(WohWeaponCategories.WAKIZASHI, new ItemStack((ItemLike) WohItems.WAKIZASHI.get()));
        event.registerCategory(WohWeaponCategories.ODACHI, new ItemStack((ItemLike) WohItems.ODACHI.get()));
    }
    private void addReloadListnerEvent(AddReloadListenerEvent event) {
        event.addListener(new WohColliderPreset());
    }
    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientEvents {
        @net.minecraftforge.eventbus.api.SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
        }
    }
}
