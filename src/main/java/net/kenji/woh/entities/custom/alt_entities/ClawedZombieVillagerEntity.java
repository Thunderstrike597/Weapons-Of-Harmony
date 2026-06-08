package net.kenji.woh.entities.custom.alt_entities;

import net.kenji.woh.item.custom.weapon.Shotogatana;
import net.kenji.woh.item.custom.weapon.Tessen;
import net.kenji.woh.item.custom.weapon.Tsume;
import net.kenji.woh.registry.WohItems;
import net.minecraft.core.Holder;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.ZombieVillager;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;

public class ClawedZombieVillagerEntity extends ZombieVillager {
    public ClawedZombieVillagerEntity(EntityType<? extends ZombieVillager> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }
    public float addedAttackSpeed = 3.8F;

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
    @Override
    public double getAttributeValue(Holder<Attribute> pAttribute) {
        if(pAttribute.get() == Attributes.ATTACK_DAMAGE){
            return 1.2;
        }
        if(pAttribute == Attributes.ATTACK_SPEED){
            return super.getAttributeValue(pAttribute) * addedAttackSpeed;
        }

        return super.getAttributeValue(pAttribute);
    }

    @Override
    public boolean doHurtTarget(Entity pEntity) {
        float f = (float)this.getAttributeValue(Attributes.ATTACK_DAMAGE);
        float f1 = (float)this.getAttributeValue(Attributes.ATTACK_KNOCKBACK);
        if(this.getMainHandItem().getItem() instanceof Tsume) {
            if (pEntity instanceof LivingEntity) {
                if (!(this.getMainHandItem().getItem() instanceof Shotogatana)) {
                    f += EnchantmentHelper.getDamageBonus(this.getMainHandItem(), ((LivingEntity) pEntity).getMobType());
                    f1 += (float) EnchantmentHelper.getKnockbackBonus(this);
                }
            }

            int i = EnchantmentHelper.getFireAspect(this);
            if (i > 0) {
                pEntity.setSecondsOnFire(i * 4);
            }

            boolean flag = pEntity.hurt(this.damageSources().mobAttack(this), 1.8F);
            if (flag) {
                if (f1 > 0.0F && pEntity instanceof LivingEntity) {
                    ((LivingEntity) pEntity).knockback((double) (f1 * 0.5F), (double) Mth.sin(this.getYRot() * ((float) Math.PI / 180F)), (double) (-Mth.cos(this.getYRot() * ((float) Math.PI / 180F))));
                    this.setDeltaMovement(this.getDeltaMovement().multiply(0.6D, 1.0D, 0.6D));
                }

                this.doEnchantDamageEffects(this, pEntity);
                this.setLastHurtMob(pEntity);
            }
            return flag;
        }
        else return super.doHurtTarget(pEntity);
    }
}
