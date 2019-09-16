package ru.otus;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import ru.otus.services.TestConsole;
import ru.otus.util.CsvReader;

import java.io.IOException;

@Configuration
@ComponentScan
@PropertySource(value="classpath:application.properties")
public class Application {

    public static void main(String[] args) throws InterruptedException {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Application.class);
        CsvReader csvReader = context.getBean(CsvReader.class);
        try {
            csvReader.readCsv();
            TestConsole testConsole = context.getBean(TestConsole.class);
            testConsole.setQuestions(csvReader.getQuestions());
            testConsole.setLocale(csvReader.getLocale());
            testConsole.runTest();
        } catch (IOException ioExc) {
            throw new RuntimeException("Error reading file with questions!" + ioExc, ioExc);
        } catch (Exception exc) {
            throw new RuntimeException("Error during the testing..." + exc, exc);
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
