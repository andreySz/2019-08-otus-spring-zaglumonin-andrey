package ru.otus;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otus.model.Questions;
import ru.otus.services.TestConsole;
import ru.otus.util.CsvReader;

import java.io.IOException;

public class Application {

    public static void main(String[] args) throws IOException, InterruptedException {

        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/context.xml");
        CsvReader csvReader = context.getBean("csvReader", CsvReader.class);

        try {
            csvReader.readCsv();
            Questions questions = csvReader.getQuestions();
            TestConsole testConsole = new TestConsole(questions);
            testConsole.runTest();
        } catch (IOException ioExc) {
            throw new RuntimeException("Error reading file with questions!" + ioExc, ioExc);
        } catch (Exception exc) {
            throw new RuntimeException("Error during the testing...");
        } finally {
            stopping();
        }
    }

    private static  void stopping() throws InterruptedException {
        System.out.println();
        System.out.print("Stopping the test");
        for (int i = 0; i < 20; i++) {
            System.out.print(".");
            Thread.sleep(50L);
        }
        System.out.println("\nStopped.");
    }

}
