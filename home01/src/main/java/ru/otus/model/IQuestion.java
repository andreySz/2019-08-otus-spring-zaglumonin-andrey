package ru.otus.model;

import java.util.List;

public interface IQuestion {

    String getQuestion();

    void setQuestion(String question);

    List<IAnswer> getAnswerList();

    void setAnswerList(List<IAnswer> answer);

}
