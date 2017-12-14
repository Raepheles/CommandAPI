package com.discordbolt.api.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IUser;

public class CommandListener {

    private CommandManager manager;
    private Logger logger = LoggerFactory.getLogger(CommandListener.class);

    protected CommandListener(CommandManager manager) {
        this.manager = manager;
    }

    @EventSubscriber
    public void onMesageEvent(MessageReceivedEvent e) {
        String message = e.getMessage().getContent();
        IUser user = e.getAuthor();

        // Ignore bots
        if (user.isBot()) {
            return;
        }

        // Message is just a single prefix.
        if (message.length() <= 1) {
            return;
        }

        // Check if message started with our command prefix
        if (message.charAt(0) != manager.getCommandPrefix(e.getGuild())) {
            return;
        }

        int userArgCount = message.split(" ").length;
        CustomCommand customCommand = manager.getCommands().stream().filter(command -> command.getCommands().length <= userArgCount).filter(command -> command.matches(message)).reduce((first, second) -> second).orElse(null);

        if (customCommand != null) {
            logger.trace("Preexecuting " + manager.getCommandPrefix(e.getGuild()) + String.join(" ", customCommand.getCommands()));
            customCommand.preexec(e.getMessage(), user);
        }
    }
}
