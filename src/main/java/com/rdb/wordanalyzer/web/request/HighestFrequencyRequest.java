package com.rdb.wordanalyzer.web.request;

import jakarta.validation.constraints.NotBlank;

public record HighestFrequencyRequest(@NotBlank String text) {
}
