package com.perfma.xpocket.plugin.jconsole.cli.console;

public class ParseInputCommandCreationException extends ParseInputException {

    public ParseInputCommandCreationException(String input, Throwable init) {
        super("Cannot create command for '" + input + "'", input);
        initCause(init);
    }

}
