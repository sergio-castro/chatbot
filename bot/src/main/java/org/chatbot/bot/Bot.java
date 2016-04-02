package org.chatbot.bot;


import java.util.Optional;

public interface Bot {

    Optional<Sentence> reply(Sentence sentence);

}
