package com.rdb.wordanalyzer.domain;

/**
 * A simple representation of how frequent a given word appears in a text.
 */
public interface WordFrequency {

    /**
     * @return the word that appeared in the text
     */
    String getWord();

    /**
     * @return the frequency of the word in the text
     */
    int getFrequency();
}