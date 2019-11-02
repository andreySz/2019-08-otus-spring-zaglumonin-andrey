package ru.otus.dao;

import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Genre;

import java.sql.SQLException;
import java.util.List;

public interface BookDao {

    List<Book> getAll();

    Book getById(long id) throws SQLException;

    List<Book> getBooksByGenre(Genre genre);

    List<Book> getBooksByAuthor(Author author);

}
