package com.rdb.wordanalyzer.service;

import com.rdb.wordanalyzer.domain.SimpleWordFrequency;
import com.rdb.wordanalyzer.domain.WordFrequency;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class RegexWordFrequencyAnalyzer implements WordFrequencyAnalyzer {

    private static final Pattern WORD_PATTERN = Pattern.compile("[a-zA-Z]+");

    @Override
    public int calculateHighestFrequency(String text) {
        Assert.hasText(text, "Text should not be null or empty");

        Map<String, Integer> wordCounts = computeWordCounts(text);
        return wordCounts.values().stream()
                .max(Integer::compareTo)
                .orElse(0);
    }

    @Override
    public int calculateFrequencyForWord(String text, String word) {
        Assert.hasText(text, "Text should not be null or empty");
        Assert.hasText(word, "Word should not be null or empty");
        Assert.isTrue(WORD_PATTERN.matcher(word).matches(), "Word should only contains alphabetical characters");

        Map<String, Integer> wordCounts = computeWordCounts(text);
        return wordCounts.getOrDefault(word.toLowerCase(), 0);
    }

    @Override
    public List<WordFrequency> calculateMostFrequentNWords(String text, int n) {
        Assert.hasText(text, "Text should not be null or empty");
        Assert.isTrue(n > 0, "Number should be higher than zero");

        Map<String, Integer> wordCounts = computeWordCounts(text);
        List<WordFrequency> results = new ArrayList<>();
        wordCounts.entrySet().stream()
                .sorted((firstEntry, secondEntry) -> secondEntry.getValue().compareTo(firstEntry.getValue()))
                .limit(n)
                .map(entry -> new SimpleWordFrequency(entry.getKey(), entry.getValue()))
                .forEach(results::add);
        return results;
    }

    private Map<String, Integer> computeWordCounts(String text) {
        Matcher matcher = WORD_PATTERN.matcher(text);
        Map<String, Integer> wordFrequencies = new TreeMap<>();
        while (matcher.find()) {
            String matchedWord = matcher.group();
            wordFrequencies.compute(matchedWord.toLowerCase(), (word, count) -> count == null ? 1 : ++count);
        }
        return wordFrequencies;
    }
}
