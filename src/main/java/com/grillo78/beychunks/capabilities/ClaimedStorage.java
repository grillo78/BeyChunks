package com.grillo78.beychunks.capabilities;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;

public class ClaimedStorage implements Capability.IStorage<IClaimed> {
    @Nullable
    @Override
    public INBT writeNBT(Capability<IClaimed> capability, IClaimed instance, Direction side) {
        CompoundNBT compoundNBT = new CompoundNBT();
        compoundNBT.putBoolean("claimed", instance.isClaimed());
        if(instance.getUUID() != null){
            compoundNBT.putUniqueId("uuid", instance.getUUID());
        }
        return compoundNBT;
    }

    @Override
    public void readNBT(Capability<IClaimed> capability, IClaimed instance, Direction side, INBT nbt) {
        instance.setClaimed(((CompoundNBT)nbt).getBoolean("claimed"));
        if(((CompoundNBT)nbt).contains("uuid")){
            instance.setUUID(((CompoundNBT)nbt).getUniqueId("uuid"));
        }
    }
}
