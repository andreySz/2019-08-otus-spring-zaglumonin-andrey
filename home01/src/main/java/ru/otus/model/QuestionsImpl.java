package ru.otus.model;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class QuestionsImpl implements Questions {

    private List<Question> questionList;

    public List<Question> getQuestionList() {
        return questionList;
    }

    public void setQuestionList(List<Question> questionList) {
        this.questionList = questionList;
    }
}
