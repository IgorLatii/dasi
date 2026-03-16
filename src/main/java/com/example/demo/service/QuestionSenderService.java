package com.example.demo.service;

import com.example.demo.model.Answer;
import com.example.demo.model.Question;
import com.example.demo.model.QuestionRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class QuestionSenderService {

    private final RestTemplate restTemplate;

    public QuestionSenderService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Answer sendQuestion(Question question) {

        QuestionRequest request = new QuestionRequest(question.getQuestion());

        return restTemplate.postForObject(
                "http://4.235.123.69:8000/ask",
                request,
                Answer.class
        );
    }
}