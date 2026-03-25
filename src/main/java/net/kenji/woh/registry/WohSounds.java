package net.kenji.woh.registry;

import net.kenji.woh.WeaponsOfHarmony;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class WohSounds {
    public static final DeferredRegister<SoundEvent> SOUNDS;

    public static final RegistryObject<SoundEvent> SWORD_UNSHEATHE;
    public static final RegistryObject<SoundEvent> SHOTOGATANA_SWING;
    public static final RegistryObject<SoundEvent> ODACHI_SWING;
    public static final RegistryObject<SoundEvent> TENRAI_SWING;

    private static RegistryObject<SoundEvent> registerSound(String name) {
        ResourceLocation res = new ResourceLocation(WeaponsOfHarmony.MODID, name);
        return SOUNDS.register(name, () -> SoundEvent.createVariableRangeEvent(res));
    }

    static {
        SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, WeaponsOfHarmony.MODID);
        SWORD_UNSHEATHE = registerSound("entity.weapon.sword_unsheathe");
        SHOTOGATANA_SWING = registerSound("entity.weapon.shotogatana_swing");
        ODACHI_SWING = registerSound("entity.weapon.odachi_swing");
        TENRAI_SWING = registerSound("entity.weapon.tenrai_swing");

    }
}
