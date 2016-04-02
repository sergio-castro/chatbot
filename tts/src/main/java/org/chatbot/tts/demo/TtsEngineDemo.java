package org.chatbot.tts.demo;


import static marytts.modules.synthesis.Voice.getAvailableVoices;

import java.util.Set;

import org.chatbot.tts.MaryVoice;
import org.chatbot.tts.TtsEngine;
import org.chatbot.tts.VoiceId;

import marytts.LocalMaryInterface;
import marytts.MaryInterface;
import marytts.exceptions.MaryConfigurationException;

public class TtsEngineDemo {

    public static void showAvailableVoices() {
        MaryInterface maryTts = null;
        try {
            maryTts = new LocalMaryInterface();
        } catch (MaryConfigurationException e) {
            throw new RuntimeException(e);
        }
        Set<String> voices = maryTts.getAvailableVoices();
        if (voices.isEmpty()) {
            System.out.println("No voices registered :(");
        } else {
            System.out.println("Available voices: ");
            for (String voice : voices) {
                System.out.println(voice);
            }
        }
    }

    public static void say(String text, VoiceId voiceId) {
        System.out.println("Saying: " + text);
        TtsEngine voice = new TtsEngine(voiceId);
        voice.say(text);
    }

    public static void testVoice(VoiceId voiceId) {
        String text;
        text = "Hello dear. How are you today?";
        say(text, voiceId);
        text = "How can I help you?";
        say(text, voiceId);
        text = "Integration tests started.";
        say(text, voiceId);
        text = "Deploying to Amazon now.";
        say(text, voiceId);
    }

    public static void main(String[] args) {
        showAvailableVoices();
        testVoice(MaryVoice.CMU_SLT_HSMM.id());
        //testVoice(MaryVoice.DFKI_PRUDENCE.id());
        //testVoice(MaryVoice.CMU_RMS_HSMM.id());
        //testVoice(MaryVoice.DFKI_OBADIAH.id());
    }

}
