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
import java.util.stream.Collectors;

public class JDBCBookDao implements BookDao {
    private final Connection connection;
    private final BookMapper bookMapper = new BookMapper();
    private final TagMapper tagMapper = new TagMapper();
    private final AuthorMapper authorMapper = new AuthorMapper();

    public JDBCBookDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Book findByName(String name) {
        return findBookByName(name, SQL_FIND_BY_NAME);
    }

    private Book findBookByName(String name, String sqlFindByName) {
        Book book = null;
        Map<Long, Book> books = new HashMap<>();
        Map<Long, Tag> tags = new HashMap<>();
        Map<Long, Author> authors = new HashMap<>();
        try {
            PreparedStatement statement = connection.prepareStatement(sqlFindByName);
            statement.setString(1, name);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                book = bookMapper.extractFromResultSet(rs);
                book = bookMapper.makeUnique(books, book);
                Tag tag = tagMapper.extractFromResultSet(rs);
                Author author = authorMapper.extractFromResultSet(rs);
                tag = tagMapper.makeUnique(tags, tag);
                author = authorMapper.makeUnique(authors, author);
                book.getAuthors().add(author);
                book.getTags().add(tag);
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
        Map<Long, Book> books = new HashMap<>();
        Map<Long, Tag> tags = new HashMap<>();
        Map<Long, Author> authors = new HashMap<>();
        try {
            PreparedStatement statement = connection.prepareStatement(SQL_FIND_BY_ID);
            statement.setLong(1, id);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                book = bookMapper.extractFromResultSet(rs);
                book = getFullBookFromResultSet(books, tags, authors, rs, book);
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
                book = getFullBookFromResultSet(books, tags, authors, rs, book);
                resultList.add(book);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return resultList.stream().distinct().collect(Collectors.toList());
    }

    protected Book getFullBookFromResultSet(Map<Long, Book> books, Map<Long, Tag> tags, Map<Long, Author> authors, ResultSet rs, Book book) throws SQLException {
        book = bookMapper.makeUnique(books, book);
        Tag tag = tagMapper.extractFromResultSet(rs);
        Author author = authorMapper.extractFromResultSet(rs);
        tag = tagMapper.makeUnique(tags, tag);
        author = authorMapper.makeUnique(authors, author);
        book.getAuthors().add(author);
        book.getTags().add(tag);
        book.setTags(book.getTags().stream().distinct().collect(Collectors.toList()));
        book.setAuthors(book.getAuthors().stream().distinct().collect(Collectors.toList()));
        return book;
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
