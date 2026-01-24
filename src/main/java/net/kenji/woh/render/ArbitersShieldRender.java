package net.kenji.woh.render;

import com.mojang.blaze3d.vertex.PoseStack;
import net.kenji.woh.WeaponsOfHarmony;
import net.kenji.woh.item.custom.base.WohShieldItem;
import net.kenji.woh.registry.WohItems;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.common.Mod;
import yesman.epicfight.api.utils.math.OpenMatrix4f;
import yesman.epicfight.client.renderer.patched.item.RenderItemBase;
import yesman.epicfight.model.armature.HumanoidArmature;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;

@Mod.EventBusSubscriber(modid = WeaponsOfHarmony.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ArbitersShieldRender extends RenderItemBase {
    private final ItemStack shieldStack;

    public ArbitersShieldRender() {
        this.shieldStack = new ItemStack((ItemLike) WohItems.ARBITERS_SHIELD.get());
    }


    @Override
    public void renderItemInHand(
            ItemStack stack,
            LivingEntityPatch<?> entitypatch,
            InteractionHand hand,
            HumanoidArmature armature,
            OpenMatrix4f[] poses,
            MultiBufferSource buffer,
            PoseStack poseStack,
            int packedLight,
            float partialTicks
    ) {

        if (entitypatch instanceof PlayerPatch<?> playerPatch) {
            if (stack.getItem() instanceof WohShieldItem shieldItem) {
                if (shieldItem.shouldRenderInHand(playerPatch, WohItems.ARBITERS_BLADE.get())) {
                    super.renderItemInHand(
                            stack,
                            playerPatch,
                            InteractionHand.OFF_HAND,
                            armature,
                            poses,
                            buffer,
                            poseStack,
                            packedLight,
                            partialTicks
                    );
                }
            }
        }
    }
}
