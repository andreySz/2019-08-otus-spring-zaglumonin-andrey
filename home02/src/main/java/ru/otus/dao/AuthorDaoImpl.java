package ru.otus.dao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.domain.Author;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class AuthorDaoImpl implements AuthorDao {

    private final String SELECT_QUERY_FOR_GET_ALL = "SELECT * FROM authors";
    private final String SELECT_QUERY_FOR_GET_BY_ID = "SELECT * FROM authors WHERE id = :authorId";

    private final NamedParameterJdbcOperations jdbcOperations;

    public AuthorDaoImpl(NamedParameterJdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }

    @Override
    public List<Author> getAll() {
        return jdbcOperations.query(SELECT_QUERY_FOR_GET_ALL, new HashMap<>(), new AuthorMapper());
    }

    @Override
    public Author getById(long id) throws SQLException {
        final Map<String, Object> params = Collections.singletonMap("authorId", id);
        List<Author> authorsList = jdbcOperations.query(SELECT_QUERY_FOR_GET_BY_ID, params, new AuthorMapper());
        if (authorsList.isEmpty()) {
            throw new SQLException("No Author with id = " + id);
        } else if (authorsList.size() > 1) {
            throw new SQLException("More than one Author for id = " + id);
        } else
            return authorsList.get(0);
    }

    private static class AuthorMapper implements RowMapper<Author> {
        @Override
        public Author mapRow(ResultSet resultSet, int i) throws SQLException {
            Long id = resultSet.getLong("id");
            String name = resultSet.getString("name");
            return new Author(id, name);
        }
    }

}
