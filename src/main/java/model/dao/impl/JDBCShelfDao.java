package model.dao.impl;

import model.dao.ShelfDao;
import model.dao.mapper.impl.BookMapper;
import model.dao.mapper.impl.ShelfMapper;
import model.entity.Author;
import model.entity.Book;
import model.entity.Shelf;
import model.entity.Tag;

import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

public class JDBCShelfDao implements ShelfDao {
    private final Connection connection;
    private final ShelfMapper shelfMapper = new ShelfMapper();

    public JDBCShelfDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Optional<Shelf> findByBookId(Long bookId) {
        Shelf shelf = null;
        Map<Long, Book> books = new HashMap<>();
        Map<Long, Tag> tags = new HashMap<>();
        Map<Long, Author> authors = new HashMap<>();
        try {
            PreparedStatement statement = connection.prepareStatement(SQL_FIND_BY_BOOK_ID);
            statement.setLong(1, bookId);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                shelf = shelfMapper.fullExtractFromResultSet(rs, books, tags, authors);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.ofNullable(shelf);
    }

    @Override
    public void create(Shelf entity) {
        try {
            PreparedStatement statement = connection.prepareStatement(SQL_INSERT);
            statement.setLong(1, entity.getBook().getBookId()); //todo
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<Shelf> findById(int id) {
        Shelf shelf = null;
        Map<Long, Book> books = new HashMap<>();
        Map<Long, Tag> tags = new HashMap<>();
        Map<Long, Author> authors = new HashMap<>();
        try {
            PreparedStatement statement = connection.prepareStatement(SQL_FIND_BY_ID);
            statement.setLong(1, id);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                shelf = shelfMapper.fullExtractFromResultSet(rs, books, tags, authors);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.ofNullable(shelf);
    }

    @Override
    public List<Shelf> findAll() {
        List<Shelf> shelves = new ArrayList<>();
        Map<Long, Book> books = new HashMap<>();
        Map<Long, Tag> tags = new HashMap<>();
        Map<Long, Author> authors = new HashMap<>();
        try (Statement st = connection.createStatement()) {
            ResultSet rs = st.executeQuery(SQL_FIND_ALL);
            while (rs.next()) {
                Shelf shelf = shelfMapper.fullExtractFromResultSet(rs, books, tags, authors);
                shelves.add(shelf);
            }
            return shelves.stream().distinct().collect(Collectors.toList());
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void update(Shelf entity) {
        try {
            PreparedStatement statement = connection.prepareStatement(SQL_UPDATE);
            statement.setLong(1, entity.getBook().getBookId());//todo
            statement.setLong(2, entity.getShelfId());
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
