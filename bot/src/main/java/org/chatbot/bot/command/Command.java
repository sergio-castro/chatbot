package org.chatbot.bot.command;


import org.chatbot.bot.Identifiable;

public class Command extends Identifiable<String> {

    private Command(String id) {
        super(id);
    }

}
