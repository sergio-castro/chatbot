
package org.chatbot.stt.adaptation.matcher;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import groovy.transform.ASTTest;

public class LevenshteinDistanceMatcherTest {

    @Test
    public void testExactMatch() {
        String s1 = "x";
        String s2 = "x";
        LevenshteinDistanceMatcher matcher = new LevenshteinDistanceMatcher(0);
        assertThat(matcher.matches(s1, s2)).isTrue();
    }

    @Test
    public void testEmptyStringsMatch() {
        String s1 = "";
        String s2 = "";
        LevenshteinDistanceMatcher matcher = new LevenshteinDistanceMatcher(0);
        assertThat(matcher.matches(s1, s2)).isTrue();
    }

    @Test
    public void testZeroToleranceMismatch() {
        String s1 = "x";
        String s2 = "xy";
        LevenshteinDistanceMatcher matcher = new LevenshteinDistanceMatcher(0);
        assertThat(matcher.matches(s1, s2)).isFalse();
        assertThat(matcher.matches(s2, s1)).isFalse();
    }

    @Test
    public void testMinimumToleranceMatch() {
        String s1 = "x";
        String s2 = "xy";
        LevenshteinDistanceMatcher matcher = new LevenshteinDistanceMatcher(1);
        assertThat(matcher.matches(s1, s2)).isTrue();
        assertThat(matcher.matches(s2, s1)).isTrue();
    }

    @Test
    public void testMinimumToleranceMismatch() {
        String s1 = "x";
        String s2 = "xyy";
        LevenshteinDistanceMatcher matcher = new LevenshteinDistanceMatcher(1);
        assertThat(matcher.matches(s1, s2)).isFalse();
        assertThat(matcher.matches(s2, s1)).isFalse();
    }

    @Test
    public void testMediumToleranceMatch() {
        String s1 = "hello";
        String s2 = "xhellox";
        LevenshteinDistanceMatcher matcher = new LevenshteinDistanceMatcher(2);
        assertThat(matcher.matches(s1, s2)).isTrue();
        assertThat(matcher.matches(s2, s1)).isTrue();
    }

}
