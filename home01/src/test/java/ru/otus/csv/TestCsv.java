package ru.otus.csv;

import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;
import ru.otus.Application;
import ru.otus.ApplicationTest;
import ru.otus.util.CsvReader;

import java.io.IOException;

@DisplayName("Tests for CsvReader class")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationTest.class)
public class TestCsv {

    @Autowired
    CsvReader csvReader;

    @DisplayName("Reading file test")
    @Test
    public void readFileTest() throws IOException {
        csvReader.readCsv();
        Assert.assertEquals(5, csvReader.getQuestionCount());
    }

}