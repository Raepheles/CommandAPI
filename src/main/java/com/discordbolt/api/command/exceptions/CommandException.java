package com.discordbolt.api.command.exceptions;

import com.discordbolt.api.command.ExceptionMessage;

/**
 * Created by Tony on 4/19/2017.
 */
public class CommandException extends Exception {

    public CommandException() {
        super(ExceptionMessage.COMMAND_PROCESS_EXCEPTION);
    }

    public CommandException(String message) {
        super(message);
    }
}
