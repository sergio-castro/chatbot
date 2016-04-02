package org.chatbot.stt.demo;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Optional;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.chatbot.sound.demo.RecorderDemo;
import org.chatbot.stt.StreamSttEngine;
import org.chatbot.stt.SttEngineConfiguration;
import org.chatbot.stt.adaptation.Trainer;
import org.chatbot.stt.adaptation.TrainerConfiguration;

public class StreamSttEngineDemo extends AbstractDemo {

    private static final String SAMPLE_DIR = "src/main/resources/";

    private static final String RESOURCES_PATH = "stt/target/classes/adaptation/training-data/";

    private static final String SAMPLES_PATH = RESOURCES_PATH;

    private static final TrainerConfiguration DEFAULT_TRAINER_CONFIGURATION = TrainerConfiguration.builder().build();

    public static void main(String[] args) {
        //StreamSttEngine engine = new StreamSttEngine(SttEngineConfiguration.standardConfig());
        StreamSttEngine engine = new StreamSttEngine(SttEngineConfiguration.adaptedConfig(RESOURCES_PATH));

        //showSentences(stream(engine, new File(RecorderDemo.SAMPLE_FILE)));

        IntStream.range(1, 7).mapToObj(StreamSttEngineDemo::soundFileName)
                .forEach(fileName -> showSentences(stream(engine, new File(fileName))));
    }

    private static Stream<String> stream(StreamSttEngine engine, File wavFile) {
        try {
            return engine.stream(new FileInputStream(wavFile), Optional.empty());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private static String soundFileName(int fileId) {
        return Trainer.getSampleFileName(DEFAULT_TRAINER_CONFIGURATION, fileId);
    }

}
