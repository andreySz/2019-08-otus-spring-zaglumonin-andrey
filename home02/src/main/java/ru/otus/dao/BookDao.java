package ru.otus.dao;

import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Genre;

import java.sql.SQLException;
import java.util.List;

public interface BookDao {

    void insertBook(Book book);
    List<Book> getAll();
    Book getById(long id) throws SQLException;
    List<Book> getBooksByGenre(Long id);
    List<Book> getBooksByAuthor(Long id);
    void updateBookTitleById(Long id, String newTitle);

}
