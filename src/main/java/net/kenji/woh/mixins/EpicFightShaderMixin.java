package net.kenji.woh.mixins;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import org.joml.Vector4f;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import yesman.epicfight.config.ClientConfig;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.skill.passive.BerserkerSkill;
import yesman.epicfight.world.capabilities.entitypatch.EntityDecorations;

import java.util.Map;

@Mixin(value = EntityDecorations.class, remap = false)
public class EpicFightShaderMixin {

    @Shadow
    @Final
    private Map<ResourceLocation, EntityDecorations.DecorationOverlay> decorationOverlays;

    @Inject(method = "addDecorationOverlay", at = @At("HEAD"), cancellable = true)
    public void onInitiate(ResourceLocation id, EntityDecorations.DecorationOverlay entityOverlay, CallbackInfo ci){
        if(!ClientConfig.activateComputeShader){
            ci.cancel();
        }
    }

}
