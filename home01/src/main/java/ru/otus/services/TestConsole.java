package ru.otus.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import ru.otus.model.*;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

@Service
public class TestConsole {

    @Value("$user.language")
    private String userLanguage;

    @Value("${questions.mintrueanswers}")
    private int cntAnswersForTestPassing;

    private IQuestions questions;

    private Locale locale;

    @Autowired
    private MessageSource messageSource;

    public TestConsole(IQuestions questions, MessageSource messageSource) {
        this.questions = questions;
    }

    public IQuestions getQuestions() {
        return questions;
    }

    public void setQuestions(IQuestions questions) {
        this.questions = questions;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public void runTest() throws IOException {
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        this.printWelcome(input);

        List<IQuestion> questionsList = questions.getQuestionList();
        int cntQst = questionsList.size();
        System.out.println(messageSource.getMessage("count.questions", new Object[]{cntQst}, getLocale()));
        String command = "start";
        try {
            while ((!"no".equalsIgnoreCase(command)) && (!"n".equalsIgnoreCase(command))) {
                int score = 0;
                Iterator<IQuestion> itQ = questionsList.iterator();
                while (itQ.hasNext()) {
                    IQuestion question = itQ.next();
                    this.printQuestion(question);
                    Iterator<IAnswer> itA = question.getAnswerList().iterator();
                    int indexAnswer = 1;
                    int indexCorrectAnswer = 0;
                    while (itA.hasNext()){
                        IAnswer answer = itA.next();
                        this.printAnswer(answer, indexAnswer);
                        indexCorrectAnswer = answer.isCorrect() ? indexAnswer : indexCorrectAnswer;
                        indexAnswer++;
                    }
                    System.out.print(messageSource.getMessage("your.answer", new Object[]{}, getLocale()));
                    int respondedAnswer;
                    try {
                        respondedAnswer = Integer.valueOf(input.readLine());
                    } catch (NumberFormatException nfExc) {
                        respondedAnswer = 0;
                    }
                    score = checkAnswer(indexCorrectAnswer, respondedAnswer) ? ++score : score;
                }

                this.printResults(score, cntQst);
                System.out.println(messageSource.getMessage("one.more.time", new Object[]{}, getLocale()));
                command = input.readLine();
            }
        } catch (IOException ioExc) {
            throw new RuntimeException("Input error value" + ioExc, ioExc);
        } catch (NumberFormatException nfExc) {
            throw new RuntimeException("Incorrect input answer variant" + nfExc, nfExc);
        }
    }

    private void printWelcome(BufferedReader input) throws IOException {
        System.out.println(" ********************************** TESTING ********************************* ");
        System.out.print(messageSource.getMessage("enter.first.name", new Object[]{}, getLocale()));
        String studentFirstName = input.readLine();
        System.out.print(messageSource.getMessage("enter.last.name", new Object[]{}, getLocale()));
        String studentLastName = input.readLine();
        System.out.println(messageSource.getMessage("hi.firstname.lastname", new Object[]{studentFirstName, studentLastName}, getLocale()));
        System.out.println(messageSource.getMessage("lets.start", new Object[]{}, getLocale()));
    }

    private void printQuestion(IQuestion question) {
        System.out.println("\nQ: " + question.getQuestion());
        System.out.print("A: ");
    }

    private void printAnswer(IAnswer answer, int indexAnswer) {
        System.out.print(indexAnswer + ") " + answer.getAnswer() + "; ");
    }

    private void printResults(int score, int countQuestions) {
        System.out.println("\n-----------------------------------------------------------------------------");
        System.out.print(messageSource.getMessage("print.results", new Object[]{score, this.calcUserRating(score, countQuestions)}, getLocale()));
        System.out.print("\n-----------------------------------------------------------------------------");
        System.out.println("\n-----------------------------------------------------------------------------");
    }

    private boolean checkAnswer(int correctInd, int answeredInd) {
        return correctInd == answeredInd;
    }

    private String calcUserRating(int score, int countQuestions) {
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
