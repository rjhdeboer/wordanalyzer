package com.rdb.wordanalyzer.domain;

public record SimpleWordFrequency(String word, int frequency) implements WordFrequency {

    public String getWord() {
        return word();
    }

    public int getFrequency() {
        return frequency();
    }

    public String toString() {
        return word() + ":" + frequency();
    }
}
