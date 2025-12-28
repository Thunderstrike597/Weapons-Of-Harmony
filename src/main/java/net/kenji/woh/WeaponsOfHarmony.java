package net.kenji.woh;

import com.mojang.logging.LogUtils;
import net.kenji.woh.entities.ModEntities;
import net.kenji.woh.gameasset.WohWeaponCategories;
import net.kenji.woh.registry.WOHSkills;
import net.kenji.woh.registry.WOHSounds;
import net.kenji.woh.registry.animation.WohAnimations;
import net.kenji.woh.registry.WOHItems;
import net.kenji.woh.tabs.WOHTabs;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
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
    }

    private void commonSetup(final net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent event) {

    }

    public static void RegisterWeaponType(WeaponCapabilityPresetRegistryEvent event) {
        event.getTypeEntry().put(new ResourceLocation(MODID, "one_hand_katana"), WeaponCapabilityPresets.UCHIGATANA);
        event.getTypeEntry().put(new ResourceLocation(MODID, "wakizashi"), WeaponCapabilityPresets.SWORD);
        event.getTypeEntry().put(new ResourceLocation(MODID, "tessen"), WeaponCapabilityPresets.DAGGER);


    }
    @OnlyIn(Dist.CLIENT)
    public static void regIcon(WeaponCategoryIconRegisterEvent event) {
        event.registerCategory(WohWeaponCategories.ONE_HAND_KATANA, new ItemStack((ItemLike) WOHItems.SHOTOGATANA.get()));
        event.registerCategory(WohWeaponCategories.TESSEN, new ItemStack((ItemLike) WOHItems.TESSEN.get()));
        event.registerCategory(WohWeaponCategories.WAKIZASHI, new ItemStack((ItemLike) WOHItems.WAKIZASHI.get()));
        event.registerCategory(WohWeaponCategories.ODACHI, new ItemStack((ItemLike) WOHItems.ODACHI.get()));

    }
    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientEvents {
        @net.minecraftforge.eventbus.api.SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
        }
    }
}
