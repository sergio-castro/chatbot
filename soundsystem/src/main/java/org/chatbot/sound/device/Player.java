package org.chatbot.sound.device;


import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * Adapted from: http://www.codejava.net/coding/how-to-play-back-audio-in-java-with-examples
 */
public class Player {

    private final Object monitor = new Object();
    private volatile boolean done = false;
    private volatile Clip clip;


    /**
     * If called with the synchronously flag set, close() does not need to be explicitly called.
     */
    private void play(File file, boolean synchronously) {
        close();
        AudioInputStream audioIn = null;
        try {
            audioIn = AudioSystem.getAudioInputStream(file);
            clip = AudioSystem.getClip();
            clip.addLineListener((LineEvent event) -> {
                if (event.getType().equals(LineEvent.Type.STOP)) {
                    done = true; monitor.notifyAll();
                }
            });
            clip.open(audioIn);
            clip.start();
            if (synchronously) {
                try {
                    while (!done) {
                        monitor.wait();
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                close();
            }
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            throw new RuntimeException(e);
        }

    }

    public void play(File file) {
        play(file, false);
    }

    public void close() {
        if (clip != null) {
            clip.close();
            clip = null;
        }
    }

    public void synchronousPlay(File file) {
        synchronized (monitor) {
            play(file, true);
        }
    }

}
