package com.rdb.wordanalyzer.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rdb.wordanalyzer.domain.SimpleWordFrequency;
import com.rdb.wordanalyzer.domain.WordFrequency;
import com.rdb.wordanalyzer.service.WordFrequencyAnalyzer;
import com.rdb.wordanalyzer.web.request.FrequencyForWordRequest;
import com.rdb.wordanalyzer.web.request.HighestFrequencyRequest;
import com.rdb.wordanalyzer.web.request.MostFrequentNWordsRequest;
import com.rdb.wordanalyzer.web.response.MostFrequentWordsResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(WordAnalysisController.class)
class WordAnalysisControllerTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;
    @MockitoBean private WordFrequencyAnalyzer wordFrequencyAnalyzer;

    @Test
    void calculateHighestFrequency() throws Exception {
        String text = "This is a sentence";
        int highestFrequency = 1;

        given(wordFrequencyAnalyzer.calculateHighestFrequency(text)).willReturn(highestFrequency);

        mockMvc.perform(post("/frequencies/highest")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new HighestFrequencyRequest(text))))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.frequency").value(highestFrequency));
    }

    @Test
    void calculateHighestFrequencyNullText() throws Exception {
        mockMvc.perform(post("/frequencies/highest")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new HighestFrequencyRequest(null))))
                .andExpect(status().isBadRequest());
    }

    @Test
    void calculateFrequencyForWord() throws Exception {
        String text = "This is a sentence";
        String word = "sentence";
        int frequency = 1;

        given(wordFrequencyAnalyzer.calculateFrequencyForWord(text, word)).willReturn(frequency);

        mockMvc.perform(post("/frequencies/word")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new FrequencyForWordRequest(text, "sentence"))))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.frequency").value(frequency));
    }

    @Test
    void calculateFrequencyForWordNullText() throws Exception {
        mockMvc.perform(post("/frequencies/word")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new FrequencyForWordRequest(null, "sentence"))))
                .andExpect(status().isBadRequest());
    }

    @Test
    void calculateFrequencyForWordNullWord() throws Exception {
        mockMvc.perform(post("/frequencies/word")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new FrequencyForWordRequest("This is a sentence", null))))
                .andExpect(status().isBadRequest());
    }

    @Test
    void calculateMostFrequentNWords() throws Exception {
        String text = "This is a sentence";
        int limit = 3;
        List<WordFrequency> wordFrequencies = List.of(
                new SimpleWordFrequency("a", 2),
                new SimpleWordFrequency("is", 1),
                new SimpleWordFrequency("repeated", 1)
        );
        MostFrequentWordsResponse expectedResponse = new MostFrequentWordsResponse(wordFrequencies);

        given(wordFrequencyAnalyzer.calculateMostFrequentNWords(text, limit)).willReturn(wordFrequencies);

        mockMvc.perform(post("/frequencies/most")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new MostFrequentNWordsRequest(text, limit))))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(expectedResponse)));
    }

    @Test
    void calculateMostFrequentNWordsNullText() throws Exception {
        mockMvc.perform(post("/frequencies/most")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new MostFrequentNWordsRequest(null, 1))))
                .andExpect(status().isBadRequest());
    }

    @Test
    void calculateMostFrequentNWordsInvalidLimit() throws Exception {
        mockMvc.perform(post("/frequencies/most")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new MostFrequentNWordsRequest("This is a sentence", 0))))
                .andExpect(status().isBadRequest());
    }
}
