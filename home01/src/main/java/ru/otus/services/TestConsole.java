package ru.otus.services;

import ru.otus.model.Answer;
import ru.otus.model.Question;
import ru.otus.model.Questions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.List;

public class TestConsole {

    Questions questions;

    public TestConsole(Questions questions) {
        this.questions = questions;
    }

    public void runTest() throws IOException {
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        this.printWelcome(input);

        List<Question> questionsList = questions.getQuestionList();
        int cntQst = questionsList.size();
        System.out.println("Count of questions in the test: " + cntQst);
        String command = "start";
        try {
            while ((!"no".equalsIgnoreCase(command)) && (!"n".equalsIgnoreCase(command))) {
                int score = 0;
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
                    System.out.print("\nYour answer: ");
                    int respondedAnswer;
                    try {
                        respondedAnswer = Integer.valueOf(input.readLine());
                    } catch (NumberFormatException nfExc) {
                        respondedAnswer = 0;
                    }
                    score = checkAnswer(indexCorrectAnswer, respondedAnswer) ? ++score : score;
                }

                this.printResults(score, cntQst);
                System.out.println("One more time? (yes/no)");
                command = input.readLine();
            }
        } catch (IOException ioExc) {
            throw new RuntimeException("Input error value");
        } catch (NumberFormatException nfExc) {
            throw new RuntimeException("Incorrect input answer variant");
        }
    }

    private void printWelcome(BufferedReader input) throws IOException {
        System.out.println(" ********************************** TESTING ********************************* ");
        System.out.print("Please, enter your first name: ");
        String studentFirstName = input.readLine();
        System.out.print("> and last name: ");
        String studentLastName = input.readLine();
        System.out.println("\nHi, " + studentFirstName + " " + studentLastName + "! :)");
        System.out.println("Let's start the test. (For all questions choose only one correct answer 1-5)");
    }

    private void printQuestion(Question question) {
        System.out.println("\nQ: " + question.getQuestion());
        System.out.print("A: ");
    }

    private void printAnswer(Answer answer, int indexAnswer) {
        System.out.print(indexAnswer + ") " + answer.getAnswer() + "; ");
    }

    private void printResults(int score, int countQuestions) {
        System.out.println("\n-----------------------------------------------------------------------------");
        System.out.print("---> RESULTS :: Your score: " + score + " - " + this.calcUserRating(score, countQuestions));
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
            rating = "Very Bad!!! Need to start test one more time!";
        } else if (part > 0.2d && !(part > 0.4d)) {
            rating = "Bad Need to start test one more time!";
        } else if (part > 0.4d && !(part > 0.6d)) {
            rating = "Satisfactory...";
        } else if (part > 0.6d && !(part > 0.8d)) {
            rating = "Good!";
        } else {
            rating = "Excellent!!!";
        }
        return rating;
    }

}