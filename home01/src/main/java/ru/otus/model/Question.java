package ru.otus.model;

import java.util.List;

public class Question implements IQuestion {

    private String question;
    private int index;
    private List<IAnswer> answerList;

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public List<IAnswer> getAnswerList() {
        return answerList;
    }

    public void setAnswerList(List<IAnswer> answerList) {
        this.answerList = answerList;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
