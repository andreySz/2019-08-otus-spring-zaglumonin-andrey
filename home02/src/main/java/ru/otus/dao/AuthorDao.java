package ru.otus.dao;

import ru.otus.domain.Author;

import java.sql.SQLException;
import java.util.List;

public interface AuthorDao {

    List<Author> getAll();
    Author getById(long id) throws SQLException;

}
