package ru.otus.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import ru.otus.model.Answer;
import ru.otus.model.Question;
import ru.otus.model.Questions;

import java.io.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

@Service
public class CsvReader {

    @Value("${questions.file.path}")
    private String fileName;

    @Value("${questions.mintrueanswers}")
    private int cntAnswersForTestPassing;

    private Questions questions;

    public CsvReader() {
        questions = new Questions();
        questions.setQuestionList(new LinkedList<Question>());
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Questions getQuestions() {
        return questions;
    }

    public void setQuestions(Questions questions) {
        this.questions = questions;
    }

    @Bean
    public void readCsv() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(fileName)));
        String line = null;
        int indexInLine = 0;
        int indexQuestion = 1;
        List<Question> questionList = new LinkedList<Question>();
        while ((line = reader.readLine()) != null) {
            Question question = new Question();
            Answer answer = new Answer();
            List<Answer> answerList = new LinkedList<Answer>();
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
