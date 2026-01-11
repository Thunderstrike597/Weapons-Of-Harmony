package net.kenji.woh.tabs;

import net.kenji.woh.WeaponsOfHarmony;
import net.kenji.woh.registry.WohItems;
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
            .icon(() -> new ItemStack(WohItems.SHOTOGATANA.get()))
            .displayItems((itemDisplayParameters, output) -> {
                output.accept(WohItems.METAL_RONIN_HEADWEAR.get());
                output.accept(WohItems.RONIN_TUNIC.get());
                output.accept(WohItems.RONIN_LEGGINGS.get());
                output.accept(WohItems.RONIN_BOOTS.get());
                output.accept(WohItems.SHOTOGATANA.get());
                output.accept(WohItems.TESSEN.get());
                output.accept(WohItems.TSUME.get());
                output.accept(WohItems.ARBITERS_BLADE.get());
                output.accept(WohItems.WAKIZASHI.get());
                output.accept(WohItems.ODACHI.get());
                output.accept(WohItems.FOLDED_IRON.get());
                output.accept(WohItems.FOLDED_STEEL.get());
                output.accept(WohItems.HARDENED_STEEL_INGOT.get());
                output.accept(WohItems.BLADE_CURVE_MODULE.get());
                output.accept(WohItems.WEAPON_REPAIR_MODULE.get());
                output.accept(WohItems.BROKEN_BLADE_AND_SHEATH.get());
                output.accept(WohItems.BROKEN_FAN_BLADE.get());
                output.accept(WohItems.BROKEN_CLAWS.get());

            })
            .build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
