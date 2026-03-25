package com.example.demo.controller;

import com.example.demo.model.AskQuestionRequest;
import com.example.demo.model.Question;
import com.example.demo.model.User;
import com.example.demo.service.QuestionService;
import com.example.demo.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/questions")
public class QuestionController {

    private final QuestionService questionService;
    private final UserService userService;

    public QuestionController(QuestionService questionService, UserService userService) {
        this.questionService = questionService;
        this.userService = userService;
    }

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public Question askQuestion(@RequestBody AskQuestionRequest request, Authentication authentication) {
        String email = authentication.getName();

        User user = userService.getUserByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return questionService.createQuestion(user.getId(), request.getQuestion());
    }

    @GetMapping("/history")
    @PreAuthorize("isAuthenticated()")
    public List<Question> getUserHistory(Authentication authentication) {
        String email = authentication.getName();

        User user = userService.getUserByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return questionService.getHistory(user.getId());
    }
}