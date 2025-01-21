package com.rdb.wordanalyzer.web.request;

import jakarta.validation.constraints.NotBlank;

public class HighestFrequencyRequest {

    @NotBlank private final String text;

    public HighestFrequencyRequest(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
