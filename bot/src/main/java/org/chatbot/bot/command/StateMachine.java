package org.chatbot.bot.command;


import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class StateMachine {

    private final Map<Command, StateTransition> commandTransitions;

    private final Set<StateTransition> fuzzyCommandTransitions;

    private volatile CommandContext currentContext;

    public StateMachine(CommandContext initialContext,
                Map<Command, StateTransition> commandTransitions,
                Set<StateTransition> fuzzyCommandTransitions) {
        this.commandTransitions = commandTransitions;
        this.fuzzyCommandTransitions = fuzzyCommandTransitions;
    }

    public boolean apply(Command command) {
        return true;
    }

    private Optional<StateTransition> find(Command command) {
        Optional<StateTransition> match = Optional.ofNullable(commandTransitions.get(command));
        if (!match.isPresent()) {
            match = fuzzyCommandTransitions.stream()
                    .filter(fuzzyCommandTransition -> fuzzyCommandTransition.matches(command))
                    .findFirst();
        }
        return match;
    }

}
