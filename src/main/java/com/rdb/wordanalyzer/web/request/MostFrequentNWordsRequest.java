package com.rdb.wordanalyzer.web.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public class MostFrequentNWordsRequest {

    @NotBlank private final String text;
    @Positive private final int limit;

    public MostFrequentNWordsRequest(String text, int limit) {
        this.text = text;
        this.limit = limit;
    }

    public String getText() {
        return text;
    }

    public int getLimit() {
        return limit;
    }
}
