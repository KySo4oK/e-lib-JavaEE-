package model.dao.impl;

import model.dao.ShelfDao;
import model.dao.mapper.impl.BookMapper;
import model.dao.mapper.impl.ShelfMapper;
import model.entity.Book;
import model.entity.Shelf;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JDBCShelfDao implements ShelfDao {
    private final Connection connection;
    private final ShelfMapper shelfMapper = new ShelfMapper();

    public JDBCShelfDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Shelf findByBookId(Long bookId) {
        Shelf shelf = null;
        try {
            PreparedStatement statement = connection.prepareStatement(SQL_FIND_BY_BOOK_ID);
            statement.setLong(1, bookId);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                shelf = shelfMapper.fullExtractFromResultSet(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return shelf;
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
    public Shelf findById(int id) {
        Shelf shelf = null;
        try {
            PreparedStatement statement = connection.prepareStatement(SQL_FIND_BY_ID);
            statement.setLong(1, id);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                shelf = shelfMapper.fullExtractFromResultSet(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return shelf;
    }

    @Override
    public List<Shelf> findAll() {
        List<Shelf> shelves = new ArrayList<>();
        try (Statement st = connection.createStatement()) {
            ResultSet rs = st.executeQuery(SQL_FIND_ALL);
            while (rs.next()) {
                Shelf shelf = shelfMapper.fullExtractFromResultSet(rs);
                shelves.add(shelf);
            }
            return shelves;
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
