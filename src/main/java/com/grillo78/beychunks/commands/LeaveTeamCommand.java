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

public class LeaveTeamCommand {

    public static void register(CommandDispatcher<CommandSource> dispatcher) {
        LiteralArgumentBuilder<CommandSource> literalargumentbuilder = Commands.literal("leave").executes((context -> leaveTeam(context.getSource())));
        dispatcher.register(literalargumentbuilder);
    }

    private static int leaveTeam(CommandSource source) {
        int i = 0;
        if (source.getEntity() != null && source.getEntity() instanceof ServerPlayerEntity) {
            ServerPlayerEntity player = (ServerPlayerEntity) source.getEntity();
            source.getWorld().getCapability(TeamsProvider.TEAMS_CAPABILITY).ifPresent(k -> {
                for (Team team : k.getTeams()) {
                    if (team.getUuidMembers().contains(player.getUniqueID())) {
                        if(team.leaveTeam(player.getUniqueID())){
                            if(team.getUuidMembers().isEmpty()) k.getTeams().remove(team);
                            source.sendFeedback(new StringTextComponent("You leave the team successfully"), false);
                        }
                    }
                }
            });
        }
        return i;
    }
}
