package net.kenji.woh.render;

import com.google.gson.JsonElement;
import com.mojang.blaze3d.vertex.PoseStack;
import net.kenji.woh.WeaponsOfHarmony;
import net.kenji.woh.item.custom.base.WohShieldItem;
import net.kenji.woh.registry.WohItems;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import yesman.epicfight.api.utils.math.OpenMatrix4f;
import yesman.epicfight.client.renderer.patched.item.RenderItemBase;
import yesman.epicfight.model.armature.HumanoidArmature;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;

import java.util.Objects;

@Mod.EventBusSubscriber(modid = WeaponsOfHarmony.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ArbitersShieldRender extends RenderItemBase {
    private final ItemStack shieldStack;

    public ArbitersShieldRender(JsonElement jsonElement) {
        super(jsonElement);
        if (jsonElement.getAsJsonObject().has("arbiters_shield")) {
            this.shieldStack = new ItemStack((ItemLike) Objects.requireNonNull((Item) ForgeRegistries.ITEMS.getValue(ResourceLocation.parse(jsonElement.getAsJsonObject().get("arbiters_shield").getAsString()))));
        } else {
            this.shieldStack = new ItemStack((ItemLike) WohItems.ARBITERS_SHIELD.get());
        }
    }



    @Override
    public void renderItemInHand(
            ItemStack stack,
            LivingEntityPatch<?> entitypatch,
            InteractionHand hand,
            OpenMatrix4f[] poses,
            MultiBufferSource buffer,
            PoseStack poseStack,
            int packedLight,
            float partialTicks
    ) {

        if(entitypatch instanceof PlayerPatch<?> playerPatch) {
            if (stack.getItem() instanceof WohShieldItem shieldItem) {
                if (shieldItem.shouldRender(playerPatch.getOriginal(), WohItems.ARBITERS_BLADE.get()))
                    super.renderItemInHand(
                            stack,
                            playerPatch,
                            InteractionHand.OFF_HAND,
                            poses,
                            buffer,
                            poseStack,
                            packedLight,
                            partialTicks
                    );
                return;
            }
        }
        poseStack.popPose();
    }
}
