package org.chatbot.stt.adaptation.matcher;


import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;

import org.junit.Test;

public class DoubleMetaphoneMatcherTest {

    @Test
    public void sameSourceMatchTest() {
        String s1 = "Catherine Smith";
        String s2 = "Katherin Zmith";
        DoubleMetaphoneMatcher matcher = new DoubleMetaphoneMatcher();
        assertThat(matcher.matches(s1, s2)).isTrue();
        System.out.println(matcher.encode(s1));
        System.out.println(matcher.encode(s2));
    }

    @Test
    public void similarSourceMatchTest() {
        String s1 = "Catherine Smith";
        String s2 = "Katherin Zmith";
        DoubleMetaphoneMatcher matcher = new DoubleMetaphoneMatcher();
        assertThat(matcher.matches(s1, s2)).isTrue();
        System.out.println(matcher.encode(s1));
        System.out.println(matcher.encode(s2));
    }

    @Test
    public void distinctSourceMismatchTest() {
        String s1 = "Catherine Smith";
        String s2 = "Catherine Summit";
        DoubleMetaphoneMatcher matcher = new DoubleMetaphoneMatcher();
        assertThat(matcher.matches(s1, s2)).isFalse();
        System.out.println(matcher.encode(s1));
        System.out.println(matcher.encode(s2));
    }

}
