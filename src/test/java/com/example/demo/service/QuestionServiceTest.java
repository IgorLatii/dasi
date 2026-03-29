package com.example.demo.service;

import com.example.demo.model.Answer;
import com.example.demo.model.Question;
import com.example.demo.repository.QuestionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class QuestionServiceTest {

    @Mock
    private QuestionRepository questionRepository;

    @Mock
    private QuestionSenderService questionSenderService;

    @InjectMocks
    private QuestionService questionService;

    private Question question;
    private Answer answer;

    @BeforeEach
    void setUp() {
        question = new Question();
        question.setUserId("user123");
        question.setQuestion("What is Java?");
        question.setAnswer("A programming language");
        question.setTimestamp(123456789L);

        answer = new Answer();
        answer.setAnswer("A programming language");
    }

    @Test
    void createQuestion_Success() {
        when(questionSenderService.sendQuestion(any(Question.class))).thenReturn(answer);
        when(questionRepository.save(any(Question.class))).thenReturn(question);

        Question result = questionService.createQuestion("user123", "What is Java?");

        assertNotNull(result);
        assertEquals("user123", result.getUserId());
        assertEquals("What is Java?", result.getQuestion());
        assertEquals("A programming language", result.getAnswer());
        verify(questionSenderService).sendQuestion(any(Question.class));
        verify(questionRepository).save(any(Question.class));
    }

    @Test
    void getHistory_ReturnsList() {
        List<Question> questions = Arrays.asList(question, new Question());
        when(questionRepository.findAllByUserId(anyString())).thenReturn(questions);

        List<Question> result = questionService.getHistory("user123");

        assertEquals(2, result.size());
        verify(questionRepository).findAllByUserId("user123");
    }
}
