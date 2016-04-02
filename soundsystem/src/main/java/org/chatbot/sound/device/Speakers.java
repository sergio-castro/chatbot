package org.chatbot.sound.device;


import java.util.stream.Stream;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

import org.chatbot.sound.SoundPackage;

public class Speakers extends AudioDevice {

    public Speakers(AudioFormat format) {
        super(format, SourceDataLine.class);
    }

    @Override
    public void open() {
        try {
            getSourceDataLine().open(getFormat());
        } catch (LineUnavailableException e) {
            throw new RuntimeException(e);
        }
    }

    public SourceDataLine getSourceDataLine() {
        return (SourceDataLine) getLine();
    }

    public int write(SoundPackage soundPackage) {
        return getSourceDataLine().write(soundPackage.getBuffer(), 0, soundPackage.getSize());
    }

    public void writeOrThrow(SoundPackage soundPackage) {
        int written = write(soundPackage);
        if (written < soundPackage.getSize()) {
            throw new RuntimeException("Unable to write to speakers. Package size: " + soundPackage.getSize() +
                    " Written: " + written + ".");
        }
    }

    public void write(Stream<SoundPackage> stream) {
        stream.forEach(this::writeOrThrow);
    }

}
