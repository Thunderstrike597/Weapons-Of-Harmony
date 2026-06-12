package net.kenji.woh.api.item_overrides;

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
    private final ResourceLocation modelLocation;

    public WeaponModelOverrides(IGeometryBakingContext context, ModelBaker baker,
                                Function<Material, TextureAtlasSprite> spriteGetter,
                                ModelState modelState, ResourceLocation modelLocation) {
        this.context = context;
        this.baker = baker;
        this.spriteGetter = spriteGetter;
        this.modelState = modelState;
        this.modelLocation = modelLocation;
    }

    @Override
    @Nullable
    public BakedModel resolve(BakedModel baseModel, ItemStack stack,
                              @Nullable ClientLevel level, @Nullable LivingEntity entity, int seed) {

        String variant = getVariantFromStack(stack);

        // No tag set — return the default base model as-is
        if (variant.isEmpty()) {
            return baseModel;
        }

        ResourceLocation modelId = ResourceLocation.fromNamespaceAndPath("woh", "models/item/obj/variants/" + variant + ".obj");
        return modelCache.computeIfAbsent(modelId, this::bakeObjModel);
    }
    private BakedModel bakeObjModel(ResourceLocation objPath) {
        ObjModel objModel = ObjLoader.INSTANCE.loadModel(
                new ObjModel.ModelSettings(
                        objPath,
                        true,   // flip_v — match your JSON setting
                        true,
                        true,
                        true,
                        null
                )
        );
        return objModel.bake(context, baker, spriteGetter, modelState, ItemOverrides.EMPTY, modelLocation);
    }

    private String getVariantFromStack(ItemStack stack) {
        // Read from NBT / DataComponents / capability
        CompoundTag tag = stack.getOrCreateTag();
        return tag.getString("weapon_variant"); // fallback to default if empty
    }
}