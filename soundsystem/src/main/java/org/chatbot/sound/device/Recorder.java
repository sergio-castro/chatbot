package org.chatbot.sound.device;


import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;

public class Recorder {

    private final Microphone micro;

    public Recorder(Microphone micro) {
        this.micro = micro;
    }

    public void record(AudioFileFormat.Type fileType, File file) {
        micro.open();
        micro.start();
        AudioInputStream ais = new AudioInputStream(micro.getTargetDataLine());
        try {
            AudioSystem.write(ais, fileType, file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void record(AudioFileFormat.Type fileType, File file, long delay, TimeUnit timeUnit) {
        micro.open();
        micro.start(delay, timeUnit);
        AudioInputStream ais = new AudioInputStream(micro.getTargetDataLine());
        try {
            AudioSystem.write(ais, fileType, file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
