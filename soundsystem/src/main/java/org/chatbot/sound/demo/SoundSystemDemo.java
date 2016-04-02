package org.chatbot.sound.demo;


import static java.util.concurrent.TimeUnit.SECONDS;

import javax.sound.sampled.AudioFormat;

import org.chatbot.sound.device.Microphone;
import org.chatbot.sound.device.Speakers;

public class SoundSystemDemo {

    public static void main(String[] args) {
        AudioFormat format = new AudioFormat(44100, 16, 2, true, true);
        Microphone micro = new Microphone(format);
        Speakers speakers = new Speakers(format);
        micro.open();
        speakers.open();
        micro.start(3, SECONDS);
        speakers.start();
        speakers.write(micro.stream());
        speakers.stop();
    }

}
