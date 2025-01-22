package com.rdb.wordanalyzer.service;

import com.rdb.wordanalyzer.domain.WordFrequency;

import java.util.List;

/**
 * A service which provides word analysis functions. A 'word' is defined as a sequence of 1 or more characters (a-z A-Z). All words ignore capitalization and the results are always
 * returned in lowercase.
 */
public interface WordFrequencyAnalyzer {

    /**
     * Calculates the highest frequency of any word in the given text. For example, in the text "This is a sentence with a repeated word", the highest frequency of any word is 2
     * (due to the word 'a').
     *
     * Note that multiple words may have this highest frequency.
     *
     * @param text text to analyse
     * @return the highest frequency of any word
     */
    int calculateHighestFrequency(String text);

    /**
     * Calculates the frequency of a given word in a given text. For example, in the text "This is a sentence with a repeated word", the frequency of the word 'a' is 2.
     *
     * @param text text to analyse
     * @param word word to find the frequency of
     * @return the frequency of the word in the given text
     */
    int calculateFrequencyForWord(String text, String word);

    /**
     * Calculates the most frequent "n" amount of words in a given text. For example, in the text "This is a sentence with a repeated word and that is a fact", with 'n' being 3,
     * the result is: [a:3, is:2, and:1]
     *
     * Note that multiple words may share the same frequency. All words with within the same frequency are alphabetically sorted.
     *
     * @param text text to analyse
     * @param n limit to the amount of word frequencies to return
     * @return list of word frequencies
     */
    List<WordFrequency> calculateMostFrequentNWords(String text, int n);
}