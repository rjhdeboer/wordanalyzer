package com.rdb.wordanalyzer.web.response;

public class FrequencyResponse {

    private final int frequency;

    public FrequencyResponse(int frequency) {
        this.frequency = frequency;
    }

    public int getFrequency() {
        return frequency;
    }
}
