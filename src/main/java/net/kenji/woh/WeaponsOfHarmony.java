package net.kenji.woh;

import com.mojang.logging.LogUtils;
import net.kenji.woh.entities.ModEntities;
import net.kenji.woh.gameasset.WohWeaponCapabilityPresets;
import net.kenji.woh.gameasset.WohWeaponCategories;
import net.kenji.woh.registry.WOHSkills;
import net.kenji.woh.registry.WOHSounds;
import net.kenji.woh.registry.WohColliderPreset;
import net.kenji.woh.registry.animation.WohAnimations;
import net.kenji.woh.registry.WOHItems;
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
import yesman.epicfight.gameasset.ColliderPreset;
import yesman.epicfight.world.capabilities.item.WeaponCapabilityPresets;
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

        WOHItems.register(modEventBus);
        WOHTabs.register(modEventBus);
        ModEntities.register(modEventBus);
        WOHSounds.SOUNDS.register(modEventBus);

        modEventBus.addListener(WeaponsOfHarmony::RegisterWeaponType);
        WeaponCategory.ENUM_MANAGER.registerEnumCls(MODID, WohWeaponCategories.class);

        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> modEventBus.addListener(WeaponsOfHarmony::regIcon));

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, WohConfigCommon.SPEC, "WeaponsOfHarmony-Common.toml");


        modEventBus.addListener(WohAnimations::registerAnimations);
        modEventBus.addListener(WOHSkills::buildSkillEvent);
        MinecraftForge.EVENT_BUS.addListener(this::addReloadListnerEvent);

    }

    private void commonSetup(final net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent event) {

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
        event.registerCategory(WohWeaponCategories.SHOTOGATANA, new ItemStack((ItemLike) WOHItems.SHOTOGATANA.get()));
        event.registerCategory(WohWeaponCategories.TESSEN, new ItemStack((ItemLike) WOHItems.TESSEN.get()));
        event.registerCategory(WohWeaponCategories.TSUME, new ItemStack((ItemLike) WOHItems.TSUME.get()));

        event.registerCategory(WohWeaponCategories.WAKIZASHI, new ItemStack((ItemLike) WOHItems.WAKIZASHI.get()));
        event.registerCategory(WohWeaponCategories.ODACHI, new ItemStack((ItemLike) WOHItems.ODACHI.get()));
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
