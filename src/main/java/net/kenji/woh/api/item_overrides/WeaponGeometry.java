package net.kenji.woh.api.item_overrides;

import net.kenji.woh.WeaponsOfHarmony;
import net.kenji.woh.client.baked_models.SwordBakedModel;
import net.minecraft.client.renderer.block.model.BlockModel;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ModelEvent;
import net.minecraftforge.client.model.ForgeFaceData;
import net.minecraftforge.client.model.ItemLayerModel;
import net.minecraftforge.client.model.geometry.IGeometryBakingContext;
import net.minecraftforge.client.model.geometry.IUnbakedGeometry;
import net.minecraftforge.client.model.obj.ObjLoader;
import net.minecraftforge.client.model.obj.ObjModel;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jline.utils.Log;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class WeaponGeometry implements IUnbakedGeometry<WeaponGeometry> {

    private final ResourceLocation defaultObjPath;
    private final ResourceLocation guiTexture; // was guiModelLocation

    public WeaponGeometry(ResourceLocation defaultObjPath, @Nullable ResourceLocation guiTexture) {
        this.defaultObjPath = defaultObjPath;
        this.guiTexture = guiTexture;
    }

    @Override
    public BakedModel bake(IGeometryBakingContext context, ModelBaker baker,
                           Function<Material, TextureAtlasSprite> spriteGetter,
                           ModelState modelState, ItemOverrides overrides, ResourceLocation modelLocation) {

        ObjModel objModel = ObjLoader.INSTANCE.loadModel(new ObjModel.ModelSettings(
                defaultObjPath,
                true,
                true,
                true,
                true,
                null
        ));
        BakedModel baseModel = objModel.bake(context, baker, spriteGetter, modelState, overrides, modelLocation);

        // Bake flat 2D perspectives if a gui model was specified in JSON
        Map<ItemDisplayContext, BakedModel> perspectives = new HashMap<>();
        if (guiTexture != null) {
            // Build the unbaked model with the texture, then resolve parent via baker
            BlockModel guiUnbaked = BlockModel.fromString(
                    "{\"parent\":\"item/generated\",\"textures\":{\"layer0\":\"" + guiTexture + "\"}}"
            );
            guiUnbaked.name = modelLocation + "_gui";

            // Resolve the parent chain manually
            guiUnbaked.resolveParents(baker::getModel);

            BakedModel guiModel = guiUnbaked.bake(baker, spriteGetter, modelState, modelLocation);
            if (guiModel != null) {
                perspectives.put(ItemDisplayContext.GUI, guiModel);
                perspectives.put(ItemDisplayContext.GROUND, guiModel);
                perspectives.put(ItemDisplayContext.FIXED, guiModel);
            }
        }

        WeaponModelOverrides customOverrides = new WeaponModelOverrides(context, baker, spriteGetter, modelState, modelLocation, this.defaultObjPath);
        return new SwordBakedModel(baseModel, customOverrides, perspectives);
    }

}