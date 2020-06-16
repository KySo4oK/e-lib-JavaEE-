package model.dao.impl;

import model.dao.AuthorSearchDao;
import model.entity.AuthorSearch;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public class JDBCAuthorSearchDao implements AuthorSearchDao {
    public JDBCAuthorSearchDao(Connection connection) {

    }

    @Override
    public void create(AuthorSearch entity) {

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

    }

    @Override
    public void delete(long id) {

    }

    @Override
    public void close() {

    }
}