package ru.otus.shell;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import org.springframework.shell.standard.ShellOption;
import ru.otus.services.TestConsole;
import ru.otus.util.CsvReader;

import java.io.IOException;

@ShellComponent
public class ShellCommands {

    @Autowired
    TestConsole testConsole;

    private String user;
    private int results = -1;

    @ShellMethod(value = "Login command", key = {"l", "login"})
    public void welcome(@ShellOption(defaultValue = "andrey sz") String firstName, String lastName) throws IOException {
        this.user = firstName + lastName;
        testConsole.printWelcome(firstName, lastName);
    }

    @ShellMethod(value = "Start test command", key = {"s", "start"})
    @ShellMethodAvailability(value = "isLogin")
    public void start() throws IOException {
        results = testConsole.runTest();
    }

    @ShellMethod(value = "Print results command", key = {"r", "res", "results"})
    @ShellMethodAvailability(value = "isPassed")
    public void printResults() {
        testConsole.printResults();
    }

    private Availability isLogin() {
        return user == null ? Availability.unavailable("- User is not logged in...") : Availability.available();
    }

    private Availability isPassed() {
        return results == -1 ? Availability.unavailable("- Test is not passed...") : Availability.available();
    }

}
