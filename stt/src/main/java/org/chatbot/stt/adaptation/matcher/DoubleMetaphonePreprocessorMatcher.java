package org.chatbot.stt.adaptation.matcher;


public class DoubleMetaphonePreprocessorMatcher implements FuzzyMatcher {

    private final DoubleMetaphoneMatcher doubleMetaphoneMatcher;
    private final FuzzyMatcher fuzzyMatcher;

    public DoubleMetaphonePreprocessorMatcher(FuzzyMatcher fuzzyMatcher) {
        doubleMetaphoneMatcher = new DoubleMetaphoneMatcher();
        this.fuzzyMatcher = fuzzyMatcher;
    }

    @Override
    public boolean matches(final String s1, final String s2) {
        return fuzzyMatcher.matches(
                doubleMetaphoneMatcher.encode(s1),
                doubleMetaphoneMatcher.encode(s2));
    }

}
