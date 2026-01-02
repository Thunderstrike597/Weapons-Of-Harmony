package net.kenji.woh.registry;

import net.minecraft.util.LazyLoadedValue;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;

import java.util.function.Supplier;

public enum WohWeaponTiers implements Tier {
    SHOTOGATANA(4, 2200, 9.0F, 7.0F, 22, () -> Ingredient.of(new ItemLike[]{WohItems.FOLDED_STEEL.get(), WohItems.BROKEN_BLADE_AND_SHEATH.get()})),
    TESSEN(4, 1750, 9.0F, 4.0F, 18, () -> Ingredient.of(new ItemLike[]{WohItems.FOLDED_STEEL.get()})),
    TSUME(4, 1750, 9.0F, 5.0F, 18, () -> Ingredient.of(new ItemLike[]{WohItems.FOLDED_STEEL.get()})),

    WAKIZASHI(4, 1750, 9.0F, 5.0F, 18, () -> Ingredient.of(new ItemLike[]{WohItems.FOLDED_STEEL.get()})),
    ODACHI(4, 1750, 9.0F, 11.0F, 18, () -> Ingredient.of(new ItemLike[]{WohItems.FOLDED_STEEL.get()}));

    private final int harvestLevel;
    private final int maxUses;
    private final float efficiency;
    private final float attackDamage;
    private final int enchantability;
    private final LazyLoadedValue<Ingredient> repairMaterial;


    WohWeaponTiers(int harvestLevelIn, int maxUsesIn, float efficiencyIn, float attackDamageIn, int enchantabilityIn, Supplier<Ingredient> repairMaterialIn) {
       this.harvestLevel = harvestLevelIn;
       this.maxUses = maxUsesIn;
       this.efficiency = efficiencyIn;
       this.attackDamage = attackDamageIn;
       this.enchantability = enchantabilityIn;
       this.repairMaterial = new LazyLoadedValue(repairMaterialIn);
   }


    @Override
    public int getUses() {
        return this.maxUses;
    }

    @Override
    public float getSpeed() {
        return this.efficiency;
    }

    @Override
    public float getAttackDamageBonus() {
        return attackDamage;
    }

    @Override
    public int getLevel() {
        return this.harvestLevel;
    }

    @Override
    public int getEnchantmentValue() {
        return this.enchantability;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return this.repairMaterial.get();
    }
}
