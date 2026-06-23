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
    public static final String STORED_SWORD_TAG = "arbiter_sword_taken";

    private boolean swordTaken = false;
    private ItemStack storedSword;

    public SwordPedistoolBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.SWORD_PEDISTOOL_BE.get(), pos, state);
        if(storedSword == null)
            storedSword = WohItems.ARBITERS_BLADE.get().getDefaultInstance();
    }

    public boolean isSwordTaken() {
        return swordTaken;
    }

    public ItemStack takeSword() {
        ItemStack finalStack = getDisplayedItem();
        swordTaken = true;
        storedSword = null;
        setChanged();

        // Sync to clients
        if (level != null && !level.isClientSide) {
            level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
        }

        return finalStack;
    }
    public void placeSword(ItemStack stack) {
        swordTaken = false;
        storedSword = stack;
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
        if(!swordTaken){
            if(storedSword != null)
                tag.put(STORED_SWORD_TAG, storedSword.serializeNBT());
        }
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        swordTaken = tag.getBoolean(SWORD_TAKEN_TAG);
        if(!swordTaken){
            CompoundTag itemTag = tag.getCompound(STORED_SWORD_TAG);
            storedSword = ItemStack.of(itemTag);
        }
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
        ItemStack finalStack = storedSword == null || storedSword == ItemStack.EMPTY ? WohItems.ARBITERS_BLADE.get().getDefaultInstance() : storedSword;
        return swordTaken ? ItemStack.EMPTY : finalStack;
    }
}