package net.kenji.woh.entities.custom.alt_entities;

import net.kenji.woh.registry.WohItems;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.ZombieVillager;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ClawedZombieVillagerEntity extends ZombieVillager {
    public ClawedZombieVillagerEntity(EntityType<? extends ZombieVillager> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }
    public float addedAttackSpeed = 3.8F;

    @Override
    public double getAttributeValue(Holder<Attribute> pAttribute) {
        if(pAttribute == Attributes.ATTACK_SPEED){
            return super.getAttributeValue(pAttribute) * addedAttackSpeed;
        }

        return super.getAttributeValue(pAttribute);
    }
    @Override
    public void onAddedToWorld() {
        super.onAddedToWorld();
        this.setItemSlot(
                EquipmentSlot.MAINHAND,
                new ItemStack(WohItems.TSUME.get())
        );
        this.setItemSlot(
                EquipmentSlot.HEAD,
                new ItemStack(WohItems.METAL_RONIN_HEADWEAR.get())
        );
        this.setDropChance(EquipmentSlot.MAINHAND, 0.0f);

    }
}
