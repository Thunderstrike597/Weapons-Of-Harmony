package net.kenji.woh.gameasset;

import net.kenji.woh.api.basegameassets.condition.CooldownCounterCondition;
import net.kenji.woh.api.basegameassets.condition.InAirCondition;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import yesman.epicfight.data.conditions.Condition;

import java.util.function.Supplier;

public class WohConditions {
    public static final DeferredRegister<Supplier<Condition<?>>> CONDITIONS = DeferredRegister.create(ResourceLocation.fromNamespaceAndPath("epicfight", "conditions"), "woh");
    public static final RegistryObject<Supplier<Condition<?>>> AIR_CONDITION;

    static {
        AIR_CONDITION = CONDITIONS.register("in_air", () -> InAirCondition::new);
    }
}
