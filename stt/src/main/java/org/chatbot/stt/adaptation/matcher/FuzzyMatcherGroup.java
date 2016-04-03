package org.chatbot.stt.adaptation.matcher;


import java.util.List;

public class FuzzyMatcherGroup implements FuzzyMatcher {

    private final List<FuzzyMatcher> strategies;

    public FuzzyMatcherGroup(List<FuzzyMatcher> strategies) {
        this.strategies = strategies;
    }

    @Override
    public boolean matches(final String s1, final String s2) {
        return strategies.stream()
                .filter(strategy -> strategy.matches(s1, s2))
                .findFirst()
                .isPresent();
    }
}
