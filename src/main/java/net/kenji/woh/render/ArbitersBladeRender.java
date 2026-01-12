package net.kenji.woh.render;

import com.google.gson.JsonElement;
import com.mojang.blaze3d.vertex.PoseStack;
import net.kenji.woh.WeaponsOfHarmony;
import net.kenji.woh.gameasset.WohSkills;
import net.kenji.woh.registry.WohItems;
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
import yesman.epicfight.api.utils.math.MathUtils;
import yesman.epicfight.api.utils.math.OpenMatrix4f;
import yesman.epicfight.client.renderer.patched.item.RenderItemBase;
import yesman.epicfight.model.armature.HumanoidArmature;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.world.capabilities.entitypatch.EntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;

import java.util.Objects;

@Mod.EventBusSubscriber(modid = WeaponsOfHarmony.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ArbitersBladeRender extends RenderItemBase {
    private final ItemStack bladeStack;

    public ArbitersBladeRender(JsonElement jsonElement) {
        super(jsonElement);
        if (jsonElement.getAsJsonObject().has("blade")) {
            this.bladeStack = new ItemStack((ItemLike) Objects.requireNonNull((Item) ForgeRegistries.ITEMS.getValue(ResourceLocation.parse(jsonElement.getAsJsonObject().get("blade").getAsString()))));
        }else {
            this.bladeStack = new ItemStack((ItemLike) WohItems.ARBITERS_BLADE.get());
        }

    }


    private boolean shouldRenderEmissive(EntityPatch<?> entitypatch) {
        if (entitypatch instanceof PlayerPatch<?> playerPatch) {
            SkillContainer container = playerPatch.getSkill(WohSkills.ARBITERS_SLASH);
            if (container != null) {
                if (container.isActivated()) {
                    return true;
                }
            }
        }
        return false;
    }

    private ItemStack getGlintStack(EntityPatch<?> entitypatch) {
        ItemStack stack = bladeStack.copy();
        if(shouldRenderEmissive(entitypatch)) {
            stack.getOrCreateTag().putBoolean("woh_force_glint", true);
        }
        else{
            stack.getOrCreateTag().putBoolean("woh_force_glint", false);
        }
        return stack;
    }

    @Override
    public void renderItemInHand(ItemStack stack, LivingEntityPatch<?> entitypatch, InteractionHand hand, OpenMatrix4f[] poses, MultiBufferSource buffer, PoseStack poseStack, int packedLight, float partialTicks) {

        // 2️⃣ Conditional emissive pass
        if (!shouldRenderEmissive(entitypatch)) {
            // 1️⃣ Normal render
            super.renderItemInHand(stack, entitypatch, hand, poses, buffer, poseStack, packedLight, partialTicks);
            return;
        }

        ItemStack glintStack = getGlintStack(entitypatch);

        OpenMatrix4f modelMatrix = this.getCorrectionMatrix(entitypatch, InteractionHand.MAIN_HAND, poses);
        poseStack.pushPose();

        MathUtils.mulStack(poseStack, modelMatrix);
        Minecraft.getInstance().getItemRenderer().renderStatic(
                glintStack,
                ItemDisplayContext.THIRD_PERSON_RIGHT_HAND,
                15,
                OverlayTexture.WHITE_OVERLAY_V,
                poseStack,
                buffer,
                null,
                0
        );

        poseStack.popPose();
    }
}
