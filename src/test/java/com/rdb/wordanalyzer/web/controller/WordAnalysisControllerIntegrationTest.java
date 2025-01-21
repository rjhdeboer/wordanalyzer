package com.rdb.wordanalyzer.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rdb.wordanalyzer.WordAnalyzerApplication;
import com.rdb.wordanalyzer.domain.SimpleWordFrequency;
import com.rdb.wordanalyzer.domain.WordFrequency;
import com.rdb.wordanalyzer.web.request.FrequencyForWordRequest;
import com.rdb.wordanalyzer.web.request.HighestFrequencyRequest;
import com.rdb.wordanalyzer.web.request.MostFrequentNWordsRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = WordAnalyzerApplication.class)
@AutoConfigureMockMvc
class WordAnalysisControllerIntegrationTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;

    @Test
    void calculateHighestFrequency() throws Exception {
        mockMvc.perform(post("/frequencies/highest")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new HighestFrequencyRequest("This is a sentence"))))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").value(1));
    }

    @Test
    void calculateFrequencyForWord() throws Exception {
        mockMvc.perform(post("/frequencies/word")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new FrequencyForWordRequest("This is a sentence with a repeated word", "sentence"))))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").value(1));
    }

    @Test
    void calculateMostFrequentNWords() throws Exception {
        List<WordFrequency> wordFrequencies = List.of(
                new SimpleWordFrequency("a", 2),
                new SimpleWordFrequency("is", 1),
                new SimpleWordFrequency("repeated", 1)
        );

        mockMvc.perform(post("/frequencies/most")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new MostFrequentNWordsRequest("This is a sentence with a repeated word", 3))))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(wordFrequencies)));
    }
}
