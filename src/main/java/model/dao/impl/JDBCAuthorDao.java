package model.dao.impl;

import model.dao.AuthorDao;
import model.dao.mapper.impl.AuthorMapper;
import model.entity.Author;

import java.sql.*;
import java.util.*;

public class JDBCAuthorDao implements AuthorDao {
    private final Connection connection;
    private final AuthorMapper authorMapper = new AuthorMapper();

    public JDBCAuthorDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Optional<Author> findByName(String name) {
        return findAuthorByName(name, SQL_FIND_BY_NAME);
    }

    private Optional<Author> findAuthorByName(String name, String sqlFindByName) {
        Author author = null;
        try (PreparedStatement statement = connection.prepareStatement(sqlFindByName)) {
            statement.setString(1, name);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                author = authorMapper.extractFromResultSet(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.ofNullable(author);
    }

    @Override
    public Optional<Author> findByNameUa(String nameUa) {
        return findAuthorByName(nameUa, SQL_FIND_BY_NAME_UA);
    }

    @Override
    public void create(Author entity) {
        try (PreparedStatement statement = connection.prepareStatement(SQL_INSERT)) {
            statement.setString(1, entity.getName());
            statement.setString(2, entity.getNameUa());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<Author> findById(long id) {
        Author author = null;
        try (PreparedStatement statement = connection.prepareStatement(SQL_FIND_BY_ID)) {
            statement.setLong(1, id);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                author = authorMapper.extractFromResultSet(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.ofNullable(author);
    }

    @Override
    public List<Author> findAll() {
        List<Author> resultList = new ArrayList<>();
        try (Statement st = connection.createStatement()) {
            ResultSet rs = st.executeQuery(SQL_FIND_ALL);
            while (rs.next()) {
                resultList.add(authorMapper.extractFromResultSet(rs));
            }
            return resultList;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void update(Author entity) {
        try (PreparedStatement statement = connection.prepareStatement(SQL_UPDATE)) {
            statement.setString(1, entity.getName());
            statement.setLong(2, entity.getAuthorId());
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
