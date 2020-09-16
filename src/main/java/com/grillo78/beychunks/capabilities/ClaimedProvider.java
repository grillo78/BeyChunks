package com.grillo78.beychunks.capabilities;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ClaimedProvider implements ICapabilitySerializable<INBT> {

    @CapabilityInject(IClaimed.class)
    public static final Capability<IClaimed> CLAIMED_CAPABILITY = null;
    private IClaimed claimedInstance = CLAIMED_CAPABILITY.getDefaultInstance();
    private LazyOptional<IClaimed> claimed = LazyOptional.of(()->claimedInstance);

    @Override
    public INBT serializeNBT() {
        return CLAIMED_CAPABILITY.getStorage().writeNBT(CLAIMED_CAPABILITY, claimedInstance, null);
    }

    @Override
    public void deserializeNBT(INBT nbt) {
        CLAIMED_CAPABILITY.getStorage().readNBT(CLAIMED_CAPABILITY, claimedInstance, null, nbt);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if(cap == CLAIMED_CAPABILITY) return claimed.cast();
        return null;
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap) {
        if(cap == CLAIMED_CAPABILITY) return claimed.cast();
        return null;
    }
}
