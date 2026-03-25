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
        try {
            QuestionRequest request = new QuestionRequest(question.getQuestion());

            Answer answer = restTemplate.postForObject(
                    "http://4.235.123.69:8000/ask",
                    request,
                    Answer.class
            );

            if (answer == null || answer.getAnswer() == null || answer.getAnswer().trim().isEmpty()) {
                Answer fallback = new Answer();
                fallback.setAnswer("AI service returned an empty response.");
                return fallback;
            }

            return answer;
        } catch (Exception e) {
            Answer fallback = new Answer();
            fallback.setAnswer("AI service is temporarily unavailable. Please try again later.");
            return fallback;
        }
    }
}