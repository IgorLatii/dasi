package com.example.demo.model;

import lombok.Data;

@Data
public class Answer {
    private String answer;

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}