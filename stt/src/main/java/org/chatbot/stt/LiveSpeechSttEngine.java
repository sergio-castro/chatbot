package org.chatbot.stt;


import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.AbstractIterator;

import edu.cmu.sphinx.api.AbstractSpeechRecognizer;
import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.LiveSpeechRecognizer;
import edu.cmu.sphinx.api.SpeechResult;
import edu.cmu.sphinx.api.StreamSpeechRecognizer;

public class LiveSpeechSttEngine extends SttEngine {

    private static final Logger log = LoggerFactory.getLogger(LiveSpeechSttEngine.class);

    public LiveSpeechSttEngine(SttEngineConfiguration config) {
        super(config);
    }

    @Override
    protected AbstractSpeechRecognizer createSphinxEngine(final Configuration sphinixConfiguration) {
        try {
            return new LiveSpeechRecognizer(sphinixConfiguration);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private LiveSpeechRecognizer getSphinxEngine() {
        return (LiveSpeechRecognizer) sphinxEngine;
    }

    public Stream<String> stream(Optional<Predicate<String>> stopCondition) {
        return super.stream(
                () -> getSphinxEngine().startRecognition(true),
                () -> getSphinxEngine().stopRecognition(),
                stopCondition);
    }

}
