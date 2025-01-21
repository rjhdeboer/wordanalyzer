package com.rdb.wordanalyzer.service;

import com.rdb.wordanalyzer.domain.WordFrequency;

import java.util.List;

public interface WordFrequencyAnalyzer {

    int calculateHighestFrequency(String text);

    int calculateFrequencyForWord(String text, String word);

    List<WordFrequency> calculateMostFrequentNWords(String text, int n);
}