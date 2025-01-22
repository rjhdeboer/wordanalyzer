# Word Analyzer

This is a small project that contains a word analyzer service exposed through a REST API.

It contains the following 3 functionalities: 
- Calculate the highest frequency of any word in a given text
- Calculate the frequency of a given word in a given text
- Calculate the most frequent _n_ number of words in a given text

The definition of a 'word' in this context is: a sequence of 1 or more characters (a-z A-Z).

### Prerequisites
- A (somewhat) recent version of Apache Maven
- JDK version 23

### Building
The application can be built with the following command: `mvn clean install`. This will also execute all unit and integration tests.
If you only want to run the tests, you can instead run: `mvn clean test`

### Running
After the application has been built, you can run the application with the following command: `mvn spring-boot:run`

### Testing
If you would like to use/test the exposed REST API, then you can access all endpoints with your favorite REST client as follows:

```
POST http://localhost:8080/frequencies/highest

{
    "text": "This is a sentence"
}

---

200 OK
{
    "frequency": 1
}
```

```
POST `http://localhost:8080/frequencies/word`

{
    "text": "This is a sentence and has a repeated word",
    "word": "a"
}

---

200 OK
{
    "frequency": 2
}
```

```
POST `http://localhost:8080/frequencies/most`

{
    "text": "This is a sentence with multiple words and that is a fact",
    "limit": 5
}

---

200 OK
{
    "wordFrequencies": [
        {
            "word": "a",
            "frequency": 2
        },
        {
            "word": "is",
            "frequency": 2
        },
        {
            "word": "and",
            "frequency": 1
        },
        {
            "word": "fact",
            "frequency": 1
        },
        {
            "word": "multiple",
            "frequency": 1
        }
    ]
}
```

### Implementation considerations
- The implementation assumes the given text is small enough to fit (and be processed) in memory. It is not a good fit for scenarios where either the concurrency on the API is high 
or the text volumes are large. In those cases it would be better to process the text (i.e. count the words) and analyze the text in separate processes.
- Calculating the frequency of a single word can technically be done directly through a regular expression. The choice was made to stick to the same processing algorithm as the
other methods for the reason of having the code and performance consistent