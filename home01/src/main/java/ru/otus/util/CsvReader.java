package ru.otus.util;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
import ru.otus.model.*;
import org.springframework.beans.factory.annotation.Value;

import java.io.*;
import java.util.*;

@Component
public class CsvReader {

    @Value("${user.language}")
    private String userLanguage;

    @Value("${questions.file.path.ru}")
    private String fileNameRu;

    @Value("${questions.file.path.en}")
    private String fileNameEn;

    private Questions questions;

    private Locale locale;

    public String getFileNameEn() {
        return fileNameEn;
    }

    public void setFileNameEn(String fileName) {
        this.fileNameEn = fileName;
    }

    public Questions getQuestions() {
        return questions;
    }

    public void setQuestions(QuestionsImpl questions) {
        this.questions = questions;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public CsvReader() {
        this.questions = new QuestionsImpl();
        this.questions.setQuestionList(new ArrayList<>());
        this.defineLocale();
    }

    public List<Question> readCsv() {
        String fileName = getLocale() == Locale.ENGLISH ? fileNameEn : fileNameRu;
        String line;
        int indexInLine = 0;
        int indexQuestion = 1;
        List<Question> questionList = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(fileName)));
        try {
            while ((line = reader.readLine()) != null) {
                QuestionImpl question = new QuestionImpl();
                AnswerImpl answer = new AnswerImpl();
                List<Answer> answerList = new ArrayList<>();
                Scanner scanner = new Scanner(line);
                scanner.useDelimiter(",");
                while (scanner.hasNext()) {
                    String data = scanner.next();
                    if (indexInLine == 0) {
                        question.setIndex(indexQuestion);
                        question.setQuestion(data);
                    } else if (indexInLine == 1 || indexInLine == 3 || indexInLine == 5 || indexInLine == 7 || indexInLine == 9) {
                        answer = new AnswerImpl();
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
        } catch (Exception exc) {
            throw new RuntimeException("Error during reading CSV file" + exc, exc);
        }
        return questionList;
    }

    private void defineLocale() {
        if (userLanguage == null) {
            setLocale(LocaleContextHolder.getLocale());
        } else if (userLanguage.equals("en")) {
            setLocale(Locale.ENGLISH);
        } else {
            setLocale(new Locale("ru"));
        }
    }

    public int getQuestionCount() {
        int cnt = 0;
        try {
            cnt = questions.getQuestionList().size();
        } catch (Exception exc) {
            throw new RuntimeException("Empty questions list" + exc, exc);
        }
        return cnt;
    }

}
