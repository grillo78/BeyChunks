package com.grillo78.beychunks.commands;

import com.grillo78.beychunks.capabilities.Team;
import com.grillo78.beychunks.capabilities.TeamsProvider;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.text.StringTextComponent;

public class CreateTeamCommand {

    public static void register(CommandDispatcher<CommandSource> dispatcher) {
        LiteralArgumentBuilder<CommandSource> literalargumentbuilder = Commands.literal("create_team").then(Commands.argument("name", StringArgumentType.string()).executes((context -> createTeam(context.getSource(), StringArgumentType.getString(context, "name")))));
        dispatcher.register(literalargumentbuilder);
    }

    private static int createTeam(CommandSource source, String name) {
        int i = 0;
        if (source.getEntity() instanceof ServerPlayerEntity) {

            source.getWorld().getCapability(TeamsProvider.TEAMS_CAPABILITY).ifPresent(h -> {

                try {
                    boolean inTeam = false;
                    for (Team team : h.getTeams()) {
                        if (team.getUuidMembers().contains(source.asPlayer().getUniqueID())) {
                            inTeam = true;
                        }
                    }
                    if (!inTeam) {
                        Team team = new Team(name);
                        team.addPlayer(source.asPlayer());
                        h.getTeams().add(team);
                        source.sendFeedback(new StringTextComponent("Team "+ name + " created successfully"), false);
                    } else {
                        source.sendErrorMessage(new StringTextComponent("You are in other team, leave it to create other team"));
                    }
                } catch (CommandSyntaxException e) {
                }
            });
        }
        return i;
    }

}
