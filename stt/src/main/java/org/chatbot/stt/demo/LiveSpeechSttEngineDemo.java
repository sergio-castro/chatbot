package org.chatbot.stt.demo;


import java.util.Optional;

import org.chatbot.stt.LiveSpeechSttEngine;
import org.chatbot.stt.SttEngineConfiguration;

public class LiveSpeechSttEngineDemo extends AbstractDemo {

    static {
        System.setProperty("java.util.logging.manager", "org.apache.logging.log4j.jul.LogManager");
    }

    public static void main(String[] args) {
        LiveSpeechSttEngine engine = new LiveSpeechSttEngine(SttEngineConfiguration.standardConfig());
        showSentences(engine.stream(Optional.empty()));
    }

}
