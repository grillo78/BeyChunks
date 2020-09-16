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

public class TeamsProvider implements ICapabilitySerializable<INBT> {

    @CapabilityInject(ITeams.class)
    public static final Capability<ITeams> TEAMS_CAPABILITY = null;
    private ITeams teamsInstance = TEAMS_CAPABILITY.getDefaultInstance();
    private LazyOptional<ITeams> teams = LazyOptional.of(()->teamsInstance);

    @Override
    public INBT serializeNBT() {
        return TEAMS_CAPABILITY.getStorage().writeNBT(TEAMS_CAPABILITY, teamsInstance, null);
    }

    @Override
    public void deserializeNBT(INBT nbt) {
        TEAMS_CAPABILITY.getStorage().readNBT(TEAMS_CAPABILITY, teamsInstance, null, nbt);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if(cap == TEAMS_CAPABILITY) return teams.cast();
        return null;
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap) {
        if(cap == TEAMS_CAPABILITY) return teams.cast();
        return null;
    }
}
