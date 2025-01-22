package com.rdb.wordanalyzer.web.request;

import jakarta.validation.constraints.NotBlank;

public record FrequencyForWordRequest(@NotBlank String text, @NotBlank String word) {
}
