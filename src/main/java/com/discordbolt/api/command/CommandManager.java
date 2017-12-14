package com.discordbolt.api.command;

import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sx.blah.discord.Discord4J;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.handle.obj.IGuild;

import java.lang.reflect.Modifier;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Tony on 2/17/2017.
 */
public class CommandManager {

    private final char DEFAULT_PREFIX = '!';
    private IDiscordClient client;
    private Logger logger = LoggerFactory.getLogger(CommandManager.class);
    private List<CustomCommand> commands = new ArrayList<>();
    private Map<Long, Character> commandPrefixes = new HashMap<Long, Character>();

    /**
     * Initalize Command API
     * @param client IDiscordClient
     * @param packagePrefix package string where commands are located
     */
    public CommandManager(IDiscordClient client, String packagePrefix) {
        logger.info("Initializing Commands");

        // Save IDiscordClient
        this.client = client;

        // Get all static methods with @BotCommand and create CustomCommand objects
        commands.addAll(new Reflections(packagePrefix, new MethodAnnotationsScanner()).getMethodsAnnotatedWith(BotCommand.class).stream().filter(a -> Modifier.isStatic(a.getModifiers())).map(a -> new CustomCommand(this, a)).collect(Collectors.toList()));

        // Register our command listener
        getClient().getDispatcher().registerListener(new CommandListener(this));
    }

    /**
     * Get the Discord4J client
     *
     * @return IDiscordClient
     */
    protected IDiscordClient getClient() {
        return client;
    }

    /**
     * Get a list of all commands currently registered
     *
     * @return UnmodifiableList of CustomCommands
     */
    protected List<CustomCommand> getCommands() {
        return Collections.unmodifiableList(commands);
    }

    /**
     * Get the command prefix of a given guild
     *
     * @param guild
     * @return char command prefix for given guild
     */
    public char getCommandPrefix(IGuild guild) {
        if (guild == null)
            return DEFAULT_PREFIX;
        return commandPrefixes.getOrDefault(guild.getLongID(), DEFAULT_PREFIX);
    }

    /**
     * Set the command prefix of a specified guild
     *
     * @param guild         Guild to change the prefix for
     * @param commandPrefix new prefix characters all commands must be prefaced with
     */
    public void setCommandPrefix(IGuild guild, char commandPrefix) {
        commandPrefixes.put(guild.getLongID(), commandPrefix);
    }
}
