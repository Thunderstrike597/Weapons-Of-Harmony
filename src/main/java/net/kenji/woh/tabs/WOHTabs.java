package net.kenji.woh.tabs;

import net.kenji.woh.WeaponsOfHarmony;
import net.kenji.woh.registry.item.WOHItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class WOHTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, WeaponsOfHarmony.MODID);

    public static final RegistryObject<CreativeModeTab> WOH_TAB = CREATIVE_MODE_TABS.register(WeaponsOfHarmony.MODID, () -> CreativeModeTab.builder()
            .title(Component.translatable("creativetab.woh"))
            .icon(() -> new ItemStack(WOHItems.SHOTOGATANA.get()))
            .displayItems((itemDisplayParameters, output) -> {
                output.accept(WOHItems.METAL_RONIN_HEADWEAR.get());

                output.accept(WOHItems.SHOTOGATANA.get());
                output.accept(WOHItems.WAKIZASHI.get());
                output.accept(WOHItems.ODACHI.get());
                output.accept(WOHItems.FOLDED_IRON.get());
                output.accept(WOHItems.FOLDED_STEEL.get());
                output.accept(WOHItems.HARDENED_STEEL_INGOT.get());
                output.accept(WOHItems.BLADE_CURVE_MODULE.get());

                output.accept(WOHItems.PATTERNED_HANDLE.get());
                output.accept(WOHItems.GOLD_TSUBA.get());
                output.accept(WOHItems.HARDENED_BLADE.get());
            })
            .build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
