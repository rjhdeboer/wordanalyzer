package com.rdb.wordanalyzer.domain;

import java.util.Objects;

// TODO: record?
public class SimpleWordFrequency implements WordFrequency {

    private final String word;
    private final int frequency;

    public SimpleWordFrequency(String word, int frequency) {
        this.word = word;
        this.frequency = frequency;
    }

    @Override
    public String getWord() {
        return word;
    }

    @Override
    public int getFrequency() {
        return frequency;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof SimpleWordFrequency that)) {
            return false;
        }
        return getFrequency() == that.getFrequency() && Objects.equals(getWord(), that.getWord());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getWord(), getFrequency());
    }

    @Override
    public String toString() {
        return word + ":" + frequency;
    }
}
