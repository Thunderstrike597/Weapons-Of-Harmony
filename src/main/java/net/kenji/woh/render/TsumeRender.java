package net.kenji.woh.render;

import com.google.gson.JsonElement;
import com.mojang.blaze3d.vertex.PoseStack;
import net.kenji.woh.WeaponsOfHarmony;
import net.kenji.woh.registry.WohItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import yesman.epicfight.api.utils.math.MathUtils;
import yesman.epicfight.api.utils.math.OpenMatrix4f;
import yesman.epicfight.client.renderer.patched.item.RenderItemBase;
import yesman.epicfight.model.armature.HumanoidArmature;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

@Mod.EventBusSubscriber(modid = WeaponsOfHarmony.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class TsumeRender extends RenderItemBase {
    private final ItemStack tsumeStack;
    private static final Map<EntityType<?>, Float> HAND_INWARD_OFFSETS = new HashMap<>();

    public TsumeRender(JsonElement jsonElement) {
        super(jsonElement);
        if (jsonElement.getAsJsonObject().has("tsume")) {
           this.tsumeStack = new ItemStack((ItemLike) Objects.requireNonNull((Item) ForgeRegistries.ITEMS.getValue(ResourceLocation.parse(jsonElement.getAsJsonObject().get("tsume").getAsString()))));
       }else {
           this.tsumeStack = new ItemStack((ItemLike) WohItems.TSUME.get());
       }
    }

    static {
        HAND_INWARD_OFFSETS.put(EntityType.PLAYER, 0F);
        HAND_INWARD_OFFSETS.put(EntityType.ZOMBIE_VILLAGER, 0.035f);
    };

    @Override
    public void renderItemInHand(ItemStack stack, LivingEntityPatch<?> entitypatch, InteractionHand hand, OpenMatrix4f[] poses, MultiBufferSource buffer, PoseStack poseStack, int packedLight, float partialTicks) {

        OpenMatrix4f modelMatrixMainHand = this.getCorrectionMatrix(entitypatch, InteractionHand.MAIN_HAND, poses);
        OpenMatrix4f modelMatrixOffHand = this.getCorrectionMatrix(entitypatch, InteractionHand.OFF_HAND, poses);

        float finalOffset = HAND_INWARD_OFFSETS.getOrDefault(entitypatch.getOriginal().getType(), 0F);

        ItemStack tsumeItem = tsumeStack;


        poseStack.pushPose();
        poseStack.translate(finalOffset, 0.0F, 0.0F);

        MathUtils.mulStack(poseStack, modelMatrixMainHand);
        itemRenderer.renderStatic(tsumeItem, ItemDisplayContext.THIRD_PERSON_RIGHT_HAND, packedLight, OverlayTexture.NO_OVERLAY, poseStack, buffer, (Level) null, 0);
        poseStack.popPose();


        poseStack.pushPose();
        poseStack.translate(-finalOffset, 0.0F, 0.0F);

        MathUtils.mulStack(poseStack, modelMatrixOffHand);
        itemRenderer.renderStatic(tsumeItem, ItemDisplayContext.THIRD_PERSON_LEFT_HAND, packedLight, OverlayTexture.NO_OVERLAY, poseStack, buffer, (Level) null, 0);
        poseStack.popPose();
        super.renderItemInHand(stack, entitypatch, hand, poses, buffer, poseStack, packedLight, partialTicks);
    }

}
