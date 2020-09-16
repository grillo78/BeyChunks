package com.grillo78.beychunks.commands;

import com.grillo78.beychunks.capabilities.Team;
import com.grillo78.beychunks.capabilities.TeamsProvider;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.entity.player.ServerPlayerEntity;

import java.util.Collection;

public class InviteTeamCommand {

    public static void register(CommandDispatcher<CommandSource> dispatcher) {
        LiteralArgumentBuilder<CommandSource> literalargumentbuilder = Commands.literal("invite").then(Commands.argument("players", EntityArgument.player()).executes((context -> invitePlayer(context.getSource(),EntityArgument.getPlayers(context, "players")))));
        dispatcher.register(literalargumentbuilder);
    }

    private static int invitePlayer(CommandSource source, Collection<ServerPlayerEntity> players) {
        int i = 0;
        if (source.getEntity() != null && source.getEntity() instanceof ServerPlayerEntity) {
            ServerPlayerEntity player = (ServerPlayerEntity) source.getEntity();
                source.getWorld().getCapability(TeamsProvider.TEAMS_CAPABILITY).ifPresent(k->{
                    for (Team team:k.getTeams()) {
                        if(team.getUuidMembers().contains(player.getUniqueID())){
                            for(ServerPlayerEntity playerEntity : players){
                                team.invitePlayer(playerEntity.getUniqueID());
                            }
                        }
                    }
                });
        }
        return i;
    }
}
