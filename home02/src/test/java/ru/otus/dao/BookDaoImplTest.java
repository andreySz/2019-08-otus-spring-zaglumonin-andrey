package ru.otus.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Библиотека")
@JdbcTest
@Import(value = {BookDaoImpl.class})
public class BookDaoImplTest {

    @Autowired
    private BookDaoImpl bookDao;

    @DisplayName("Получить книгу по id ")
    @Test
    void getById() throws SQLException {
        assertEquals("book1-tst", bookDao.getById(1L).getTitle());
    }

}
