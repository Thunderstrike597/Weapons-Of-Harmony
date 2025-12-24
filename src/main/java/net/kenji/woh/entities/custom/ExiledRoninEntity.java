package net.kenji.woh.entities.custom;

import net.kenji.woh.registry.WOHItems;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ExiledRoninEntity extends PathfinderMob {
    private static ExiledRoninEntity INSTANCE;

    public ExiledRoninEntity(EntityType<? extends PathfinderMob> p_27403_, Level p_27404_) {
        super(p_27403_, p_27404_);
        if(INSTANCE == null){
            INSTANCE = this;
        }
    }

    @Override
    public void setSpeed(float pSpeed) {
        float speed = 0.15f;
        if(getTarget() != null ) {
            speed = 0.22f;
        }

        super.setSpeed(speed);
    }

    @Override
    protected boolean isSunBurnTick() {
        return false;
    }


    @Override
    public void tick() {
        super.tick();

        if (this.level().isClientSide) return;

        // Set item in hand
        if (this.getMainHandItem().isEmpty()) {
            this.setItemInHand(InteractionHand.MAIN_HAND,
                    new ItemStack(WOHItems.SHOTOGATANA.get()));
        }

        // Find nearest player
        Player nearest = this.level().getNearestPlayer(this, 16); // 16 blocks radius
        if (nearest != null) {
            this.setTarget(nearest); // sets the entity's attack target
        }
    }

    @Override
    public boolean canHoldItem(ItemStack pStack) {
        return true;
    }

    @Override
    public boolean isAggressive() {
        return true;
    }



    @Override
    protected void registerGoals() {
        super.registerGoals();

        // Target the nearest player

        // Melee attack behavior
        this.goalSelector.addGoal(2, new net.minecraft.world.entity.ai.goal.MeleeAttackGoal(this, 1.2D, false));

        // Move toward target
        this.goalSelector.addGoal(3, new net.minecraft.world.entity.ai.goal.MoveTowardsTargetGoal(this, 1.2D, 32.0F));
    }
}

