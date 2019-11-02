package ru.otus.dao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Repository
public class BookDaoImpl implements BookDao {

    private final String SELECT_QUERY_FOR_GET_ALL = "SELECT * FROM books";
    private final String SELECT_QUERY_FOR_GET_BY_ID = "SELECT * FROM books WHERE id = :bookId";
    private final String SELECT_QUERY_FOR_GET_BY_GENRE =
            "SELECT books.id, books.title FROM books " +
            "INNER JOIN genres " +
                    "ON books.genre_id = genres.id " +
            "WHERE genres.id = :genreId";
    private final String SELECT_QUERY_FOR_GET_BY_AUTHOR =
            "SELECT books.id, books.title FROM books " +
            "INNER JOIN authors " +
                    "ON books.author_id = authors.id " +
            "WHERE authors.id = :authorId";

    private final NamedParameterJdbcOperations jdbcOperations;

    public BookDaoImpl(NamedParameterJdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }

    @Override
    public List<Book> getAll() {
        return  jdbcOperations.query(SELECT_QUERY_FOR_GET_ALL, new BookMapper());
    }

    @Override
    public Book getById(long id) throws SQLException {
        final Map<String, Object> params = Collections.singletonMap("bookId", id);
        List<Book> booksList = jdbcOperations.query(SELECT_QUERY_FOR_GET_BY_ID, params, new BookMapper());
        if (booksList.isEmpty()) {
            throw new SQLException("Could not find Book with id " + id);
        } else if (booksList.size() > 1) {
            throw new SQLException("More than one Book for id " + id);
        } else
            return booksList.get(0);
    }

    @Override
    public List<Book> getBooksByGenre(Genre genre) {
        final Map<String, Object> params = Collections.singletonMap("genreId", genre.getId());
        return jdbcOperations.query(SELECT_QUERY_FOR_GET_BY_GENRE, params, new BookMapper());
    }

    @Override
    public List<Book> getBooksByAuthor(Author author) {
        final Map<String, Object> params = Collections.singletonMap("authorId", author.getId());
        return jdbcOperations.query(SELECT_QUERY_FOR_GET_BY_AUTHOR, params, new BookMapper());
    }

    private static class BookMapper implements RowMapper<Book> {
        @Override
        public Book mapRow(ResultSet resultSet, int i) throws SQLException {
            Long id = resultSet.getLong("id");
            String title = resultSet.getString("title");
            return new Book(id, title);
        }
    }

}
