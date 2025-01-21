package com.rdb.wordanalyzer.web.controller;

import com.rdb.wordanalyzer.domain.WordFrequency;
import com.rdb.wordanalyzer.service.WordFrequencyAnalyzer;
import com.rdb.wordanalyzer.web.request.FrequencyForWordRequest;
import com.rdb.wordanalyzer.web.request.HighestFrequencyRequest;
import com.rdb.wordanalyzer.web.request.MostFrequentNWordsRequest;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/frequencies")
public class WordAnalysisController {

    private final WordFrequencyAnalyzer wordFrequencyAnalyzer;

    public WordAnalysisController(WordFrequencyAnalyzer wordFrequencyAnalyzer) {
        this.wordFrequencyAnalyzer = wordFrequencyAnalyzer;
    }

    @PostMapping("/highest")
    public int calculateHighestFrequency(@RequestBody @Valid HighestFrequencyRequest request) {
        return wordFrequencyAnalyzer.calculateHighestFrequency(request.getText());
    }

    @PostMapping("/word")
    public int calculateFrequencyForWord(@RequestBody @Valid FrequencyForWordRequest request) {
        return wordFrequencyAnalyzer.calculateFrequencyForWord(request.getText(), request.getWord());
    }

    @PostMapping("/most")
    public List<WordFrequency> calculateMostFrequentNWords(@RequestBody @Valid MostFrequentNWordsRequest request) {
        return wordFrequencyAnalyzer.calculateMostFrequentNWords(request.getText(), request.getLimit());
    }
}
