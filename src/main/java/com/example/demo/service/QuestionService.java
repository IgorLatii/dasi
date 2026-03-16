package com.example.demo.service;

import com.example.demo.model.Answer;
import com.example.demo.model.Question;
import com.example.demo.repository.QuestionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final QuestionSenderService questionSenderService;

    public QuestionService(QuestionRepository questionRepository,
                           QuestionSenderService questionSenderService) {
        this.questionRepository = questionRepository;
        this.questionSenderService = questionSenderService;
    }

    public Question createQuestion(String userId, String text) {

        Question question = new Question();
        question.setUserId(userId);
        question.setQuestion(text);
        question.setTimestamp(System.currentTimeMillis());

        Answer response = questionSenderService.sendQuestion(question);

        question.setAnswer(response.getAnswer());

        return questionRepository.save(question);
    }

    public List<Question> getHistory(String userId) {
        return questionRepository.findAllByUserId(userId);
    }
}