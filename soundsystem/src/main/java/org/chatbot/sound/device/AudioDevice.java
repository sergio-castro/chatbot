package org.chatbot.sound.device;


import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;

public abstract class AudioDevice {

    private final AudioFormat format;
    private final DataLine.Info info;
    private DataLine line;

    public AudioDevice(AudioFormat format, Class<? extends DataLine> dataLineClass) {
        this.format = format;
        info = new DataLine.Info(dataLineClass, format);
        if (!AudioSystem.isLineSupported(info)) {
            throw new RuntimeException("Line not supported");
        }
    }

    protected DataLine getLine() {
        if (line == null) {
            try {
                line = (DataLine) AudioSystem.getLine(info);
            } catch (LineUnavailableException e) {
                throw new RuntimeException(e);
            }
        }
        return line;
    }

    public AudioFormat getFormat() {
        return format;
    }

    public DataLine.Info getInfo() {
        return info;
    }

    public abstract void open();

    public void close() {
        if (line != null) {
            line.close();
            line = null;
        }
    }

    public boolean isRunning() {
        return getLine().isRunning();
    }

    public void start() {
        getLine().start();
    }

    public void stop() {
        getLine().stop();
    }

    public int getBufferSize() {
        return getLine().getBufferSize();
    }

}
