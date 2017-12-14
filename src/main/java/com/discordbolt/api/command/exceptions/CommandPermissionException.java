package com.discordbolt.api.command.exceptions;

import com.discordbolt.api.command.ExceptionMessage;

/**
 * Created by Tony on 4/19/2017.
 */
public class CommandPermissionException extends CommandException {

    public CommandPermissionException() {
        super(ExceptionMessage.PERMISSION_DENIED);
    }

    public CommandPermissionException(String message) {
        super(message);
    }
}
