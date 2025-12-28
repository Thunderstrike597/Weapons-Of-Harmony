package net.kenji.woh.gameasset;

import net.minecraft.world.item.Item;
import yesman.epicfight.skill.guard.GuardSkill;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.capabilities.item.WeaponCategory;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class WohWeaponCategoryMapper {
    private static final Map<WohWeaponCategories, WeaponCategory> categoryMap = new HashMap();

    public static CapabilityItem.Builder apply(Item item, WohWeaponCategories category) {
        WeaponCategory mappedCategory = categoryMap.getOrDefault(category, category);
        try {
            Method applyMethod = mappedCategory.getClass().getMethod("apply", Item.class);
            return (CapabilityItem.Builder)applyMethod.invoke(mappedCategory, item);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    static {
        categoryMap.put(WohWeaponCategories.SHOTOGATANA, CapabilityItem.WeaponCategories.UCHIGATANA);
        categoryMap.put(WohWeaponCategories.WAKIZASHI, CapabilityItem.WeaponCategories.SWORD);

    }
}
