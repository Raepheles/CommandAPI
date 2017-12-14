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

        if (cc.getArgCount() > 1) {
            String userRequestedModule = cc.combineArgs(1, cc.getArgCount() - 1);
            modules = modules.stream().filter(s -> s.equalsIgnoreCase(userRequestedModule)).collect(Collectors.toList());
            if (modules.size() < 1) {
                cc.replyWith("No modules found matching \"" + userRequestedModule + "\".");
                return;
            }
        }

        char commandPrefix = manager.getCommandPrefix(cc.getGuild());

        boolean send = false;
        EmbedBuilder embed = new EmbedBuilder();
        embed.withColor(36, 153, 153);

        StringBuilder sb = new StringBuilder();
        for (String module : modules) {
            sb.setLength(0);

            for (CustomCommand command : manager.getCommands().stream().filter(c -> c.getModule().equals(module)).collect(Collectors.toList())) {
                // Check if the user has permission for the command.
                if (!cc.getAuthor().getPermissionsForGuild(cc.getGuild()).containsAll(command.getPermissions()))
                    continue;
                if (command.isSecret())
                    continue;

                sb.append('`').append(commandPrefix).append(String.join(" ", command.getCommands())).append("` | ").append(command.getDescription()).append('\n');
            }
            if (sb.length() > 1024)
                sb.setLength(1024);

            if (embed.getTotalVisibleCharacters() + sb.length() + module.length() >= 6000)
                continue;

            if (module.length() == 0 || sb.length() == 0) {
                continue;
            }

            send = true;
            embed.appendField(module, sb.toString(), false);
        }
        if (send)
            cc.replyWith(embed.build());
        else
            cc.replyWith("No available commands.");
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
