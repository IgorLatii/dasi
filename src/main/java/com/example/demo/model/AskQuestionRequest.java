package com.example.demo.model;

public class AskQuestionRequest {
    private String userId;
    private String question;

    public AskQuestionRequest() {}

    public AskQuestionRequest(String userId, String question) {
        this.userId = userId;
        this.question = question;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }
}
