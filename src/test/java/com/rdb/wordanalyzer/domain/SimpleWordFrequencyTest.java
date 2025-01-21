package com.rdb.wordanalyzer.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SimpleWordFrequencyTest {

    @Test
    void testEquals() {
        assertEquals(new SimpleWordFrequency("a", 0), new SimpleWordFrequency("a", 0));
        assertNotEquals(new SimpleWordFrequency("a", 0), new SimpleWordFrequency("b", 0));
        assertNotEquals(new SimpleWordFrequency("a", 0), new SimpleWordFrequency("a", 1));
        assertNotEquals(new SimpleWordFrequency("a", 0), null);
        assertNotEquals(new SimpleWordFrequency("a", 0), "a:0");
    }

    @Test
    void testHashCode() {
        assertEquals(new SimpleWordFrequency("a", 0).hashCode(), new SimpleWordFrequency("a", 0).hashCode());
        assertNotEquals(new SimpleWordFrequency("a", 0).hashCode(), new SimpleWordFrequency("b", 0).hashCode());
        assertNotEquals(new SimpleWordFrequency("a", 0).hashCode(), new SimpleWordFrequency("a", 1).hashCode());
    }
}