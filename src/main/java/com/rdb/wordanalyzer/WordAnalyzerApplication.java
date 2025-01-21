package com.rdb.wordanalyzer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.rdb.wordanalyzer")
public class WordAnalyzerApplication {

	public static void main(String[] args) {
		SpringApplication.run(WordAnalyzerApplication.class, args);
	}

}
