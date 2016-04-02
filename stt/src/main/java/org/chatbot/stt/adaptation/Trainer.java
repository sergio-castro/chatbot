package org.chatbot.stt.adaptation;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;

import org.chatbot.sound.device.Microphone;
import org.chatbot.sound.device.Recorder;
import org.chatbot.tts.MaryVoice;
import org.chatbot.tts.TtsEngine;


public class Trainer {

    private final NumberFormat SECONDS_FORMATTER = new DecimalFormat("#0.0");

    private final AudioFormat format;
    private final Recorder recorder;
    private final TrainerConfiguration config;
    private final TtsEngine ttsEngine;


    public static String getSampleFileName(TrainerConfiguration config, int id) {
        String fileExtension = "wav";
        String fileName = config.getSampleFilePrefix() + "_" +
                leftPadding(id, config.getSampleFileZeroPaddingSize())
                + "." + fileExtension;
        return config.getOutputFolder() + File.separator + fileName;
    }

    public static String leftPadding(int number, int paddingSize) {
        if (paddingSize == 0) {
            return Integer.toString(number);
        } else {
            return String.format("%0" + paddingSize + "d", number);
        }
    }

    public Trainer(TrainerConfiguration config) {
        this.config = config;
        //defined according to: http://cmusphinx.sourceforge.net/wiki/tutorialadapt
        format = new AudioFormat(16000, 16, 1, true, false);
        Microphone micro = new Microphone(format);
        recorder = new Recorder(micro);
        ttsEngine = new TtsEngine(MaryVoice.CMU_SLT_HSMM.id());
    }

    public void start() {
        Path outputPath = Paths.get(config.getOutputFolder());
        if (!Files.isDirectory(outputPath)) {
            try {
                Files.createDirectory(outputPath);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        AtomicInteger index = new AtomicInteger();
        Path path = Paths.get(config.getSourceFile());
        try (Stream<String> lines = Files.lines(path);
             BufferedWriter fileIdsWriter = createFileIdsBufferedWriter();
             BufferedWriter transcriptionWriter = createTranscriptionBufferedWriter()) {
            ConfigurationHelper configHelper = new ConfigurationHelper(fileIdsWriter, transcriptionWriter);
            lines.map(line -> trainFromSentence(line, index.incrementAndGet())).forEach(configHelper::add);
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
    }

    private BufferedWriter createFileIdsBufferedWriter() {
        return createBufferedWriter(config.getOutputFolder() + File.separator + config.getFileIdsName());
    }

    private BufferedWriter createTranscriptionBufferedWriter() {
        return createBufferedWriter(config.getOutputFolder() + File.separator + config.getFileTranscriptionName());
    }

    private static BufferedWriter createBufferedWriter(String fileName) {
        File file = new File(fileName);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
        return bw;
    }

    public FileWithSpeech trainFromSentence(String text, int id) {
        long milliseconds = milliSecondsToSpeech(text);
        String generatedFileName = getSampleFileName(config, id);
        FileWithSpeech generatedFile = new FileWithSpeech(generatedFileName, text);
        String pleaseText = "Please say: ";
        String quotedText = "\"" + text + "\"";
        System.out.println(pleaseText);
        System.out.println(quotedText);
        System.out.println("(You have " + SECONDS_FORMATTER.format(milliseconds / 1000d) + " seconds)");
        ttsEngine.say(pleaseText);
        ttsEngine.say(text);
        System.out.println("Starting recording ...");
        recorder.record(AudioFileFormat.Type.WAVE, generatedFile, milliseconds, TimeUnit.MILLISECONDS);
        System.out.println("Done. Generating file: " + generatedFileName);
        return generatedFile;
    }

    private long milliSecondsToSpeech(String text) {
        return (text.length() * 110) + 600;
    }



    private static class ConfigurationHelper {
        private final BufferedWriter fileIdsWriter;
        private final BufferedWriter transcriptionWriter;

        public ConfigurationHelper(BufferedWriter fileIdsWriter, BufferedWriter transcriptionWriter) {
            this.fileIdsWriter = fileIdsWriter;
            this.transcriptionWriter = transcriptionWriter;
        }

        public void add(FileWithSpeech fileWithSpeech) {
            try {
                fileIdsWriter.write(withoutExtension(fileWithSpeech.getName()));
                fileIdsWriter.newLine();
                transcriptionWriter.write(asTranscriptionEntry(fileWithSpeech));
                transcriptionWriter.newLine();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        public String asTranscriptionEntry(FileWithSpeech fileWithSpeech) {
            return "<s> " + fileWithSpeech.getSpeechText() + " </s> (" + withoutExtension(fileWithSpeech.getName()) + ")";
        }

        private String withoutExtension(String fileName) {
            return com.google.common.io.Files.getNameWithoutExtension(fileName);
        }
    }


    public static class FileWithSpeech extends File {

        private final String speechText;

        public FileWithSpeech(String fileName, String speechText) {
            super(fileName);
            this.speechText = speechText;
        }

        public String getSpeechText() {
            return speechText;
        }
    }

}
