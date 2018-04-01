package com.discordbolt.api.command;

import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.guild.channel.message.MentionEvent;
import sx.blah.discord.util.EmbedBuilder;
import sx.blah.discord.util.RequestBuffer;

import java.util.List;
import java.util.stream.Collectors;

public class HelpCommand {

    private static CommandManager manager;

    public HelpCommand(CommandManager manager) {
        HelpCommand.manager = manager;
    }

    @BotCommand(command = "help", aliases = "h", module = "Help Module", secret = true, description = "View all available commands.", usage = "Help [Module]")
    public static void helpCommand(CommandContext cc) {
        
        List<String> modules = manager.getCommands().stream().map(CustomCommand::getModule).distinct().collect(Collectors.toList());
        String commandPrefix = manager.getCommandPrefix(cc.getGuild());

        if (cc.getArgCount() > 1) {
            String userRequestedModule = cc.combineArgs(1, cc.getArgCount() - 1);
            modules = modules.stream().filter(s -> s.equalsIgnoreCase(userRequestedModule)).collect(Collectors.toList());
            if (modules.size() < 1) {
                cc.replyWith("No modules found matching \"" + userRequestedModule + "\".");
                return;
            }
        }

        boolean send = false;
        StringBuilder sb = new StringBuilder("```\n");

        for (String module : modules) {
            sb.append(String.format("%s | For module commands use: %shelp %s\n", module, manager.getCommandPrefix(cc.getGuild()), module);
        }
        sb.append("```");
        cc.replyWith(sb.toString());   
    }

    @EventSubscriber
    public void helpMention(MentionEvent e) {
        if (e.getMessage().getContent().toLowerCase().contains("prefix")) {
            RequestBuffer.request(() -> {
                return e.getChannel().sendMessage("Prefix your commands with `" + manager.getCommandPrefix(e.getGuild()) + "`");
            }).get();
        }
    }
}
