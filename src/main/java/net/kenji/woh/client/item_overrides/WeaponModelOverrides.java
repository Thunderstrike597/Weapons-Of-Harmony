package net.kenji.woh.client.item_overrides;

import net.kenji.woh.WeaponsOfHarmony;
import net.kenji.woh.client.ItemOverrideManager;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.*;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.model.geometry.IGeometryBakingContext;
import net.minecraftforge.client.model.obj.ObjLoader;
import net.minecraftforge.client.model.obj.ObjModel;
import org.jline.utils.Log;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class WeaponModelOverrides extends ItemOverrides {

    private final Map<ResourceLocation, BakedModel> modelCache = new HashMap<>();

    // Store everything needed to re-bake on demand
    private final IGeometryBakingContext context;
    private final ModelBaker baker;
    private final Function<Material, TextureAtlasSprite> spriteGetter;
    private final ModelState modelState;
    private final ResourceLocation guiModelLocation;

    private final ResourceLocation defaultObjLocation;

    public WeaponModelOverrides(IGeometryBakingContext context, ModelBaker baker,
                                Function<Material, TextureAtlasSprite> spriteGetter,
                                ModelState modelState, ResourceLocation guiModelLocation, ResourceLocation defaultObjLocation) {
        this.context = context;
        this.baker = baker;
        this.spriteGetter = spriteGetter;
        this.modelState = modelState;
        this.guiModelLocation = guiModelLocation;
        this.defaultObjLocation = defaultObjLocation;
    }

    @Override
    @Nullable
    public BakedModel resolve(BakedModel baseModel, ItemStack stack,
                              @Nullable ClientLevel level, @Nullable LivingEntity entity, int seed) {

        String variant = getModelVariantFromStack(stack);
        String mltVariant = getTextureVariantFromStack(stack);
        // No tag set — return the default base model as-is
        if (variant.isEmpty() && mltVariant.isEmpty()) {
            return baseModel;
        }

        ResourceLocation modelLoc = ResourceLocation.fromNamespaceAndPath(WeaponsOfHarmony.MODID, "models/item/obj/variants/" + variant + ".obj");
        ResourceLocation modelId = variant.isEmpty() ? this.defaultObjLocation : modelLoc;
        String mltId = mltVariant.isEmpty() ? null : mltVariant;
        return modelCache.computeIfAbsent(modelId, (location) -> bakeObjModel(location, mltId));
    }
    private BakedModel bakeObjModel(ResourceLocation objPath, @Nullable String mltVariant) {
        String mtlOverride = (mltVariant == null) ? null
                : WeaponsOfHarmony.MODID + ":models/item/obj/variants/mtl/" + mltVariant + ".mtl";
        Log.info("Attempting mtl override at: " + mtlOverride); // confirm exact path
        ObjModel objModel = ObjLoader.INSTANCE.loadModel(
                new ObjModel.ModelSettings(objPath, true, true, true, true, mtlOverride)
        );
        return objModel.bake(context, baker, spriteGetter, modelState, ItemOverrides.EMPTY, guiModelLocation);
    }


    private String getModelVariantFromStack(ItemStack stack) {
        // Read from NBT / DataComponents / capability
        CompoundTag tag = stack.getOrCreateTag();
        return tag.getString(ItemOverrideManager.MODEL_OVERRIDE_TAG); // fallback to default if empty
    }
    private String getTextureVariantFromStack(ItemStack stack) {
        // Read from NBT / DataComponents / capability
        CompoundTag tag = stack.getOrCreateTag();
        return tag.getString(ItemOverrideManager.TEXTURE_OVERRIDE_TAG); // fallback to default if empty
    }
}