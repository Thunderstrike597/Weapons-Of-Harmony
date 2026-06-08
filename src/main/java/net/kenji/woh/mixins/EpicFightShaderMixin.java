package net.kenji.woh.mixins;

import net.kenji.woh.WohConfigClient;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import yesman.epicfight.world.capabilities.entitypatch.EntityDecorations;

import java.util.Map;

@Mixin(value = EntityDecorations.class, remap = false)
public class EpicFightShaderMixin {

    @Shadow
    @Final
    private Map<ResourceLocation, EntityDecorations.DecorationOverlay> decorationOverlays;

    @Inject(method = "addDecorationOverlay", at = @At("HEAD"), cancellable = true)
    public void onInitiate(ResourceLocation id, EntityDecorations.DecorationOverlay entityOverlay, CallbackInfo ci){
        if(WohConfigClient.EPIC_FIGHT_SHADER_COMPAT.get()){
            ci.cancel();
        }
    }

}
