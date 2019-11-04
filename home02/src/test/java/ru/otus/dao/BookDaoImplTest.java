package ru.otus.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.otus.domain.Book;

import java.sql.SQLException;
import java.util.List;

import static org.apache.logging.log4j.util.LambdaUtil.getAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Библиотека")
@JdbcTest
@Import(value = {BookDaoImpl.class})
public class BookDaoImplTest {

    @Autowired
    private BookDaoImpl bookDao;

    @DisplayName("Получить книгу по id")
    @Test
    void getById() throws SQLException {
        assertEquals("book1-tst", bookDao.getById(1L).getTitle());
    }

    @DisplayName("Вставка книг")
    @Test
    void insertBook() {
        List<Book> bookList = bookDao.getAll();
        int initSize = bookList.size();
        bookDao.insertBook(new Book("New Book", "Author Name", "Genre Name"));
        assertEquals(initSize, initSize++);
    }

    @DisplayName("Обновление книги")
    @Test
    void updateBook() throws SQLException {
        String oldTitle = bookDao.getById(1L).getTitle();
        bookDao.updateBookTitleById(1L, "New title");
        assertEquals(oldTitle, bookDao.getById(1L).getTitle());
    }

}
