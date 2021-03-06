package com.discordbolt.api.command;

public interface ExceptionMessage {

    String API_LIMIT = "Sending Discord too many requests. Rate limit hit.";
    String PERMISSION_DENIED = "You do not have permission for this command!";
    String BOT_PERMISSION_DENIED = "I do not have permission to perform this action!";
    String COMMAND_PROCESS_EXCEPTION = "An error has occurred while processing your command. Please contact with bot owner regarding this issue using `feedback` command. Don't forget to mention exactly when did this error happen and which command caused it.";
    String INCORRECT_USAGE = "Your command did not match expected input. Please check !Help for usage.";
    String BAD_STATE = "I'm sorry Dave, I'm afraid I can't do that";
    String EXECUTE_IN_GUILD = "You must execute this command in a guild.";
}
