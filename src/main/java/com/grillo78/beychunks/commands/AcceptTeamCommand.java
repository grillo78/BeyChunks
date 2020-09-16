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

public class AcceptTeamCommand {

    public static void register(CommandDispatcher<CommandSource> dispatcher) {
        LiteralArgumentBuilder<CommandSource> literalargumentbuilder = Commands.literal("accept").then(Commands.argument("name", StringArgumentType.string()).executes((context -> acceptInvite(context.getSource(), StringArgumentType.getString(context, "name")))));
        dispatcher.register(literalargumentbuilder);
    }

    private static int acceptInvite(CommandSource source, String name) {
        int i = 0;
        if (source.getEntity() instanceof ServerPlayerEntity) {
            source.getWorld().getCapability(TeamsProvider.TEAMS_CAPABILITY).ifPresent(h -> {
                for (Team team : h.getTeams()) {
                    if(team.getName().equals(name)){
                        try {
                            if (team.acceptInvite(source.asPlayer().getUniqueID())){
                                source.sendFeedback(new StringTextComponent("You accepted the invite to the team called "+name), false);
                            } else {
                                source.sendErrorMessage(new StringTextComponent("You wasn't invite to that team"));
                            }
                        } catch (CommandSyntaxException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        }
        return i;
    }

}
