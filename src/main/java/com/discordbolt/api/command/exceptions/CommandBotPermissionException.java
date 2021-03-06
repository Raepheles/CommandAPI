package com.discordbolt.api.command.exceptions;

import com.discordbolt.api.command.ExceptionMessage;

/**
 * Created by Tony on 4/19/2017.
 */
public class CommandBotPermissionException extends CommandException {

    public CommandBotPermissionException() {
        super(ExceptionMessage.BOT_PERMISSION_DENIED);
    }

    public CommandBotPermissionException(String message) {
        super(message);
    }
}
