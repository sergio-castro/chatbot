package org.chatbot.tts;


import static org.chatbot.tts.VoiceId.*;

public enum MaryVoice {

    DFKI_OBADIAH_HSMM("dfki-obadiah-hsmm"), //quiet, conversational male voice
    CMU_RMS_HSMM("cmu-rms-hsmm"), //assertive male voice
    DFKI_POPPY_HSMM("dfki-poppy-hsmm"), //friendly female voice, GB accent
    CMU_BDL_HSMM("cmu-bdl-hsmm"), //energic male voice
    DFKI_POPPY("dfki-poppy"), //friendly female voice
    CMU_SLT("cmu-slt"), //female, robot-like voice, a bit of distortion
    DFKI_SPIKE("dfki-spike"), //male, robot-like voice
    DFKI_PRUDENCE("dfki-prudence"), //female, mature and friendly voice
    DFKI_OBADIAH("dfki-obadiah"), //male, calm
    DFKI_SPIKE_HSMM("dfki-spike-hsmm"), //male, with emphasis in the pronunciation
    DFKI_PRUDENCE_HSMM("dfki-prudence-hsmm"), //female, a bit of distortion
    CMU_SLT_HSMM("cmu-slt-hsmm");

    private final String id;

    MaryVoice(String id) {
        this.id = id;
    }

    public VoiceId id() {
        return voiceId(id);
    }

}
