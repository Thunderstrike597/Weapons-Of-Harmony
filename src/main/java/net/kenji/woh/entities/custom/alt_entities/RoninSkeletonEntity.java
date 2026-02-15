package net.kenji.woh.entities.custom.alt_entities;

import net.kenji.woh.registry.WohItems;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class RoninSkeletonEntity extends Skeleton {
    public RoninSkeletonEntity(EntityType<? extends Skeleton> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Override
    public void onAddedToWorld() {
        super.onAddedToWorld();
        this.setItemSlot(
                EquipmentSlot.MAINHAND,
                new ItemStack(WohItems.SHOTOGATANA.get())
        );
        this.setItemSlot(
                EquipmentSlot.HEAD,
                new ItemStack(WohItems.METAL_RONIN_HEADWEAR.get())
        );
        this.setItemSlot(
                EquipmentSlot.CHEST,
                new ItemStack(WohItems.RONIN_TUNIC.get())
        );
        this.setItemSlot(
                EquipmentSlot.LEGS,
                new ItemStack(WohItems.RONIN_LEGGINGS.get())
        );

        this.setDropChance(EquipmentSlot.MAINHAND, 0.0f);
        this.setDropChance(EquipmentSlot.HEAD, 0.35f);

    }
}
