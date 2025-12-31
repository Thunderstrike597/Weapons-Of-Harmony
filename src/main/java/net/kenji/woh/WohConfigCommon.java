package net.kenji.woh;

import net.minecraftforge.common.ForgeConfigSpec;

public class WohConfigCommon {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    public static ForgeConfigSpec.ConfigValue<Double> RONIN_SKELETON_SPAWN_CHANCE;
    public static ForgeConfigSpec.ConfigValue<Double> WAR_FAN_PILLAGER_SPAWN_CHANCE;
    public static ForgeConfigSpec.ConfigValue<Double> CLAWED_ZOMBIE_VILLAGER_SPAWN_CHANCE;

    public static ForgeConfigSpec.ConfigValue<Double> RONIN_SKELETON_SURRENDER_CHANCE;
    public static ForgeConfigSpec.ConfigValue<Double> WAR_FAN_PILLAGER_OFFHAND_CHANCE;

    static {
        BUILDER.push("Chance Values");

        RONIN_SKELETON_SPAWN_CHANCE = BUILDER
                .comment("Ronin Skeleton Spawn Chance")
                .defineInRange("The chance for a skeleton to spawn as a ronin skeleton", 0.18f, 0.0f, 1.0f);

        WAR_FAN_PILLAGER_SPAWN_CHANCE = BUILDER
                .comment("War Fan Pillager Spawn Chance")
                .defineInRange("The chance for a pillager to spawn as a war fan pillager", 0.2f, 0.0f, 1.0f);

        CLAWED_ZOMBIE_VILLAGER_SPAWN_CHANCE = BUILDER
                .comment("Clawed Zombie Villager Spawn Chance")
                .defineInRange("The chance for a zombie villager to spawn as a clawed zombie villager", 0.25f, 0.0f, 1.0f);


        RONIN_SKELETON_SURRENDER_CHANCE = BUILDER
                .comment("Ronin Skeleton Surrender Chance")
                .defineInRange("The chance for a ronin skeleton to surrender when low on health", 0.36f, 0.0f, 1.0f);
        WAR_FAN_PILLAGER_OFFHAND_CHANCE = BUILDER
                .comment("War Fan Pillager OffHand Chance")
                .defineInRange("The chance for a War Fan Pillager to spawn dual wielding tessen's", 0.45f, 0.0f, 1.0f);


        BUILDER.pop();

        SPEC = BUILDER.build();
    }
}
