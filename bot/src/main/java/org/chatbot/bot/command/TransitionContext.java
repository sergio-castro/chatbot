package org.chatbot.bot.command;


public class TransitionContext {

    private final CommandContext currentCommandContext;

    private final Command command;

    public TransitionContext(CommandContext currentCommandContext, Command command) {
        this.currentCommandContext = currentCommandContext;
        this.command = command;
    }

    public CommandContext getCurrentCommandContext() {
        return currentCommandContext;
    }

    public Command getCommand() {
        return command;
    }

}
