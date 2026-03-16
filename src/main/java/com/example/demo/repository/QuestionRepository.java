package com.example.demo.repository;

import com.example.demo.model.Question;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface QuestionRepository extends MongoRepository<Question, String> {
    List<Question> findAllByUserId(String userId);
}