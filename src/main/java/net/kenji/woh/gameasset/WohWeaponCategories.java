package net.kenji.woh.gameasset;

import net.minecraft.world.item.Item;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.capabilities.item.WeaponCategory;

import java.util.function.Function;

public enum WohWeaponCategories implements WeaponCategory, Function<Item, CapabilityItem.Builder> {
    SHOTOGATANA,
    TESSEN,
    TSUME,
    ARBITERS_BLADE,
    WAKIZASHI,
    ODACHI;
    final int id;


    WohWeaponCategories() {
        this.id = WeaponCategory.ENUM_MANAGER.assign(this);
    }


    @Override
    public int universalOrdinal() {
        return this.id;
    }

    @Override
    public CapabilityItem.Builder apply(Item item) {
        return WohWeaponCategoryMapper.apply(item, this);
    }
}
