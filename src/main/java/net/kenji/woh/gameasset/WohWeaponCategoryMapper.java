package net.kenji.woh.gameasset;

import net.minecraft.world.item.Item;
import yesman.epicfight.skill.guard.GuardSkill;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.capabilities.item.WeaponCategory;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class WohWeaponCategoryMapper {

    private static final Map<WohWeaponCategories, WeaponCategory> CATEGORY_MAP = new HashMap<>();

    static {
        CATEGORY_MAP.put(WohWeaponCategories.SHOTOGATANA, CapabilityItem.WeaponCategories.TACHI);
        CATEGORY_MAP.put(WohWeaponCategories.WAKIZASHI, CapabilityItem.WeaponCategories.SWORD);
        CATEGORY_MAP.put(WohWeaponCategories.ODACHI, CapabilityItem.WeaponCategories.GREATSWORD);
    }

    public static CapabilityItem.Builder apply(Item item, WohWeaponCategories category) {
        WeaponCategory base = CATEGORY_MAP.getOrDefault(category, category);
        return ((Function<Item, CapabilityItem.Builder>) base).apply(item);
    }
}
