package org.chatbot.bot.command;


import java.util.Collections;
import java.util.Set;
import java.util.function.BiPredicate;

public class FuzzyCommand {

    private final BiPredicate<Command, Command> matcher;

    private final Set<Command> commandSynonyms;

    public FuzzyCommand(BiPredicate<Command, Command> matcher, Set<Command> commandSynonyms) {
        this.matcher = matcher;
        this.commandSynonyms = Collections.unmodifiableSet(commandSynonyms);
    }

    @Override
    public String toString() {
        return commandSynonyms.toString();
    }

    public boolean matches(FuzzyCommand fuzzyCommand) {
        return fuzzyCommand.commandSynonyms.stream().filter(command -> matches(command)).findFirst().isPresent();
    }

    public boolean matches(Command command) {
        return commandSynonyms.stream().filter(synonym -> matcher.test(synonym, command)).findFirst().isPresent();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FuzzyCommand that = (FuzzyCommand) o;
        return matches(that);
    }

    @Override
    public int hashCode() {
        return commandSynonyms.hashCode();
    }
}
