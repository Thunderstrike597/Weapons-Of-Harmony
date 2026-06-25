package net.kenji.woh.entities.custom.alt_entities;

import net.kenji.woh.WeaponsOfHarmony;
import net.kenji.woh.entities.WohEntities;
import net.kenji.woh.item.custom.weapon.Shotogatana;
import net.kenji.woh.registry.WohItems;
import net.minecraft.core.Holder;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = WeaponsOfHarmony.MODID)
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
        this.setItemSlot(
                EquipmentSlot.FEET,
                new ItemStack(WohItems.RONIN_BOOTS.get())
        );
        this.setDropChance(EquipmentSlot.MAINHAND, 0.0f);
        this.setDropChance(EquipmentSlot.HEAD, 0.35f);
    }

    @Override
    public double getAttributeValue(Holder<Attribute> pAttribute) {
        if(pAttribute.get() == Attributes.ATTACK_DAMAGE){
            return 1;
        }
        return super.getAttributeValue(pAttribute);
    }

    @Override
    public boolean doHurtTarget(Entity pEntity) {
        float f = (float)this.getAttributeValue(Attributes.ATTACK_DAMAGE);
        float f1 = (float)this.getAttributeValue(Attributes.ATTACK_KNOCKBACK);
        if(this.getMainHandItem().getItem() instanceof Shotogatana) {
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
