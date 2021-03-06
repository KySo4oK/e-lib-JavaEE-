package model.dao.impl;

import model.dao.BookDao;
import model.dao.mapper.impl.BookMapper;
import model.entity.Author;
import model.entity.Book;
import model.entity.Pageable;
import model.entity.Tag;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.*;

public class JDBCBookDao implements BookDao {
    private final Connection connection;
    private final BookMapper bookMapper = new BookMapper();
    private final static Logger log = LogManager.getLogger(JDBCBookDao.class);

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
        try (PreparedStatement statement = connection.prepareStatement(sqlFindByName)) {
            statement.setString(1, name);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                book = bookMapper.extractWithRelationsFromResultSet(rs, books, tags, authors);
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        return Optional.ofNullable(book);
    }

    @Override
    public Optional<Book> findByNameUa(String nameUa) {
        return findBookByName(nameUa, SQL_FIND_BY_NAME_UA);
    }

    @Override
    public List<Book> getBooksByFilter(String partOfName, String[] authorsStrings, String[] tagString, Pageable pageable) {
        return getByFilter(partOfName, authorsStrings, tagString, SQL_FIND_BY_FILTER, pageable);
    }

    @Override
    public List<Book> getBooksByFilterUa(String partOfName, String[] authorsStrings, String[] tagString, Pageable pageable) {
        return getByFilter(partOfName, authorsStrings, tagString, SQL_FIND_BY_FILTER_UA, pageable);
    }

    @Override
    public List<Book> findAllByAvailableIsTrue(Pageable pageable) {
        Map<Long, Book> books = new HashMap<>();
        Map<Long, Tag> tags = new HashMap<>();
        Map<Long, Author> authors = new HashMap<>();
        try (PreparedStatement statement = connection.prepareStatement(SQL_FIND_ALL_AVAILABLE)) {
            statement.setInt(1, pageable.getNumber());
            statement.setInt(2, pageable.getNumber() * pageable.getPage());
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                bookMapper.extractWithRelationsFromResultSet(rs, books, tags, authors);
            }
        } catch (SQLException e) {
            log.error(e.getMessage() + " when trying findAll books");
            throw new RuntimeException(e);
        }
        return new ArrayList<>(books.values());
    }

    private List<Book> getByFilter(String partOfName,
                                   String[] authorsStrings,
                                   String[] tagsStrings,
                                   String sqlFindByFilterUa, Pageable pageable) {
        Map<Long, Book> books = new HashMap<>();
        Map<Long, Tag> tags = new HashMap<>();
        Map<Long, Author> authors = new HashMap<>();
        try (PreparedStatement statement = connection.prepareStatement(sqlFindByFilterUa)) {
            statement.setArray(1,
                    statement.getConnection().createArrayOf("text", authorsStrings));
            statement.setArray(2,
                    statement.getConnection().createArrayOf("text", tagsStrings));
            statement.setString(3, partOfName);
            statement.setInt(4, pageable.getNumber());
            statement.setInt(5, pageable.getNumber() * pageable.getPage());
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                bookMapper.extractWithRelationsFromResultSet(rs, books, tags, authors);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return new ArrayList<>(books.values());
    }

    @Override
    public void create(Book entity) {
        try (PreparedStatement statement = connection.prepareStatement(SQL_INSERT_BOOK_FIELDS);
             PreparedStatement statementForAuthors = connection.prepareStatement(SQL_INSERT_INTO_BOOK_AUTHOR)) {
            connection.setAutoCommit(false);
            statement.setString(1, entity.getName());
            statement.setString(2, entity.getNameUa());
            statement.setBoolean(3, entity.isAvailable());
            statement.setLong(4, entity.getTag().getTagId());

            for (Author author : entity.getAuthors()) {
                statementForAuthors.setLong(1, author.getAuthorId());
                statementForAuthors.addBatch();
            }

            statement.execute();
            statementForAuthors.executeBatch();
            connection.commit();
        } catch (SQLException e) {
            log.warn(e);
            try {
                connection.rollback();
            } catch (SQLException e1) {
                log.fatal(e1);
                throw new RuntimeException(e1);
            }
        }
    }

    @Override
    public Optional<Book> findById(long id) {
        Book book = null;
        Map<Long, Book> books = new HashMap<>();
        Map<Long, Tag> tags = new HashMap<>();
        Map<Long, Author> authors = new HashMap<>();
        try (PreparedStatement statement = connection.prepareStatement(SQL_FIND_BY_ID)) {
            statement.setLong(1, id);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                book = bookMapper.extractWithRelationsFromResultSet(rs, books, tags, authors);
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        return Optional.ofNullable(book);
    }

    @Override
    public List<Book> findAll() {
        Map<Long, Book> books = new HashMap<>();
        Map<Long, Tag> tags = new HashMap<>();
        Map<Long, Author> authors = new HashMap<>();
        try (Statement st = connection.createStatement()) {
            ResultSet rs = st.executeQuery(SQL_FIND_ALL);
            while (rs.next()) {
                bookMapper.extractWithRelationsFromResultSet(rs, books, tags, authors);
            }
        } catch (SQLException e) {
            log.error(e.getMessage() + " when trying findAll books");
            throw new RuntimeException(e);
        }
        return new ArrayList<>(books.values());
    }

    @Override
    public void update(Book entity) {
        try (PreparedStatement statement = connection.prepareStatement(SQL_UPDATE)) {
            statement.setBoolean(1, entity.isAvailable());
            statement.setString(2, entity.getName());
            statement.setString(3, entity.getNameUa());
            statement.setLong(4, entity.getBookId());
            statement.execute();
        } catch (SQLException e) {
            log.error(e.getMessage() + "when trying update book");
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(long id) {
        try (PreparedStatement statement = connection.prepareStatement(SQL_DELETE)) {
            connection.setAutoCommit(false);
            statement.setLong(1, id);
            PreparedStatement statement1 = connection.prepareStatement(SQL_DELETE_ID_IN_SHELF);
            statement1.setLong(1, id);

            statement.execute();
            statement1.execute();
            connection.commit();
        } catch (SQLException e) {
            log.error(e.getMessage() + " when trying commit transaction");
            try {
                connection.rollback();
            } catch (SQLException e1) {
                log.fatal(e1.getMessage() + " when trying rollback transaction");
                throw new RuntimeException(e1);
            }
        }
    }

    @Override
    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            log.error(e.getMessage() + " when trying close");
            throw new RuntimeException(e);
        }
    }
}
