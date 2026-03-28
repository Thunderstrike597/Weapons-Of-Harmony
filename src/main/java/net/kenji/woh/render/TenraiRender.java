package net.kenji.woh.render;

import com.google.gson.JsonElement;
import com.mojang.blaze3d.vertex.PoseStack;
import net.kenji.woh.WeaponsOfHarmony;
import net.kenji.woh.api.manager.ShotogatanaManager;
import net.kenji.woh.api.manager.TenraiManager;
import net.kenji.woh.gameasset.WohSkills;
import net.kenji.woh.item.custom.base.HolsterWeaponBase;
import net.kenji.woh.registry.WohItems;
import net.kenji.woh.registry.animation.TenraiAnimations;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import org.jline.utils.Log;
import yesman.epicfight.api.animation.AnimationPlayer;
import yesman.epicfight.api.client.animation.Layer;
import yesman.epicfight.api.client.animation.property.TrailInfo;
import yesman.epicfight.api.client.model.ItemSkinsReloadListener;
import yesman.epicfight.api.utils.math.MathUtils;
import yesman.epicfight.api.utils.math.OpenMatrix4f;
import yesman.epicfight.client.particle.AbstractTrailParticle;
import yesman.epicfight.client.particle.AnimationTrailParticle;
import yesman.epicfight.client.renderer.patched.item.RenderItemBase;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.world.capabilities.entitypatch.EntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.capabilities.item.WeaponCapability;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@Mod.EventBusSubscriber(modid = WeaponsOfHarmony.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class TenraiRender extends RenderItemBase {
    private final ItemStack bladeStack;
    private final ItemStack spiltPrimary;
    private final ItemStack spiltSecondary;

    private static Map<UUID, BladeSplit> dualRenderMap = new HashMap<>();

    class BladeSplit {
        boolean visualState = false; // what you're currently rendering
        Boolean targetState = null;  // null = no transition, true/false = pending
    }

    public TenraiRender(JsonElement jsonElement) {
        super(jsonElement);
        if (jsonElement.getAsJsonObject().has("tenrai")) {
            this.bladeStack = new ItemStack((ItemLike) Objects.requireNonNull((Item) ForgeRegistries.ITEMS.getValue(ResourceLocation.parse(jsonElement.getAsJsonObject().get("tenrai").getAsString()))));
        }else {
            this.bladeStack = new ItemStack((ItemLike) WohItems.ARBITERS_BLADE.get());
        }
        this.spiltPrimary = new ItemStack((ItemLike) WohItems.TENRAI_SPLIT_PRIMARY.get());
        this.spiltSecondary = new ItemStack((ItemLike) WohItems.TENRAI_SPLIT_SECONDARY.get());
    }


    private boolean shouldRenderDual(LivingEntityPatch<?> entitypatch) {
        if (entitypatch instanceof PlayerPatch<?> playerPatch) {
            UUID playerID = playerPatch.getOriginal().getUUID();
            return TenraiManager.renderSplitMap.getOrDefault(playerID, false);
        }
        return false;
    }

    @Override
    public void renderItemInHand(ItemStack stack, LivingEntityPatch<?> entitypatch, InteractionHand hand, OpenMatrix4f[] poses, MultiBufferSource buffer, PoseStack poseStack, int packedLight, float partialTicks) {
        // 2️⃣ Conditional emissive pass
        if (!shouldRenderDual(entitypatch)) {
            // 1️⃣ Normal render
            super.renderItemInHand(
                    stack,
                    entitypatch,
                    hand,
                    poses,
                    buffer,
                    poseStack,
                    packedLight,
                    partialTicks
            );
            return;
        }

        super.renderItemInHand(
                spiltPrimary,
                entitypatch,
                hand,
                poses,
                buffer,
                poseStack,
                packedLight,
                partialTicks
        );
        super.renderItemInHand(
                spiltSecondary,
                entitypatch,
                InteractionHand.OFF_HAND,
                poses,
                buffer,
                poseStack,
                packedLight,
                partialTicks
        );
    }
}
