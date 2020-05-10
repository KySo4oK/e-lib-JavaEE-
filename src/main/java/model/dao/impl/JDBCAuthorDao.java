package model.dao.impl;

import model.dao.AuthorDao;
import model.dao.mapper.impl.AuthorMapper;
import model.entity.Author;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JDBCAuthorDao implements AuthorDao {
    private Connection connection;
    private AuthorMapper authorMapper = new AuthorMapper();

    public JDBCAuthorDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Author findByName(String name) {
        Author author = null;
        try {
            PreparedStatement statement = connection.prepareStatement(SQL_FIND_BY_NAME);
            statement.setString(1, name);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                author = authorMapper.extractFromResultSet(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return author;
    }

    @Override
    public Author findByNameUa(String nameUa) {
        Author author = null;
        try {
            PreparedStatement statement = connection.prepareStatement(SQL_FIND_BY_NAME_UA);
            statement.setString(1, nameUa);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                author = authorMapper.extractFromResultSet(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return author;
    }

    @Override
    public void create(Author entity) {
        try {
            PreparedStatement statement = connection.prepareStatement(SQL_INSERT);
            statement.setString(1, entity.getName());
            statement.setString(1, entity.getNameUa());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Author findById(int id) {
        Author author = null;
        try {
            PreparedStatement statement = connection.prepareStatement(SQL_FIND_BY_ID);
            statement.setLong(1, id);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                author = authorMapper
                        .extractFromResultSet(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return author;
    }

    @Override
    public List<Author> findAll() {
        Map<Long, Author> authors = new HashMap<>();
        try (Statement st = connection.createStatement()) {
            ResultSet rs = st.executeQuery(SQL_FIND_ALL);
            while (rs.next()) {
                Author author = authorMapper
                        .extractFromResultSet(rs);
                author = authorMapper //useless now
                        .makeUnique(authors, author);
            }
            return new ArrayList<>(authors.values());
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void update(Author entity) {
        try {
            PreparedStatement statement = connection.prepareStatement(SQL_UPDATE);
            statement.setString(1, entity.getName());
            statement.setLong(2, entity.getAuthorId());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        try {
            PreparedStatement statement = connection.prepareStatement(SQL_DELETE);
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
