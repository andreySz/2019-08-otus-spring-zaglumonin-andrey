package ru.otus.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import ru.otus.model.*;
import ru.otus.util.CsvReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

@Component
public class TestConsole {

    @Value("${user.language}")
    private String userLanguage;

    @Value("${questions.mintrueanswers}")
    private int cntAnswersForTestPassing;

    private Locale locale;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private CsvReader csvReader;

    private int score;

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    //@Bean
    public String login() {
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        try  {
            System.out.print(messageSource.getMessage("enter.first.name", new Object[]{}, getLocale()));
            String studentFirstName = input.readLine();
            System.out.print(messageSource.getMessage("enter.last.name", new Object[]{}, getLocale()));
            String studentLastName = input.readLine();
            this.printWelcome(studentFirstName, studentLastName);
        } catch (Exception exc) {
            throw new RuntimeException("Error during login" + exc, exc);
        }
        return "OK";
    }

    //@Bean
    public int runTest() throws IOException {
        List<Question> questionsList = csvReader.readCsv();
        int cntQst = questionsList.size();
        System.out.println(messageSource.getMessage("count.questions", new Object[]{cntQst}, getLocale()));
        String command = "start";
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        try {
            //while ((!"no".equalsIgnoreCase(command)) && (!"n".equalsIgnoreCase(command))) {
                score = 0;
                Iterator<Question> itQ = questionsList.iterator();
                while (itQ.hasNext()) {
                    Question question = itQ.next();
                    this.printQuestion(question);
                    Iterator<Answer> itA = question.getAnswerList().iterator();
                    int indexAnswer = 1;
                    int indexCorrectAnswer = 0;
                    while (itA.hasNext()){
                        Answer answer = itA.next();
                        this.printAnswer(answer, indexAnswer);
                        indexCorrectAnswer = answer.isCorrect() ? indexAnswer : indexCorrectAnswer;
                        indexAnswer++;
                    }
                    System.out.print(messageSource.getMessage("your.answer", new Object[]{}, getLocale()));
                    int respondedAnswer;
                    try {
                        respondedAnswer = Integer.parseInt(input.readLine());
                    } catch (NumberFormatException nfExc) {
                        respondedAnswer = 0;
                    }
                    score = checkAnswer(indexCorrectAnswer, respondedAnswer) ? ++score : score;
                }
                //this.printResults(cntQst);
                //System.out.println(messageSource.getMessage("one.more.time", new Object[]{}, getLocale()));
                //command = input.readLine();
            //}
        } catch (IOException ioExc) {
            throw new RuntimeException("Input error value " + ioExc, ioExc);
        } catch (NumberFormatException nfExc) {
            throw new RuntimeException("Incorrect input answer variant " + nfExc, nfExc);
        }
        return score;
    }

    public void printWelcome(String studentFirstName, String studentLastName) throws IOException {
        System.out.println(" ********************************** TESTING ********************************* ");
        System.out.println(messageSource.getMessage("hi.firstname.lastname", new Object[]{studentFirstName, studentLastName}, getLocale()));
        System.out.println(messageSource.getMessage("lets.start", new Object[]{}, getLocale()));
    }

    private void printQuestion(Question question) {
        System.out.println("\nQ: " + question.getQuestion());
        System.out.print("A: ");
    }

    private void printAnswer(Answer answer, int indexAnswer) {
        System.out.print(indexAnswer + ") " + answer.getAnswer() + "; ");
    }

    //@Bean
    public String printResults() {
        System.out.println("\n-----------------------------------------------------------------------------");
        System.out.print(messageSource.getMessage("print.results", new Object[]{score, this.calcUserRating(csvReader.getQuestionCount())}, getLocale()));
        System.out.print("\n-----------------------------------------------------------------------------");
        System.out.println("\n-----------------------------------------------------------------------------");
        return "OK";
    }

    private boolean checkAnswer(int correctInd, int answeredInd) {
        return correctInd == answeredInd;
    }

    private String calcUserRating(int countQuestions) {
        String rating;
        double part = (double) score/countQuestions;
        if (!(part > 0.2d)) {
            rating = messageSource.getMessage("rating.verybad", new Object[]{}, getLocale());
        } else if (part > 0.2d && !(part > 0.4d)) {
            rating = messageSource.getMessage("rating.bad", new Object[]{}, getLocale());
        } else if (part > 0.4d && !(part > 0.6d)) {
            rating = messageSource.getMessage("rating.satisfactory", new Object[]{}, getLocale());
        } else if (part > 0.6d && !(part > 0.8d)) {
            rating = messageSource.getMessage("rating.good", new Object[]{}, getLocale());
        } else {
            rating = messageSource.getMessage("rating.verybad", new Object[]{}, getLocale());
        }
        return rating;
    }



}
