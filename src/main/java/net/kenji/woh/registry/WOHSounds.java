package net.kenji.woh.registry;

import net.kenji.woh.WeaponsOfHarmony;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class WOHSounds {
    public static final DeferredRegister<SoundEvent> SOUNDS;

    public static final RegistryObject<SoundEvent> SWORD_UNSHEATHE;

    private static RegistryObject<SoundEvent> registerSound(String name) {
        ResourceLocation res = new ResourceLocation( WeaponsOfHarmony.MODID, name);
        return SOUNDS.register(name, () -> SoundEvent.createVariableRangeEvent(res));
    }

    static {
        SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, WeaponsOfHarmony.MODID);
        SWORD_UNSHEATHE = registerSound("entity.weapon.sword_unsheathe");

    }
}
