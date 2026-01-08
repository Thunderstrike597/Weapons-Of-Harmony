package net.kenji.woh.events.loot;

import net.kenji.woh.WeaponsOfHarmony;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import yesman.epicfight.api.forgeevent.SkillLootTableRegistryEvent;
import yesman.epicfight.config.CommonConfig;
import yesman.epicfight.data.loot.function.SetSkillFunction;
import yesman.epicfight.world.item.EpicFightItems;

@Mod.EventBusSubscriber(modid = WeaponsOfHarmony.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)

public class WohLootDrops {
    @SubscribeEvent
    public static void SkillLootDrop(SkillLootTableRegistryEvent event) {
        int modifier = (Integer) CommonConfig.SKILL_BOOK_MOB_DROP_CHANCE_MODIFIER.get();
        int dropChance = 100 + modifier;
        int antiDropChance = 100 - modifier;
        float dropChanceModifier = antiDropChance == 0 ? Float.MAX_VALUE : (float) dropChance / (float) antiDropChance;
        event.add(EntityType.ZOMBIE,
                LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1.0F))
                        .when(LootItemRandomChanceCondition
                                .randomChance(0.025F * dropChanceModifier))
                        .add(LootItem.lootTableItem((ItemLike) EpicFightItems.SKILLBOOK.get())
                                .apply(SetSkillFunction.builder(
                                        1.0F, "woh:katajutsu"))))
                .add(EntityType.SKELETON,
                        LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0F))
                                .when(LootItemRandomChanceCondition
                                        .randomChance(0.025F * dropChanceModifier))
                                .add(LootItem.lootTableItem((ItemLike) EpicFightItems.SKILLBOOK.get())
                                        .apply(SetSkillFunction.builder(
                                                1.0F, "woh:katajutsu"))))
                .add(EntityType.SPIDER,
                        LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0F))
                                .when(LootItemRandomChanceCondition
                                        .randomChance(0.0275F * dropChanceModifier))
                                .add(LootItem.lootTableItem((ItemLike) EpicFightItems.SKILLBOOK.get())
                                        .apply(SetSkillFunction.builder(
                                                1.0F, "woh:katajutsu"))))
                .add(EntityType.WITCH,
                        LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0F))
                                .when(LootItemRandomChanceCondition
                                        .randomChance(0.0375F * dropChanceModifier))
                                .add(LootItem.lootTableItem((ItemLike) EpicFightItems.SKILLBOOK.get())
                                        .apply(SetSkillFunction.builder(
                                                1.0F, "woh:katajutsu"))))
                .add(EntityType.EVOKER,
                        LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0F))
                                .when(LootItemRandomChanceCondition
                                        .randomChance(0.1F * dropChanceModifier))
                                .add(LootItem.lootTableItem((ItemLike) EpicFightItems.SKILLBOOK.get())
                                        .apply(SetSkillFunction.builder(
                                                1.0F, "woh:katajutsu"))))
                .add(EntityType.GHAST,
                        LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0F))
                                .when(LootItemRandomChanceCondition
                                        .randomChance(0.12F * dropChanceModifier))
                                .add(LootItem.lootTableItem((ItemLike) EpicFightItems.SKILLBOOK.get())
                                        .apply(SetSkillFunction.builder(
                                                1.0F, "woh:katajutsu"))));
    }
}
