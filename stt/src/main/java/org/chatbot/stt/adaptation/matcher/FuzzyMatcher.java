package org.chatbot.stt.adaptation.matcher;

// http://stackoverflow.com/questions/2314045/best-practice-for-determining-the-probability-that-2-strings-match
public interface FuzzyMatcher {

    boolean matches(String s1, String s2);

}
