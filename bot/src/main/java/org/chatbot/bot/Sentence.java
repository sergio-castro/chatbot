
package org.chatbot.bot;


public class Sentence {

    private final String sentenceText;

    private Sentence(String sentenceText) {
        this.sentenceText = sentenceText;
    }

    public static Sentence sentence(String sentenceText) {
        return new Sentence(sentenceText);
    }

    public String asText() {
        return sentenceText;
    }

}
