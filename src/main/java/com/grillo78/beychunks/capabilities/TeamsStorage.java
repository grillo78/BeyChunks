package com.grillo78.beychunks.capabilities;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class TeamsStorage implements Capability.IStorage<ITeams> {
    @Nullable
    @Override
    public INBT writeNBT(Capability<ITeams> capability, ITeams instance, Direction side) {
        CompoundNBT compoundNBT = new CompoundNBT();
        for (int i = 0; i < instance.getTeams().size(); i++) {
            compoundNBT.put(String.valueOf(i),instance.getTeams().get(i).writeNBT());
        }
        return compoundNBT;
    }

    @Override
    public void readNBT(Capability<ITeams> capability, ITeams instance, Direction side, INBT nbt) {
        List<Team> teams = new ArrayList<>();
        for (int i = 0; i<((CompoundNBT)nbt).size();i++){
            teams.add(new Team().readNBT((CompoundNBT)((CompoundNBT)nbt).get(String.valueOf(i))));
        }
        instance.setTeams(teams);
    }
}
