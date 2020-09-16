package com.grillo78.beychunks.commands;

import com.grillo78.beychunks.capabilities.ClaimedProvider;
import com.grillo78.beychunks.capabilities.Team;
import com.grillo78.beychunks.capabilities.TeamsProvider;
import com.grillo78.beycraft.capabilities.BladerCapProvider;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.chunk.Chunk;

public class ClaimChunkCommand {

    public static void register(CommandDispatcher<CommandSource> dispatcher) {
        LiteralArgumentBuilder<CommandSource> literalargumentbuilder = Commands.literal("claim_chunk").executes((context -> claimChunk(context.getSource())));
        dispatcher.register(literalargumentbuilder);
    }

    private static int claimChunk(CommandSource source) {
        int i = 0;
        if (source.getEntity() != null && source.getEntity() instanceof ServerPlayerEntity) {
            ServerPlayerEntity player = (ServerPlayerEntity) source.getEntity();
            player.getCapability(BladerCapProvider.BLADERCURRENCY_CAP).ifPresent(h -> {
                if(h.getCurrency()>=100){
                    ((Chunk) source.getWorld().getChunk(new BlockPos(source.getPos()))).getCapability(ClaimedProvider.CLAIMED_CAPABILITY).ifPresent(j->{
                        source.getWorld().getCapability(TeamsProvider.TEAMS_CAPABILITY).ifPresent(k->{
                            for (Team team:k.getTeams()) {
                                if(team.getUuidMembers().contains(player.getUniqueID())){
                                    j.setClaimed(true);
                                    j.setUUID(team.getUuid());
                                    h.increaseCurrency(-100);
                                }
                            }
                        });
                    });
                } else {
                    source.sendErrorMessage(new StringTextComponent("Not enough BeyPoints"));
                }
            });
        }
        return i;
    }
}
