package org.chatbot.sound.demo;


import static java.util.concurrent.TimeUnit.SECONDS;

import java.io.File;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;

import org.chatbot.sound.device.Microphone;
import org.chatbot.sound.device.Player;
import org.chatbot.sound.device.Recorder;

public class RecorderDemo {

    public static void record(AudioFileFormat.Type fileType, File file) {
        //AudioFormat format = new AudioFormat(44100, 16, 2, true, true);
        AudioFormat format = new AudioFormat(16000, 16, 1, true, false);
        Recorder recorder = new Recorder(new Microphone(format));
        recorder.record(fileType, file, 3, SECONDS);
    }

    public static void play(File file) {
        new Player().synchronousPlay(file);
    }

    private static final String FILE_LOCATION = "/tmp";
    private static final String FILE_NAME = "sample.wav";
    public static final String SAMPLE_FILE = FILE_LOCATION + File.separator + "sample.wav";

    public static void main(String[] args) {
        File file = new File(SAMPLE_FILE);
        System.out.println("recording now...");
        record(AudioFileFormat.Type.WAVE, file);
        System.out.println("end recording.");
        System.out.println("playing record...");
        play(file);
        System.out.println("end playing.");
    }

}
