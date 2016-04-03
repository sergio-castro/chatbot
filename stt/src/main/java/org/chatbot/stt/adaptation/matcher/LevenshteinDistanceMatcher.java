package org.chatbot.stt.adaptation.matcher;


import java.util.function.IntUnaryOperator;
import java.util.function.ToIntFunction;

import org.apache.commons.text.similarity.LevenshteinDistance;

public class LevenshteinDistanceMatcher implements FuzzyMatcher {

    private final ToIntFunction<String> thresholdFunction;

    public LevenshteinDistanceMatcher(int threshold) {
        this(s -> threshold);
    }

    public LevenshteinDistanceMatcher(ToIntFunction<String> thresholdFunction) {
        this.thresholdFunction = thresholdFunction;
    }

    @Override
    public boolean matches(String s1, String s2) {
        int threshold = thresholdFunction.applyAsInt(s1);
        LevenshteinDistance levenshteinDistance = new LevenshteinDistance(threshold);
        int distance = levenshteinDistance.apply(s1, s2);
        return distance >= 0 && distance <= threshold;
    }


    public static class LengthDependingThresholdFunction implements ToIntFunction<String> {

        private final IntUnaryOperator lengthToThresoldFunction;

        public LengthDependingThresholdFunction(IntUnaryOperator lengthToThresoldFunction) {
            this.lengthToThresoldFunction = lengthToThresoldFunction;
        }

        @Override
        public int applyAsInt(String value) {
            return lengthToThresoldFunction.applyAsInt(value.length());
        }
    }

}
