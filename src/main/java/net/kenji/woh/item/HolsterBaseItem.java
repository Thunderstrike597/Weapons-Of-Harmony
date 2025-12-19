package net.kenji.woh.item;

import net.minecraft.ChatFormatting;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tier;
import net.minecraft.world.phys.Vec3;
import org.joml.Quaternionf;

public class HolsterBaseItem extends WohWeaponItem {


    public static class Vec3Pair{
        public Vec3 key;
        public Vec3 value;
        Vec3Pair(float x, float y, float z,
                 float xx, float yy, float zz){
            key = new Vec3(x, y, z);
            value = new Vec3(xx, yy, zz);
        }
    }
    public static class QuaternionFPair{
        public Quaternionf key;
        public Quaternionf value;
        QuaternionFPair(float x, float y, float z,
                        float xx, float yy, float zz){
            key = new Quaternionf().rotateX((float)Math.toRadians(x))
                    .rotateY((float)Math.toRadians(y)).
                    rotateZ((float)Math.toRadians(z));
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

    public HolsterTransform holsterTransform;
    public Item holsterItem;
    public Item unholsteredItem;
    public boolean canOffHandHolster;

    public HolsterBaseItem(Tier tier, int damageIn, float speedIn, Properties builder, boolean hasTooltip, ChatFormatting tooltipColor, HolsterTransform holsterTransform, Item holsterItem, Item unholsterItem, boolean holsterOffhand) {
        super(tier, damageIn, speedIn, builder, hasTooltip, tooltipColor);
        this.holsterTransform = holsterTransform;
        this.holsterItem = holsterItem;
        this.unholsteredItem = unholsterItem;
        this.canOffHandHolster = holsterOffhand;
    }
}
