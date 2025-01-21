package com.rdb.wordanalyzer.web.request;

import jakarta.validation.constraints.NotBlank;

public class FrequencyForWordRequest {

    @NotBlank private final String text;
    @NotBlank private final String word;

    public FrequencyForWordRequest(String text, String word) {
        this.text = text;
        this.word = word;
    }

    public String getText() {
        return text;
    }

    public String getWord() {
        return word;
    }
}
