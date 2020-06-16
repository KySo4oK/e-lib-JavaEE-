package model.dao.impl;

import model.dao.AuthorSearchDao;
import model.dao.mapper.impl.AuthorSearchMapper;
import model.entity.Author;
import model.entity.AuthorSearch;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class JDBCAuthorSearchDao implements AuthorSearchDao {
    private final Connection connection;
    private final AuthorSearchMapper mapper = new AuthorSearchMapper();

    public JDBCAuthorSearchDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(AuthorSearch entity) {
        Long userId = entity.getUser().getId();
        try (PreparedStatement statement = connection.prepareStatement(SQL_INSERT)) {
            for (Author author : entity.getAuthors()) {
                statement.setLong(1, userId);
                statement.setLong(2, author.getAuthorId());
                statement.addBatch();
            }
            statement.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<AuthorSearch> findById(long id) {
        return Optional.empty();
    }

    @Override
    public List<AuthorSearch> findAll() {
        return null;
    }

    @Override
    public void update(AuthorSearch entity) {
        try (PreparedStatement statement = connection.prepareStatement(SQL_UPDATE)) {
            statement.setDate(1, Date.valueOf(entity.getSearchDate()));
            statement.setLong(2, entity.getAuthorSearchId());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(long id) {
        try (PreparedStatement statement = connection.prepareStatement(SQL_DELETE)) {
            statement.setLong(1, id);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}