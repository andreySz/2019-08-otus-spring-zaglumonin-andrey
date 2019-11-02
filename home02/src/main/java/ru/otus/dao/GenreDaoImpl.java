package ru.otus.dao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.domain.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class GenreDaoImpl implements GenreDao {

    private final String SELECT_QUERY_FOR_GET_ALL = "SELECT * FROM genres";
    private final String SELECT_QUERY_FOR_GET_BY_ID = "SELECT * FROM genres WHERE id = :genreId";

    private final NamedParameterJdbcOperations jdbcOperations;

    public GenreDaoImpl(NamedParameterJdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }

    @Override
    public List<Genre> getAll() {
        return jdbcOperations.query(SELECT_QUERY_FOR_GET_ALL, new HashMap<>(), new GenreMapper());
    }

    @Override
    public Genre getById(long id) throws SQLException {

        final Map<String, Object> params = Collections.singletonMap("genreId", id);
        List<Genre> genresList = jdbcOperations.query(SELECT_QUERY_FOR_GET_BY_ID, params, new GenreMapper());

        if (genresList.isEmpty()) {
            throw new SQLException("Could not find genre with id " + id);
        } else if (genresList.size() > 1) {
            throw new SQLException("More than one genre for id " + id);
        } else
            return genresList.get(0);
    }

    private static class GenreMapper implements RowMapper<Genre> {
        @Override
        public Genre mapRow(ResultSet resultSet, int i) throws SQLException {
            Long id = resultSet.getLong("id");
            String name = resultSet.getString("name");
            return new Genre(id, name);
        }
    }

}
