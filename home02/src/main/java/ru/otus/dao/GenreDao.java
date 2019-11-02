package ru.otus.dao;

import ru.otus.domain.Genre;

import java.sql.SQLException;
import java.util.List;

public interface GenreDao {

    List<Genre> getAll();

    Genre getById(long id) throws SQLException;



}
