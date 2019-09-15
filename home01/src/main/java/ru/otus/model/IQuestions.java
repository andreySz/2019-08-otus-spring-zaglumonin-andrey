package ru.otus.model;

import java.util.List;

public interface IQuestions {

    List<IQuestion> getQuestionList();

    void setQuestionList(List<IQuestion> questionList);

}
