package net.kenji.woh.client.baked_models;

import com.mojang.blaze3d.vertex.PoseStack;
import net.kenji.woh.api.item_overrides.WeaponModelOverrides;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraftforge.client.model.BakedModelWrapper;
import org.jline.utils.Log;

import java.util.Map;

public class SwordBakedModel extends BakedModelWrapper<BakedModel> {

    private final WeaponModelOverrides overrides;
    private final Map<ItemDisplayContext, BakedModel> perspectives; // gui/ground/fixed models

    public SwordBakedModel(BakedModel wrapped, WeaponModelOverrides overrides,
                           Map<ItemDisplayContext, BakedModel> perspectives) {
        super(wrapped);
        this.overrides = overrides;
        this.perspectives = perspectives;
    }

    @Override
    public BakedModel applyTransform(ItemDisplayContext ctx, PoseStack poseStack, boolean applyLeftHandTransform) {
        if (perspectives.containsKey(ctx)) {
            BakedModel result = perspectives.get(ctx).applyTransform(ctx, poseStack, applyLeftHandTransform);
            return result;
        }
        return super.applyTransform(ctx, poseStack, applyLeftHandTransform);
    }

    @Override
    public ItemOverrides getOverrides() {
        return overrides;
    }
}