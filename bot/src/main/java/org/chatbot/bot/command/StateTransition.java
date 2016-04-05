package org.chatbot.bot.command;


import java.util.function.Function;

public class StateTransition {

    private final FuzzyCommand fuzzyCommand;

    private final Function<TransitionContext, CommandContext> transitionFunction;

    public StateTransition(FuzzyCommand command, Function<TransitionContext, CommandContext> transitionFunction) {
        this.fuzzyCommand = command;
        this.transitionFunction = transitionFunction;
    }

    public FuzzyCommand getFuzzyCommand() {
        return fuzzyCommand;
    }

    public Function<TransitionContext, CommandContext> getTransitionFunction() {
        return transitionFunction;
    }

    public boolean matches(Command command) {
        return fuzzyCommand.matches(command);
    }
}
