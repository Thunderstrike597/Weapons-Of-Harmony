package net.kenji.woh;

import net.minecraftforge.common.ForgeConfigSpec;

public class WohConfigCommon {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    public static ForgeConfigSpec.ConfigValue<Double> RONIN_SKELETON_SPAWN_CHANCE;



    static {
        BUILDER.push("Spawn Values");

        RONIN_SKELETON_SPAWN_CHANCE = BUILDER
                .comment("Ronin Skeleton Spawn Chance")
                .defineInRange("The chance for a skeleton to spawn as a ronin skeleton", 0.08f, 0.0f, 1.0f);

        BUILDER.pop();

        SPEC = BUILDER.build();
    }
}
