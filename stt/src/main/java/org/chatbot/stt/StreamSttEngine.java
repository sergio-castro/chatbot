package org.chatbot.stt;


import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.cmu.sphinx.api.AbstractSpeechRecognizer;
import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.StreamSpeechRecognizer;

public class StreamSttEngine extends SttEngine {

    private static final Logger log = LoggerFactory.getLogger(StreamSttEngine.class);

    public StreamSttEngine(SttEngineConfiguration config) {
        super(config);
    }

    @Override
    protected AbstractSpeechRecognizer createSphinxEngine(Configuration sphinixConfiguration) {
        try {
            return new StreamSpeechRecognizer(sphinixConfiguration);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private StreamSpeechRecognizer getSphinxEngine() {
        return (StreamSpeechRecognizer) sphinxEngine;
    }

    public Stream<String> stream(InputStream stream, Optional<Predicate<String>> stopCondition) {
        return super.stream(
                () -> getSphinxEngine().startRecognition(stream),
                () -> getSphinxEngine().stopRecognition(),
                stopCondition);
    }

}
