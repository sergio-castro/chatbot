package org.chatbot.tts;

import javax.sound.sampled.AudioInputStream;

import marytts.LocalMaryInterface;
import marytts.MaryInterface;
import marytts.exceptions.MaryConfigurationException;
import marytts.exceptions.SynthesisException;
import marytts.util.data.audio.AudioPlayer;

/**
 * Disclaimer:
 * Adaped from: http://lukealderton.com/blog/posts/2013/december/using-marytts-or-openmary-in-java/
 */
public class TtsEngine {
    private final MaryInterface maryTts;

    public TtsEngine(VoiceId voiceId) {
        try {
            maryTts =  new LocalMaryInterface();
            maryTts.setVoice(voiceId.get());
        } catch (MaryConfigurationException e) {
            throw new RuntimeException(e);
        }

    }

    private void say(String text, boolean synchronously) {
        AudioInputStream audio;
        try {
            audio = maryTts.generateAudio(text);
        } catch (SynthesisException e) {
            throw new RuntimeException(text);
        }
        AudioPlayer audioPlayer = new AudioPlayer();
        audioPlayer.setAudio(audio);
        audioPlayer.start();
        if (synchronously) {
            try {
                audioPlayer.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void say(String text) {
        say(text, true);
    }

    public void asynchronousSay(String text) {
        say(text, false);
    }

}
