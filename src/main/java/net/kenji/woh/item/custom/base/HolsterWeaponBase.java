package net.kenji.woh.item.custom.base;

import net.minecraft.ChatFormatting;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.phys.Vec3;
import org.joml.Quaternionf;
import yesman.epicfight.api.animation.Joint;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;

import java.util.function.Supplier;

public abstract class HolsterWeaponBase extends WohWeaponItem {


    public static class Vec3Pair{
        public Vec3 key;
        public Vec3 value;

        public float keyX, keyY, keyZ;
        public float valueX, valueY, valueZ;

        public Vec3Pair(float x, float y, float z,
                        float xx, float yy, float zz){
            keyX = x; keyY = y; keyZ = z;
            valueX = xx; valueY = yy; valueZ = zz;
            key = new Vec3(x, y, z);
            value = new Vec3(xx, yy, zz);
        }
    }
    public static class QuaternionFPair {
        public Quaternionf key;
        public Quaternionf value;
        // Store original angles for reference
        public float keyX, keyY, keyZ;
        public float valueX, valueY, valueZ;

        public QuaternionFPair(float x, float y, float z, float xx, float yy, float zz) {
            // Store angles
            keyX = x; keyY = y; keyZ = z;
            valueX = xx; valueY = yy; valueZ = zz;

            // Create quaternions
            key = new Quaternionf()
                    .rotateX((float)Math.toRadians(x))
                    .rotateY((float)Math.toRadians(y))
                    .rotateZ((float)Math.toRadians(z));
            value = new Quaternionf()
                    .rotateX((float)Math.toRadians(xx))
                    .rotateY((float)Math.toRadians(yy))
                    .rotateZ((float)Math.toRadians(zz));
        }
    }



    public static class HolsterTransform {
        public Vec3Pair translationPair;
        public Vec3Pair scalePair;
        public QuaternionFPair rotationPair;


        public HolsterTransform(Vec3Pair t, Vec3Pair s, QuaternionFPair r) {
            translationPair = t;
            scalePair = s;
            rotationPair = r;
        }
    }

    public static class JointPair {
        Supplier<Joint> hotBarJointSupplier;
        Supplier<Joint> offHandJointSupplier;

        public JointPair(Supplier<Joint> joint1Supplier, Supplier<Joint> joint2Supplier) {
            hotBarJointSupplier = joint1Supplier;
            offHandJointSupplier = joint2Supplier;
        }

        // These getters evaluate the supplier at runtime, not during construction
        public Joint getHotBarJoint() {
            return hotBarJointSupplier != null ? hotBarJointSupplier.get() : null;
        }

        public Joint getOffHandJoint() {
            return offHandJointSupplier != null ? offHandJointSupplier.get() : null;
        }
    }


    public HolsterTransform holsterTransform;
    public Item holsterItem;
    public Item unholsteredItem;
    public JointPair holsterJoints;
    public boolean canOffHandHolster;

    public boolean shouldRenderHolstered(PlayerPatch<?> player){
        return !shouldRenderInHand(player);
    }
    public boolean shouldRenderUnholstered(PlayerPatch<?> player){
        return !shouldRenderHolstered(player);
    }
    public boolean shouldRenderInHand(PlayerPatch<?> playerPatch){
        ItemStack holdingItem = playerPatch.getOriginal().getItemInHand(InteractionHand.MAIN_HAND);
        return (holdingItem.getItem() == this) && playerPatch.isBattleMode();
    }

    public HolsterWeaponBase(Tier tier, int damageIn, float speedIn, Properties builder, boolean hasTooltip, ChatFormatting tooltipColor, HolsterTransform holsterTransform, Item holsterItem, Item unholsterItem, boolean holsterOffhand, JointPair holsterJoints) {
        super(tier, damageIn, speedIn, builder, hasTooltip, tooltipColor);
        this.holsterTransform = holsterTransform;
        this.holsterItem = holsterItem;
        this.unholsteredItem = unholsterItem;
        this.canOffHandHolster = holsterOffhand;
        this.holsterJoints = holsterJoints;
    }
    public HolsterWeaponBase(Tier tier, int damageIn, float speedIn, Properties builder, boolean hasTooltip, ChatFormatting tooltipColor, HolsterTransform holsterTransform, Item holsterItem, boolean holsterOffhand, JointPair holsterJoints) {
        super(tier, damageIn, speedIn, builder, hasTooltip, tooltipColor);
        this.holsterTransform = holsterTransform;
        this.holsterItem = holsterItem;
        this.canOffHandHolster = holsterOffhand;
        this.holsterJoints = holsterJoints;
    }
    public HolsterWeaponBase(Tier tier, int damageIn, float speedIn, Properties builder, boolean hasTooltip, ChatFormatting tooltipColor) {
        super(tier, damageIn, speedIn, builder, hasTooltip, tooltipColor);
    }
}
