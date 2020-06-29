package model.dao.impl;

import model.dao.ShelfDao;
import model.dao.mapper.impl.ShelfMapper;
import model.entity.Shelf;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class JDBCShelfDao implements ShelfDao {
    private final Connection connection;
    private final ShelfMapper shelfMapper = new ShelfMapper();
    private final static Logger log = LogManager.getLogger(JDBCShelfDao.class);

    public JDBCShelfDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Optional<Shelf> findByBookId(Long bookId) {
        Shelf shelf = null;
        if (bookId == null) {
            try (Statement statement = connection.createStatement()) {
                ResultSet rs = statement.executeQuery(SQL_FIND_EMPTY);
                while (rs.next()) {
                    shelf = shelfMapper.fullExtractFromResultSet(rs);
                }
            } catch (SQLException e) {
                log.error(e.getMessage());
                throw new RuntimeException(e);
            }
        } else {
            try (PreparedStatement statement = connection.prepareStatement(SQL_FIND_BY_BOOK_ID)) {
                statement.setObject(1, bookId, Types.BIGINT);
                ResultSet rs = statement.executeQuery();
                while (rs.next()) {
                    shelf = shelfMapper.fullExtractFromResultSet(rs);
                }
            } catch (SQLException e) {
                log.error(e.getMessage());
                throw new RuntimeException(e);
            }
        }
        return Optional.ofNullable(shelf);
    }

    @Override
    public void create(Shelf entity) {
        try (PreparedStatement statement = connection.prepareStatement(SQL_INSERT)) {
            if (entity.getBook() != null) {
                statement.setLong(1, entity.getBook().getBookId());
            } else {
                statement.setNull(1, Types.BIGINT);
            }
            statement.execute();
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Shelf> findById(long id) {
        Shelf shelf = null;
        try (PreparedStatement statement = connection.prepareStatement(SQL_FIND_BY_ID)) {
            statement.setLong(1, id);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                shelf = shelfMapper.fullExtractFromResultSet(rs);
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
        return Optional.ofNullable(shelf);
    }

    @Override
    public List<Shelf> findAll() {
        List<Shelf> shelves = new ArrayList<>();
        try (Statement st = connection.createStatement()) {
            ResultSet rs = st.executeQuery(SQL_FIND_ALL);
            while (rs.next()) {
                shelves.add(shelfMapper.fullExtractFromResultSet(rs));
            }
            return shelves.stream().distinct().collect(Collectors.toList());
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Shelf entity) {
        try (PreparedStatement statement = connection.prepareStatement(SQL_UPDATE)) {
            statement.setLong(1, entity.getBook().getBookId());
            statement.setLong(2, entity.getShelfId());
            statement.execute();
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(long id) {
        try (PreparedStatement statement = connection.prepareStatement(SQL_DELETE)) {
            statement.setLong(1, id);
            statement.execute();
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
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
