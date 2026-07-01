package net.kenji.woh.render;

import com.google.gson.JsonElement;
import com.mojang.blaze3d.vertex.PoseStack;
import net.kenji.woh.WeaponsOfHarmony;
import net.kenji.woh.gameasset.WohSkills;
import net.kenji.woh.item.custom.base.HolsterWeaponBase;
import net.kenji.woh.item.custom.weapon.ArbitersBlade;
import net.kenji.woh.registry.WohItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
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
        if (jsonElement.getAsJsonObject().has("arbiters_blade")) {
            this.bladeStack = new ItemStack((ItemLike) Objects.requireNonNull((Item) ForgeRegistries.ITEMS.getValue(ResourceLocation.parse(jsonElement.getAsJsonObject().get("arbiters_blade").getAsString()))));
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



    @Override
    public void renderItemInHand(ItemStack stack, LivingEntityPatch<?> entitypatch, InteractionHand hand, OpenMatrix4f[] poses, MultiBufferSource buffer, PoseStack poseStack, int packedLight, float partialTicks) {
        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
        BakedModel model = itemRenderer.getModel(stack, entitypatch.getOriginal().level(), entitypatch.getOriginal(), 0);
        if(stack.getItem() instanceof ArbitersBlade arbitersBlade){
            if(entitypatch instanceof PlayerPatch<?> playerPatch) {
                if (!arbitersBlade.shouldRenderInHand(playerPatch)){
                    return;
                }
            }
        }
        // getModel() calls resolve() internally — but let's also call it explicitly
        // in case Epic Fight's pipeline skips it
        BakedModel resolvedModel = model.getOverrides().resolve(
                model,
                stack,
                (ClientLevel) entitypatch.getOriginal().level(),
                entitypatch.getOriginal(),
                0
        );

        OpenMatrix4f modelMatrix = this.getCorrectionMatrix(entitypatch, InteractionHand.MAIN_HAND, poses);
        poseStack.pushPose();
        MathUtils.mulStack(poseStack, modelMatrix);
        if(resolvedModel == null){
            poseStack.popPose();
            return;
        }
        Minecraft.getInstance().getItemRenderer().render(
                bladeStack,  // pass the real stack — resolve() will run against this
                ItemDisplayContext.THIRD_PERSON_RIGHT_HAND,
                false,
                poseStack,
                buffer,
                packedLight,
                OverlayTexture.NO_OVERLAY,
                resolvedModel
        );
        poseStack.popPose();
    }
}
