package org.chatbot.stt;


import java.io.InputStream;
import java.util.Iterator;
import java.util.Optional;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Predicate;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.AbstractIterator;

import edu.cmu.sphinx.api.AbstractSpeechRecognizer;
import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.SpeechResult;
import edu.cmu.sphinx.result.Result;

public abstract class SttEngine {

    private static final Logger log = LoggerFactory.getLogger(SttEngine.class);

    protected final SttEngineConfiguration config;
    protected final AbstractSpeechRecognizer sphinxEngine;

    public SttEngine(SttEngineConfiguration config) {
        this.config = config;
        sphinxEngine = createSphinxEngine(asSphinxConfiguration(config));
    }

    protected abstract AbstractSpeechRecognizer createSphinxEngine(Configuration sphinixConfiguration);

    private Configuration asSphinxConfiguration(SttEngineConfiguration config) {
        Configuration sphinxConfig = new Configuration();
        sphinxConfig
                .setAcousticModelPath(config.getAcousticModelPath());
        sphinxConfig
                .setDictionaryPath(config.getDictionaryPath());
        sphinxConfig
                .setLanguageModelPath(config.getLanguageModelPath());
        return sphinxConfig;
    }

    protected Stream<String> stream(Iterator<String> it) {
        return StreamSupport.stream(
                Spliterators.spliteratorUnknownSize(it, Spliterator.ORDERED),
                false);
    }

    protected boolean isEndResult(Optional<String> hypothesis, Optional<Predicate<String>> stopCondition) {
        return !hypothesis.isPresent()
                || (stopCondition.isPresent() && stopCondition.get().test(hypothesis.get()));
    }

    protected Optional<String> getHypothesis() {
        String hypothesis = null;
        SpeechResult result = sphinxEngine.getResult();
        if (result != null) {
            hypothesis = result.getHypothesis();
        }
        return Optional.ofNullable(hypothesis);
    }

    protected Stream<String> stream(Runnable startRecognition, Runnable stopRecognition,
                                    Optional<Predicate<String>> stopCondition) {
        log.debug("Starting recognition.");
        startRecognition.run();
        SpeechResult result;
        Iterator<String> it = new AbstractIterator<String>() {
            @Override
            protected String computeNext() {
                Optional<String> hypothesis = getHypothesis();
                if (!isEndResult(hypothesis, stopCondition)) {
                    log.debug("Found hypothesis: " + hypothesis.get());
                    return hypothesis.get();
                } else {
                    if (hypothesis.isPresent()) {
                        log.debug("Stop keyword found: " + hypothesis.get());
                    }
                    log.debug("Stopping recognition.");
                    stopRecognition.run();
                    return endOfData();
                }
            }
        };
        return stream(it);
    }

}
