package net.kenji.woh.api.item_overrides;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import net.kenji.woh.WeaponsOfHarmony;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ModelEvent;
import net.minecraftforge.client.model.geometry.IGeometryLoader;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jline.utils.Log;

public class WeaponGeometryLoader implements IGeometryLoader<WeaponGeometry> {
    @Override
    public WeaponGeometry read(JsonObject json, JsonDeserializationContext ctx) {
        ResourceLocation defaultObj = ResourceLocation.parse(json.get("default_model").getAsString());
        ResourceLocation guiTexture = json.has("gui_texture")
                ? ResourceLocation.parse(json.get("gui_texture").getAsString())
                : null;
        return new WeaponGeometry(defaultObj, guiTexture);
    }
    @Mod.EventBusSubscriber(modid = WeaponsOfHarmony.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class Events {  // <-- add static
        @SubscribeEvent
        public static void onModelLoad(ModelEvent.RegisterGeometryLoaders event) {
            event.register("weapon", new WeaponGeometryLoader());
        }
    }
    
}