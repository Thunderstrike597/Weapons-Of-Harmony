package net.kenji.woh;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.ForgeConfigSpec;

import java.util.List;

public class WohConfigClient {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    public static final ForgeConfigSpec.ConfigValue<List<? extends String>> HOLSTER_HIDDEN_ITEMS;

    static {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
        builder.push("holster");

        HOLSTER_HIDDEN_ITEMS = builder
                .comment("Items that should render holstered weapons")
                .defineList(
                        "hidden_items",
                        List.of(),
                        o -> o instanceof String s && ResourceLocation.isValidResourceLocation(s)
                );

        builder.pop();
        SPEC = builder.build();
    }
}
