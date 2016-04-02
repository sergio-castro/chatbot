package org.chatbot.tts;


import java.util.Objects;

public class VoiceId {

    private final String id;

    private VoiceId(String id) {
        this.id = id;
    }

    public static VoiceId voiceId(String id) {
        return new VoiceId(id);
    }

    public String get() {
        return id;
    }

    @Override
    public String toString() {
        return id;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final VoiceId voiceId = (VoiceId) o;
        return Objects.equals(id, voiceId.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
