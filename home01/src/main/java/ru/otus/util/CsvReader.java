package ru.otus.util;

import ru.otus.model.*;

import java.io.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class CsvReader {

    private String fileName = "";

    private IQuestions questions;

    public CsvReader(String fileName) {
        questions = new Questions();
        questions.setQuestionList(new LinkedList<IQuestion>());
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public IQuestions getQuestions() {
        return questions;
    }

    public void setQuestions(Questions questions) {
        this.questions = questions;
    }

    public void readCsv() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(fileName)));
        String line = null;
        int indexInLine = 0;
        int indexQuestion = 1;
        List<IQuestion> questionList = new LinkedList<IQuestion>();
        while ((line = reader.readLine()) != null) {
            Question question = new Question();
            Answer answer = new Answer();
            List<IAnswer> answerList = new LinkedList<IAnswer>();
            Scanner scanner = new Scanner(line);
            scanner.useDelimiter(",");
            while (scanner.hasNext()) {
                String data = scanner.next();
                if (indexInLine == 0) {
                    question.setIndex(indexQuestion);
                    question.setQuestion(data);
                } else if (indexInLine == 1 || indexInLine == 3 || indexInLine == 5 || indexInLine == 7 || indexInLine == 9) {
                    answer = new Answer();
                    answer.setAnswer(data);
                } else if (indexInLine == 2 || indexInLine == 4 || indexInLine == 6 || indexInLine == 8 || indexInLine == 10) {
                    answer.setCorrect("1".equals(data) ? true : false);
                    answerList.add(answer);
                } else {
                    System.out.println("Incorrect data:" + data);
                }
                indexInLine++;
            }
            indexInLine = 0;
            indexQuestion++;
            question.setAnswerList(answerList);
            questionList.add(question);
        }
        questions.setQuestionList(questionList);
        reader.close();
    }

}
