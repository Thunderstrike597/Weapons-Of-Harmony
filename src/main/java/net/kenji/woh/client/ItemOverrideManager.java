package net.kenji.woh.client;

import net.minecraft.world.item.ItemStack;

public class ItemOverrideManager {
    public static final String MODEL_OVERRIDE_TAG = "weapon_model_override";
    public static final String TEXTURE_OVERRIDE_TAG = "weapon_texture_override";

    public static void setItemTextureVariant(ItemStack stack, String variant){
        stack.getOrCreateTag().putString(TEXTURE_OVERRIDE_TAG, variant);
    }
    public static void setItemModelVariant(ItemStack stack, String variant){
        stack.getOrCreateTag().putString(MODEL_OVERRIDE_TAG, variant);
    }
}
