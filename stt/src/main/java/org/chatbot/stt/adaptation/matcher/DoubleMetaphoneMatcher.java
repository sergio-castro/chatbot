package org.chatbot.stt.adaptation.matcher;


import static java.util.Arrays.stream;

import java.util.stream.Collectors;

import org.apache.commons.codec.language.DoubleMetaphone;

public class DoubleMetaphoneMatcher implements FuzzyMatcher {

    private final DoubleMetaphone doubleMetaphone;

    public DoubleMetaphoneMatcher() {
        this.doubleMetaphone = new DoubleMetaphone();
    }

    @Override
    public boolean matches(String s1, String s2) {
        return encode(s1).equals(encode(s2));
    }

    public String encode(String s) {
        String[] tokens = s.split("[^a-zA-Z]");
        return stream(tokens)
                .filter(token -> !token.isEmpty())
                .map(token -> doubleMetaphone.doubleMetaphone(token))
                .collect(Collectors.joining(" "));
    }

}
