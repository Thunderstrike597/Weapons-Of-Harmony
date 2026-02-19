package net.kenji.woh.block.custom.entity;

import net.kenji.woh.block.ModBlockEntities;
import net.kenji.woh.registry.WohItems;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class SwordPedistoolBlockEntity extends BlockEntity {

    public static final String SWORD_TAKEN_TAG = "arbiter_sword_taken";
    private boolean swordTaken = false;


    public SwordPedistoolBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.SWORD_PEDISTOOL_BE.get(), pos, state);
    }

    public boolean isSwordTaken() {
        return swordTaken;
    }

    public void takeSword() {
        swordTaken = true;
        setChanged();

        // Sync to clients
        if (level != null && !level.isClientSide) {
            level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
        }
    }


    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putBoolean(SWORD_TAKEN_TAG, swordTaken);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        swordTaken = tag.getBoolean(SWORD_TAKEN_TAG);
    }

    // Sync data to client
    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag tag = super.getUpdateTag();
        saveAdditional(tag);
        return tag;
    }

    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    public ItemStack getDisplayedItem() {
        return swordTaken ? ItemStack.EMPTY : WohItems.ARBITERS_BLADE.get().getDefaultInstance();
    }
}