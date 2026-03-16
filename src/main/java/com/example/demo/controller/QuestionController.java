package com.example.demo.controller;

import com.example.demo.model.AskQuestionRequest;
import com.example.demo.model.Question;
import com.example.demo.service.QuestionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/questions")
public class QuestionController {

    private final QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @PostMapping
    public Question askQuestion(@RequestBody AskQuestionRequest request) {
        return questionService.createQuestion(request.getUserId(), request.getQuestion());
    }

    @GetMapping("/history/{userId}")
    public List<Question> getUserHistory(@PathVariable String userId) {
        return questionService.getHistory(userId);
    }
}