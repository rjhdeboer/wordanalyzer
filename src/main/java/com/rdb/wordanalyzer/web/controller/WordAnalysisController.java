package com.rdb.wordanalyzer.web.controller;

import com.rdb.wordanalyzer.domain.WordFrequency;
import com.rdb.wordanalyzer.service.WordFrequencyAnalyzer;
import com.rdb.wordanalyzer.web.request.FrequencyForWordRequest;
import com.rdb.wordanalyzer.web.request.HighestFrequencyRequest;
import com.rdb.wordanalyzer.web.request.MostFrequentNWordsRequest;
import com.rdb.wordanalyzer.web.response.FrequencyResponse;
import com.rdb.wordanalyzer.web.response.MostFrequentWordsResponse;
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
    public FrequencyResponse calculateHighestFrequency(@RequestBody @Valid HighestFrequencyRequest request) {
        int highestFrequency = wordFrequencyAnalyzer.calculateHighestFrequency(request.getText());
        return new FrequencyResponse(highestFrequency);
    }

    @PostMapping("/word")
    public FrequencyResponse calculateFrequencyForWord(@RequestBody @Valid FrequencyForWordRequest request) {
        int frequencyForWord = wordFrequencyAnalyzer.calculateFrequencyForWord(request.getText(), request.getWord());
        return new FrequencyResponse(frequencyForWord);
    }

    @PostMapping("/most")
    public MostFrequentWordsResponse calculateMostFrequentNWords(@RequestBody @Valid MostFrequentNWordsRequest request) {
        List<WordFrequency> wordFrequencies = wordFrequencyAnalyzer.calculateMostFrequentNWords(request.getText(), request.getLimit());
        return new MostFrequentWordsResponse(wordFrequencies);
    }
}
