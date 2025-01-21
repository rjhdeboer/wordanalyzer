package com.rdb.wordanalyzer.service;

import com.rdb.wordanalyzer.domain.SimpleWordFrequency;
import com.rdb.wordanalyzer.domain.WordFrequency;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RegexWordFrequencyAnalyzerTest {

    private static final String FREQUENT_N_WORDS_TEXT =
            """
            This is a larger piece of text that contains more words and sentences than other tests. Especially the words 'is' and 'a' are represented multiple times.
            That is a nice way to see if this method works or not.
            """;

    private RegexWordFrequencyAnalyzer analyzer;

    @BeforeEach
    void setUp() {
        analyzer = new RegexWordFrequencyAnalyzer();
    }

    @Test
    void calculateHighestFrequencyNoRepeatedWords() {
        assertEquals(1, analyzer.calculateHighestFrequency("This is a sentence with some words"));
    }

    @Test
    void calculateHighestFrequencyRepeatedWords() {
        assertEquals(2, analyzer.calculateHighestFrequency("This is a sentence with a repeated word"));
    }

    @Test
    void calculateHighestFrequencyRepeatedWordsCapitalized() {
        assertEquals(2, analyzer.calculateHighestFrequency("This is a sentence with this repeated word"));
    }

    @Test
    void calculateHighestFrequencyMultipleRepeatedWords() {
        assertEquals(2, analyzer.calculateHighestFrequency("This is a sentence where a different repeated word is also present"));
    }

    @Test
    void calculateHighestFrequencyMultipleHighestFrequencyWords() {
        assertEquals(3, analyzer.calculateHighestFrequency("This is a sentence where a repeated word is present and it is three times present at that"));
    }

    @Test
    void calculateHighestFrequencyPunctuation() {
        assertEquals(2, analyzer.calculateHighestFrequency("This is a sentence with a repeated word."));
        assertEquals(2, analyzer.calculateHighestFrequency("Is this is a sentence with a repeated word?"));
        assertEquals(2, analyzer.calculateHighestFrequency("This is a sentence, but with a repeated word"));
        assertEquals(2, analyzer.calculateHighestFrequency("This is a sentence with a repeated word!"));
        assertEquals(2, analyzer.calculateHighestFrequency("This is a sentence with a repeated word: the smallest word"));
        assertEquals(2, analyzer.calculateHighestFrequency("This is a sentence with a repeated word; for testing purposes"));
        assertEquals(2, analyzer.calculateHighestFrequency("This is a semi-formatted sentence with a repeated word"));
        assertEquals(2, analyzer.calculateHighestFrequency("This is a sentence with a repeated word from my friend [John]"));
        assertEquals(2, analyzer.calculateHighestFrequency("This is a sentence with a repeated word and some {braces}"));
        assertEquals(2, analyzer.calculateHighestFrequency("This (like all the others) is a sentence with a repeated word"));
        assertEquals(2, analyzer.calculateHighestFrequency("This is John's favorite sentence with a repeated word and a nice one at that"));
        assertEquals(2, analyzer.calculateHighestFrequency("This is a sentence with a repeated word..."));
    }

    @Test
    void calculateHighestFrequencyNumbers() {
        assertEquals(1, analyzer.calculateHighestFrequency("In the year 2000 I wrote a sentence with 2000 repeated words"));
    }

    @Test
    void calculateHighestFrequencyNullText() {
        assertThrows(IllegalArgumentException.class, () -> analyzer.calculateHighestFrequency(null));
    }

    @Test
    void calculateHighestFrequencyEmptyText() {
        assertThrows(IllegalArgumentException.class, () -> analyzer.calculateHighestFrequency(""));
        assertThrows(IllegalArgumentException.class, () -> analyzer.calculateHighestFrequency("   "));
    }

    @Test
    void calculateFrequencyForWord() {
        assertEquals(2, analyzer.calculateFrequencyForWord("This is a sentence with a repeated word", "a"));
    }

    @Test
    void calculateFrequencyForWordNotFound() {
        assertEquals(0, analyzer.calculateFrequencyForWord("This is a sentence with a repeated word", "bear"));
    }

    @Test
    void calculateFrequencyForWordPartialMatch() {
        assertEquals(0, analyzer.calculateFrequencyForWord("This is a sentence with a repeated word", "repeat"));
    }

    @Test
    void calculateFrequencyForWordCapitalizedInText() {
        assertEquals(2, analyzer.calculateFrequencyForWord("This is a sentence with this repeated word", "this"));
    }

    @Test
    void calculateFrequencyForWordCapitalizedInWord() {
        assertEquals(2, analyzer.calculateFrequencyForWord("This is a sentence with this repeated word", "This"));
    }

    @Test
    void calculateFrequencyForWordPunctuatedText() {
        assertEquals(1, analyzer.calculateFrequencyForWord("This is a sentence with a repeated word.", "word"));
        assertEquals(1, analyzer.calculateFrequencyForWord("Is this is a sentence with a repeated word?", "word"));
        assertEquals(1, analyzer.calculateFrequencyForWord("This is a sentence, but with a repeated word", "sentence"));
        assertEquals(1, analyzer.calculateFrequencyForWord("This is a sentence with a repeated word!", "word"));
        assertEquals(1, analyzer.calculateFrequencyForWord("This is a sentence with a repeated word:the smallest one", "word"));
        assertEquals(1, analyzer.calculateFrequencyForWord("This is a sentence with a repeated word; for testing purposes", "word"));
        // this could technically be considered incorrect, since typical grammar would consider "semi-formatted" one word, but seeing as the definition of a word is (a-z A-Z), this
        // does meet the requirements
        assertEquals(1, analyzer.calculateFrequencyForWord("This is a semi-formatted sentence with a repeated word", "formatted"));
        assertEquals(1, analyzer.calculateFrequencyForWord("This is a sentence with a repeated word from my friend [John]", "john"));
        assertEquals(1, analyzer.calculateFrequencyForWord("This is a sentence with a repeated word and some {braces}", "braces"));
        assertEquals(1, analyzer.calculateFrequencyForWord("This (like all the others) is a sentence with a repeated word", "others"));
        assertEquals(1, analyzer.calculateFrequencyForWord("This is John's favorite sentence with a repeated word and a nice one at that", "john"));
        assertEquals(1, analyzer.calculateFrequencyForWord("This is a sentence with a repeated word...", "word"));
    }

    @Test
    void calculateFrequencyForWordNullText() {
        assertThrows(IllegalArgumentException.class, () -> analyzer.calculateFrequencyForWord(null, "word"));
    }

    @Test
    void calculateFrequencyForWordNullWord() {
        assertThrows(IllegalArgumentException.class, () -> analyzer.calculateFrequencyForWord("This is a sentence", null));
    }

    @Test
    void calculateFrequencyForWordEmptyText() {
        assertThrows(IllegalArgumentException.class, () -> analyzer.calculateFrequencyForWord("", "word"));
        assertThrows(IllegalArgumentException.class, () -> analyzer.calculateFrequencyForWord("   ", "word"));
    }

    @Test
    void calculateFrequencyForWordEmptyWord() {
        assertThrows(IllegalArgumentException.class, () -> analyzer.calculateFrequencyForWord("This is a sentence", ""));
        assertThrows(IllegalArgumentException.class, () -> analyzer.calculateFrequencyForWord("This is a sentence", "   "));
    }

    @Test
    void calculateFrequencyForWordNonAlphabeticWord() {
        assertThrows(IllegalArgumentException.class, () -> analyzer.calculateFrequencyForWord("This is a sentence", "2000"));
        assertThrows(IllegalArgumentException.class, () -> analyzer.calculateFrequencyForWord("This is john's sentence", "john's"));
        assertThrows(IllegalArgumentException.class, () -> analyzer.calculateFrequencyForWord("This is a sentence", "john_doe"));
    }

    @Test
    void calculateMostFrequentNWords() {
        List<WordFrequency> expected = List.of(
                new SimpleWordFrequency("a", 3),
                new SimpleWordFrequency("is", 3),
                new SimpleWordFrequency("and", 2),
                new SimpleWordFrequency("that", 2),
                new SimpleWordFrequency("this", 2)
        );

        List<WordFrequency> actual = analyzer.calculateMostFrequentNWords(FREQUENT_N_WORDS_TEXT, 5);
        assertEquals(expected, actual);
    }

    @Test
    void calculateMostFrequentNWordsMultipleOfSameFrequency() {
        List<WordFrequency> expected = List.of(
                new SimpleWordFrequency("a", 3),
                new SimpleWordFrequency("is", 3),
                new SimpleWordFrequency("and", 2)
        );

        List<WordFrequency> actual = analyzer.calculateMostFrequentNWords(FREQUENT_N_WORDS_TEXT, 3);
        assertEquals(expected, actual);
    }

    @Test
    void calculateMostFrequentNWordsAlphabeticallySorted() {
        List<WordFrequency> actual = analyzer.calculateMostFrequentNWords(FREQUENT_N_WORDS_TEXT, 10);
        WordFrequency previous = null;
        for (WordFrequency current : actual) {
            if (previous != null
                    && current.getFrequency() == previous.getFrequency()
                    && current.getWord().compareTo(previous.getWord()) < 0) {
                fail("Results are not alphabetically sorted");
            }
            previous = current;
        }
    }

    @Test
    void calculateMostFrequentNWordsMoreRequestedThanPresent() {
        List<WordFrequency> expected = List.of(
                new SimpleWordFrequency("a", 1),
                new SimpleWordFrequency("is", 1),
                new SimpleWordFrequency("sentence", 1),
                new SimpleWordFrequency("this", 1)
        );

        List<WordFrequency> actual = analyzer.calculateMostFrequentNWords("This is a sentence", 10);
        assertEquals(expected, actual);
    }

    @Test
    void calculateMostFrequentNWordsNullText() {
        assertThrows(IllegalArgumentException.class, () -> analyzer.calculateMostFrequentNWords(null, 3));
    }

    @Test
    void calculateMostFrequentNWordsNumberLowerThanZero() {
        assertThrows(IllegalArgumentException.class, () -> analyzer.calculateMostFrequentNWords(FREQUENT_N_WORDS_TEXT, 0));
        assertThrows(IllegalArgumentException.class, () -> analyzer.calculateMostFrequentNWords(FREQUENT_N_WORDS_TEXT, -1));
    }

    @Test
    void calculateMostFrequentNWordsEmptyText() {
        assertThrows(IllegalArgumentException.class, () -> analyzer.calculateMostFrequentNWords("", 1));
        assertThrows(IllegalArgumentException.class, () -> analyzer.calculateMostFrequentNWords("   ", 1));
    }
}