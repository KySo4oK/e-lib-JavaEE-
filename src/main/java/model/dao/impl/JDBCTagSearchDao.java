package model.dao.impl;

import model.dao.TagSearchDao;
import model.entity.TagSearch;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public class JDBCTagSearchDao implements TagSearchDao {
    public JDBCTagSearchDao(Connection connection) {
    }

    @Override
    public void create(TagSearch entity) {

    }

    @Override
    public Optional<TagSearch> findById(long id) {
        return Optional.empty();
    }

    @Override
    public List<TagSearch> findAll() {
        return null;
    }

    @Override
    public void update(TagSearch entity) {

    }

    @Override
    public void delete(long id) {

    }

    @Override
    public void close() {

    }
}