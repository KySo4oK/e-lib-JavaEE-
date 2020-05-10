package model.dao.impl;

import model.dao.BookDao;
import model.dao.mapper.impl.AuthorMapper;
import model.dao.mapper.impl.BookMapper;
import model.dao.mapper.impl.TagMapper;
import model.entity.Author;
import model.entity.Book;
import model.entity.Tag;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JDBCBookDao implements BookDao {
    private Connection connection;
    private BookMapper bookMapper = new BookMapper();
    private TagMapper tagMapper = new TagMapper();
    private AuthorMapper authorMapper = new AuthorMapper();

    public JDBCBookDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Book findByName(String name) {
        return findBookByName(name, SQL_FIND_BY_NAME);
    }

    private Book findBookByName(String name, String sqlFindByName) {
        Book book = null;
        try {
            PreparedStatement statement = connection.prepareStatement(sqlFindByName);
            statement.setString(1, name);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                book = bookMapper.extractFromResultSet(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return book;
    }

    @Override
    public Book findByNameUa(String nameUa) {
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
    public Book findById(int id) {
        Book book = null;
        try {
            PreparedStatement statement = connection.prepareStatement(SQL_FIND_BY_ID);
            statement.setLong(1, id);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                book = bookMapper
                        .extractFromResultSet(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return book;
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
                Book book = bookMapper.extractFromResultSet(rs);
                Tag tag = tagMapper.extractFromResultSet(rs);
                Author author = authorMapper.extractFromResultSet(rs);
                tag = tagMapper.makeUnique(tags,tag);
                author = authorMapper.makeUnique(authors, author);
                book = bookMapper.makeUnique(books, book);
                book.getAuthors().add(author);
                book.getTags().add(tag);
                resultList.add(book);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return resultList;
    }

    @Override
    public void update(Book entity) {
        try {
            PreparedStatement statement = connection.prepareStatement(SQL_UPDATE);
            statement.setString(1, entity.getName());
            statement.setBoolean(2, entity.isAvailable());
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
