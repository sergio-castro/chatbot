package org.chatbot.sound;


import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;

public class SoundPackage {

    private final byte[] buffer;
    private final int size;

    private SoundPackage(byte[] buffer, int size) {
        this.buffer = buffer;
        this.size = size;
    }

    public static SoundPackage fromBuffer(byte[] buffer, int size) {
        Preconditions.checkArgument(size >= 0 && size <= buffer.length);
        return new SoundPackage(buffer, size);
    }

    public int getBufferSize() {
        return buffer.length;
    }

    public byte[] getBuffer() {
        return buffer;
    }

    public int getSize() {
        return size;
    }

    public boolean isEmpty() {
        return getSize() == 0;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("buffer", buffer)
                .add("size", size)
                .toString();
    }
}
