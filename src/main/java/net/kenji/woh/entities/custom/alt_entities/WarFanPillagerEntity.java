package net.kenji.woh.entities.custom.alt_entities;

import net.kenji.woh.WohConfigCommon;
import net.kenji.woh.registry.WohItems;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.monster.Pillager;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class WarFanPillagerEntity extends Pillager {
    public WarFanPillagerEntity(EntityType<? extends Pillager> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }
    public static final String WAR_FAN_OFFHAND_TAG = "woh_war_fan_pillager_offhand";


    @Override
    public void onAddedToWorld() {
        super.onAddedToWorld();
        double offhandChance = Math.random();
        this.setItemSlot(
                EquipmentSlot.MAINHAND,
                new ItemStack(WohItems.TESSEN.get())
        );
        this.setItemSlot(
                EquipmentSlot.HEAD,
                new ItemStack(WohItems.METAL_RONIN_HEADWEAR.get())
        );
        if(offhandChance < WohConfigCommon.WAR_FAN_PILLAGER_OFFHAND_CHANCE.get()) {
            this.setItemSlot(
                    EquipmentSlot.OFFHAND,
                    new ItemStack(WohItems.TESSEN.get())
            );
            this.getPersistentData().putBoolean(WAR_FAN_OFFHAND_TAG, true);
        }
        this.setDropChance(EquipmentSlot.MAINHAND, 0.0f);

    }
}
