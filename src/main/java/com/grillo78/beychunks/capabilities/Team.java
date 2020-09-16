package com.grillo78.beychunks.capabilities;

import com.grillo78.beychunks.Reference;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Team {

    private final List<UUID> uuidMembers = new ArrayList<>();
    private final List<UUID> invitedPlayers = new ArrayList<>();
    private String name;
    private UUID uuid;

    public Team(){}

    public Team(String name){
        this.name = name;
        this.uuid = UUID.randomUUID();
    }

    public void setName(String name) {
        this.name = name;
    }

    public void invitePlayer(UUID uuid){
        invitedPlayers.add(uuid);
    }

    public boolean acceptInvite(UUID uuid){
        boolean accepted = invitedPlayers.remove(uuid);
        if(accepted){
            uuidMembers.add(uuid);
        }
        return accepted;
    }

    public boolean leaveTeam(UUID uuid){
        return uuidMembers.remove(uuid);
    }

    public String getName() {
        return name;
    }

    public UUID getUuid() {
        return uuid;
    }

    public List<UUID> getUuidMembers() {
        return uuidMembers;
    }

    public void addPlayer(ServerPlayerEntity player){
        uuidMembers.add(player.getUniqueID());
    }

    public INBT writeNBT(){
        CompoundNBT nbt = new CompoundNBT();
        CompoundNBT uuids = new CompoundNBT();
        for (int i = 0; i < uuidMembers.size(); i++) {
            uuids.putUniqueId(String.valueOf(i),uuidMembers.get(i));
        }
        nbt.putUniqueId("uuid", uuid);
        nbt.putString("name", name);
        nbt.put("uuids",uuids);
        return nbt;
    }

    public Team readNBT(CompoundNBT nbt){
        CompoundNBT uuids = (CompoundNBT) nbt.get("uuids");
        for (int i = 0; i < uuids.size(); i++) {
            uuidMembers.add(i,uuids.getUniqueId(String.valueOf(i)));
        }
        name = nbt.getString("name");
        uuid = nbt.getUniqueId("uuid");
        return this;
    }
}
