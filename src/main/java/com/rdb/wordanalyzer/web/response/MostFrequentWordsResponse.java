package com.rdb.wordanalyzer.web.response;

import com.rdb.wordanalyzer.domain.WordFrequency;

import java.util.List;

public class MostFrequentWordsResponse {

    private final List<WordFrequencyResult> wordFrequencies;

    public MostFrequentWordsResponse(List<WordFrequency> results) {
        this.wordFrequencies = results.stream()
                .map(result -> new WordFrequencyResult(result.getWord(), result.getFrequency()))
                .toList();
    }

    public List<WordFrequencyResult> getWordFrequencies() {
        return wordFrequencies;
    }

    public static class WordFrequencyResult {
        private final String word;
        private final int frequency;

        public WordFrequencyResult(String word, int frequency) {
            this.word = word;
            this.frequency = frequency;
        }

        public String getWord() {
            return word;
        }

        public int getFrequency() {
            return frequency;
        }
    }
}
