package org.chatbot.stt.demo;


import org.chatbot.stt.adaptation.Trainer;
import org.chatbot.stt.adaptation.TrainerConfiguration;

public class TrainerDemo extends AbstractDemo {

    public static void main(String[] args) {
        Trainer trainer = new Trainer(TrainerConfiguration.builder().build());
        trainer.start();
    }

}
