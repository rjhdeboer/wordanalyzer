package com.rdb.wordanalyzer.web.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record MostFrequentNWordsRequest(@NotBlank String text, @Positive int limit) {
}
