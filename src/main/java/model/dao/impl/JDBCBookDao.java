package model.dao.impl;

import model.dao.BookDao;
import model.dao.mapper.impl.BookMapper;
import model.entity.Author;
import model.entity.Book;
import model.entity.Tag;

import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

public class JDBCBookDao implements BookDao {
    private final Connection connection;
    private final BookMapper bookMapper = new BookMapper();

    public JDBCBookDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Optional<Book> findByName(String name) {
        return findBookByName(name, SQL_FIND_BY_NAME);
    }

    private Optional<Book> findBookByName(String name, String sqlFindByName) {
        Book book = null;
        Map<Long, Book> books = new HashMap<>();
        Map<Long, Tag> tags = new HashMap<>();
        Map<Long, Author> authors = new HashMap<>();
        try {
            PreparedStatement statement = connection.prepareStatement(sqlFindByName);
            statement.setString(1, name);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                book = bookMapper.fullExtractFromResultSet(rs, books, tags, authors);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.ofNullable(book);
    }

    @Override
    public Optional<Book> findByNameUa(String nameUa) {
        return findBookByName(nameUa, SQL_FIND_BY_NAME_UA);
    }

    @Override
    public void create(Book entity) {
        try {
            PreparedStatement statement = connection.prepareStatement(SQL_INSERT);
            statement.setString(1, entity.getName());
            statement.setString(2, entity.getNameUa());
            statement.setBoolean(3, entity.isAvailable());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<Book> findById(long id) {
        Book book = null;
        Map<Long, Book> books = new HashMap<>();
        Map<Long, Tag> tags = new HashMap<>();
        Map<Long, Author> authors = new HashMap<>();
        try {
            PreparedStatement statement = connection.prepareStatement(SQL_FIND_BY_ID);
            statement.setLong(1, id);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                book = bookMapper.fullExtractFromResultSet(rs, books, tags, authors);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.ofNullable(book);
    }

    @Override
    public List<Book> findAll() {
        List<Book> resultList = new ArrayList<>();
        Map<Long, Book> books = new HashMap<>();
        Map<Long, Tag> tags = new HashMap<>();
        Map<Long, Author> authors = new HashMap<>();
        try (Statement st = connection.createStatement()) {
            ResultSet rs = st.executeQuery(SQL_FIND_ALL);
            while (rs.next()) {
                Book book = bookMapper.fullExtractFromResultSet(rs, books, tags, authors);
                resultList.add(book);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return resultList.stream().distinct().collect(Collectors.toList());
    }

    @Override
    public void update(Book entity) {
        try {
            PreparedStatement statement = connection.prepareStatement(SQL_UPDATE);
            statement.setBoolean(1, entity.isAvailable());
            statement.setLong(2, entity.getBookId());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(long id) {
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
