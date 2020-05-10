package model.dao.impl;

import model.dao.AuthorDao;
import model.dao.mapper.impl.AuthorMapper;
import model.dao.mapper.impl.UserMapper;
import model.entity.Author;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class JDBCAuthorDao implements AuthorDao {
    private Connection connection;
    private AuthorMapper authorMapper = new AuthorMapper();

    public JDBCAuthorDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Author findByName(String name) {
        return null;
    }

    @Override
    public Author findByNameUa(String nameUa) {
        return null;
    }

    @Override
    public void create(Author entity) {
        try {
            PreparedStatement statement = connection.prepareStatement(SQL_INSERT);
            statement.setString(1, entity.getUsername());
            statement.setString(1, entity.getUsername());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Author findById(int id) {
        return null;
    }

    @Override
    public List<Author> findAll() {
        return null;
    }

    @Override
    public void update(Author entity) {

    }

    @Override
    public void delete(int id) {

    }

    @Override
    public void close() {

    }
}
