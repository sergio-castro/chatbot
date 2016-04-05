package org.chatbot.bot;


import java.util.Optional;

public interface Bot {

    Optional<Identifiable> reply(Identifiable sentence);

}
