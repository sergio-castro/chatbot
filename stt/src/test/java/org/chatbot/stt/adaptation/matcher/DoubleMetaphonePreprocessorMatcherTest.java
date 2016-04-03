package org.chatbot.stt.adaptation.matcher;


import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class DoubleMetaphonePreprocessorMatcherTest {

    @Test
    public void similarSourceMatchTest() {
        String s1 = "Catherine Smith";
        String s2 = "Catherine Smit";
        DoubleMetaphoneMatcher matcher = new DoubleMetaphoneMatcher();
        // a simple match with the DoubleMetaphoneMatcher fails.
        assertThat(matcher.matches(s1, s2)).isFalse();
        // a softer match, employing the Levenshtein distance with a small threshold, succeeds.
        DoubleMetaphonePreprocessorMatcher softerMatcher =
                new DoubleMetaphonePreprocessorMatcher(new LevenshteinDistanceMatcher(1));
        assertThat(softerMatcher.matches(s1, s2)).isTrue();

    }

}
