package net.kenji.woh.events.loot;

import net.kenji.woh.WeaponsOfHarmony;
import net.kenji.woh.registry.WohItems;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import yesman.epicfight.data.loot.function.SetSkillFunction;
import yesman.epicfight.world.item.EpicFightItems;

@Mod.EventBusSubscriber(modid = WeaponsOfHarmony.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class WohLootTables {

    // ===== BALANCE CONSTANTS =====
    private static final float CLOTH_STACK_CHANCE = 0.175F; // 2–3 cloth
    private static final float CLOTH_SINGLE_CHANCE = 0.08F; // 1 cloth

    private static final float ARBITER_ARMOR_CHANCE = 0.025F;
    private static final float ARBITER_SHIELD_CHANCE = 0.015F;

    @SubscribeEvent
    public static void modifyVanillaLootPools(LootTableLoadEvent event) {

        // === MYSTERIOUS CLOTH (stack-biased) ===
        addCloth(event, BuiltInLootTables.VILLAGE_ARMORER);
        addCloth(event, BuiltInLootTables.VILLAGE_SHEPHERD);
        addCloth(event, BuiltInLootTables.DESERT_PYRAMID);
        addCloth(event, BuiltInLootTables.STRONGHOLD_LIBRARY);
        addCloth(event, BuiltInLootTables.STRONGHOLD_CORRIDOR);
        addCloth(event, BuiltInLootTables.STRONGHOLD_CROSSING);
        addCloth(event, BuiltInLootTables.SHIPWRECK_TREASURE);
        addCloth(event, BuiltInLootTables.JUNGLE_TEMPLE);
        addCloth(event, BuiltInLootTables.WOODLAND_MANSION);

        // === ARBITER ARMOR ===
        addArmor(event, BuiltInLootTables.STRONGHOLD_LIBRARY,
                WohItems.ARBITERS_TUNIC.get());

        addArmor(event, BuiltInLootTables.STRONGHOLD_CORRIDOR,
                WohItems.ARBITERS_LEGGINGS.get());

        addArmor(event, BuiltInLootTables.STRONGHOLD_CROSSING,
                WohItems.ARBITERS_BOOTS.get());

        addArmor(event, BuiltInLootTables.WOODLAND_MANSION,
                WohItems.ARBITERS_TUNIC.get(),
                WohItems.ARBITERS_LEGGINGS.get(),
                WohItems.ARBITERS_BOOTS.get());

        // === ARBITER SHIELD (rarest) ===
        addShield(event, BuiltInLootTables.STRONGHOLD_CORRIDOR, 0.1F);
        addShield(event, BuiltInLootTables.JUNGLE_TEMPLE, 0.125F);
        addShield(event, BuiltInLootTables.WOODLAND_MANSION, 0.115F);
        addShield(event, BuiltInLootTables.VILLAGE_ARMORER, 0.115F);
        addShield(event, BuiltInLootTables.VILLAGE_WEAPONSMITH, 0.115F);
        addShield(event, BuiltInLootTables.PILLAGER_OUTPOST, 0.16F);
        addShield(event, BuiltInLootTables.ANCIENT_CITY, 0.2F);
        addShield(event, BuiltInLootTables.SIMPLE_DUNGEON, 0.1F);
        addShield(event, BuiltInLootTables.BURIED_TREASURE, 0.08F);
        addShield(event, BuiltInLootTables.WEAPONSMITH_GIFT, 0.4F);

        addSkillBook(event, BuiltInLootTables.DESERT_PYRAMID, new String[]{"woh:katajutsu"}, 0.035F);
        addSkillBook(event, BuiltInLootTables.STRONGHOLD_LIBRARY, new String[]{"woh:katajutsu"}, 0.05F);
        addSkillBook(event, BuiltInLootTables.BASTION_TREASURE, new String[]{"woh:katajutsu"}, 0.04F);
        addSkillBook(event, BuiltInLootTables.SIMPLE_DUNGEON, new String[]{"woh:katajutsu"}, 0.045F);
        addSkillBook(event, BuiltInLootTables.ANCIENT_CITY, new String[]{"woh:katajutsu"}, 0.03F);
        addSkillBook(event, BuiltInLootTables.PILLAGER_OUTPOST, new String[]{"woh:katajutsu"}, 0.028F);
    }

    // ===== HELPERS =====

    private static void addCloth(LootTableLoadEvent event, net.minecraft.resources.ResourceLocation table) {
        if (!event.getName().equals(table)) return;

        // 2–3 cloth (most common outcome)
        event.getTable().addPool(
                LootPool.lootPool()
                        .when(LootItemRandomChanceCondition.randomChance(CLOTH_STACK_CHANCE))
                        .add(LootItem.lootTableItem(WohItems.MYSTERIOUS_CLOTH.get())
                                .apply(SetItemCountFunction.setCount(
                                        UniformGenerator.between(2.0F, 3.0F))))
                        .build()
        );

        // Single cloth (rarer)
        event.getTable().addPool(
                LootPool.lootPool()
                        .when(LootItemRandomChanceCondition.randomChance(CLOTH_SINGLE_CHANCE))
                        .add(LootItem.lootTableItem(WohItems.MYSTERIOUS_CLOTH.get()))
                        .build()
        );
    }

    private static void addArmor(LootTableLoadEvent event,
                                 net.minecraft.resources.ResourceLocation table,
                                 net.minecraft.world.level.ItemLike... items) {

        if (!event.getName().equals(table)) return;

        for (var item : items) {
            event.getTable().addPool(
                    LootPool.lootPool()
                            .when(LootItemRandomChanceCondition.randomChance(ARBITER_ARMOR_CHANCE))
                            .add(LootItem.lootTableItem(item))
                            .build()
            );
        }
    }

    private static void addShield(LootTableLoadEvent event,
                                  net.minecraft.resources.ResourceLocation table,
                                  float chance) {

        if (!event.getName().equals(table)) return;

        event.getTable().addPool(
                LootPool.lootPool()
                        .when(LootItemRandomChanceCondition.randomChance(chance))
                        .add(LootItem.lootTableItem(WohItems.ARBITERS_SHIELD.get()))
                        .build()
        );
    }
    private static void addSkillBook(LootTableLoadEvent event,
                                     net.minecraft.resources.ResourceLocation table,
                                     String[] identifiers,
                                     float chance) {

        if (!event.getName().equals(table)) return;
        for (var identifier : identifiers) {
            event.getTable().addPool(
                    LootPool.lootPool()
                            .when(LootItemRandomChanceCondition.randomChance(chance))
                            .add(LootItem.lootTableItem((ItemLike) EpicFightItems.SKILLBOOK.get())
                                    .apply(SetSkillFunction.builder(
                                            1.0F, identifier)))
                            .build()
            );
        }
    }
}
