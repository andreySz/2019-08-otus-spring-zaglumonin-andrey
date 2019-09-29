package ru.otus.model;

import java.util.List;

public interface Question {

    String getQuestion();

    void setQuestion(String question);

    List<Answer> getAnswerList();

    void setAnswerList(List<Answer> answer);

}
