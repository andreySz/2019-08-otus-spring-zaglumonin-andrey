package ru.otus.model;

import java.util.List;

public class Questions implements IQuestions {

    private List<IQuestion> questionList;

    public List<IQuestion> getQuestionList() {
        return questionList;
    }

    public void setQuestionList(List<IQuestion> questionList) {
        this.questionList = questionList;
    }
}
