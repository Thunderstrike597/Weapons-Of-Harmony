package net.kenji.woh.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import yesman.epicfight.world.capabilities.item.WeaponCapability;
import yesman.epicfight.world.entity.eventlistener.ComboCounterHandleEvent;

@Mixin(value = WeaponCapability.class, remap = false)
public interface WeaponCapabilityAccessor {

    @Accessor("comboCounterHandler")
    ComboCounterHandleEvent.ComboCounterHandler getComboHandler();
}
