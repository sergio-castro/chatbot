package org.chatbot.sound.device;


import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;

import org.chatbot.sound.SoundPackage;

import com.google.common.collect.AbstractIterator;

public class Microphone extends AudioDevice {
    private final int soundPackageSize;

    public Microphone(AudioFormat format) {
        super(format, TargetDataLine.class);
        this.soundPackageSize = getBufferSize() / 5;
    }

    public Microphone(AudioFormat format, int soundPackageSize) {
        super(format, TargetDataLine.class);
        this.soundPackageSize = soundPackageSize;
    }

    @Override
    public void open() {
        try {
            getTargetDataLine().open(getFormat());
        } catch (LineUnavailableException e) {
            throw new RuntimeException(e);
        }
    }

    public void start(long delay, TimeUnit timeUnit) {
        start();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                Microphone.this.close();
            }
        };
        new Timer(true).schedule(timerTask, TimeUnit.MILLISECONDS.convert(delay, timeUnit));
    }

    public TargetDataLine getTargetDataLine() {
        return (TargetDataLine) getLine();
    }

    public SoundPackage read() {
        byte[] buffer = new byte[soundPackageSize];
        int bytesRead = getTargetDataLine().read(buffer, 0, buffer.length);
        return SoundPackage.fromBuffer(buffer, bytesRead);
    }

    private Iterator<SoundPackage> iterator() {
        return new AbstractIterator<SoundPackage>() {
            @Override
            public SoundPackage computeNext() {
                SoundPackage soundPackage = read();
                if (soundPackage.isEmpty() && !Microphone.this.isRunning()) {
                    return endOfData();
                } else {
                    return soundPackage;
                }
            }
        };
    }

    public Stream<SoundPackage> stream() {
        return StreamSupport.stream(
                Spliterators.spliteratorUnknownSize(iterator(), Spliterator.ORDERED),
                false);
    }

}
