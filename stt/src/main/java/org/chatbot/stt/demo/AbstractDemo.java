package org.chatbot.stt.demo;


import java.util.stream.Stream;

public class AbstractDemo {

    static {
        System.setProperty("java.util.logging.manager", "org.apache.logging.log4j.jul.LogManager");
    }

    protected static void showSentences(Stream<String> sentences) {
        sentences.forEach(s -> System.out.println(s + "."));
    }
}
