package net.kenji.woh;

import net.minecraftforge.common.ForgeConfigSpec;

public class WohConfigCommon {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    public static ForgeConfigSpec.ConfigValue<Double> RONIN_SKELETON_SPAWN_CHANCE;
    public static ForgeConfigSpec.ConfigValue<Double> RONIN_SKELETON_SURRENDER_CHANCE;



    static {
        BUILDER.push("Chance Values");

        RONIN_SKELETON_SPAWN_CHANCE = BUILDER
                .comment("Ronin Skeleton Spawn Chance")
                .defineInRange("The chance for a skeleton to spawn as a ronin skeleton", 0.175f, 0.0f, 1.0f);


        RONIN_SKELETON_SURRENDER_CHANCE = BUILDER
                .comment("Ronin Skeleton Surrender Chance")
                .defineInRange("The chance for a ronin skeleton to surrender when low on health", 0.36f, 0.0f, 1.0f);
        BUILDER.pop();

        SPEC = BUILDER.build();
    }
}
