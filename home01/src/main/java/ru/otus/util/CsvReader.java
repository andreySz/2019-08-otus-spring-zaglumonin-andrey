package ru.otus.util;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
import ru.otus.model.*;
import org.springframework.beans.factory.annotation.Value;

import java.io.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

@Component
public class CsvReader {

    @Value("${user.language}")
    private String userLanguage;

    @Value("${questions.file.path.ru}")
    private String fileNameRu;

    @Value("${questions.file.path.en}")
    private String fileNameEn;

    private IQuestions questions;

    private Locale locale;

    public String getFileNameEn() {
        return fileNameEn;
    }

    public void setFileNameEn(String fileName) {
        this.fileNameEn = fileName;
    }

    public IQuestions getQuestions() {
        return questions;
    }

    public void setQuestions(Questions questions) {
        this.questions = questions;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public CsvReader() {
        this.questions = new Questions();
        this.questions.setQuestionList(new LinkedList<IQuestion>());
        this.defineLocale();
    }

    public List<IQuestion> readCsv() throws IOException {
        String fileName = getLocale() == Locale.ENGLISH ? fileNameEn : fileNameRu;
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

}
