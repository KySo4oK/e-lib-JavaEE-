package model.dao.impl;

import model.dao.OrderDao;
import model.dao.mapper.impl.OrderMapper;
import model.entity.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Date;
import java.sql.*;
import java.util.*;

public class JDBCOrderDao implements OrderDao {
    private final Connection connection;
    private final OrderMapper orderMapper = new OrderMapper();
    private final static Logger log = LogManager.getLogger(JDBCOrderDao.class);

    public JDBCOrderDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Order> findAllByActive(Boolean active) {
        Map<Long, Order> orders = new HashMap<>();
        Map<Long, Book> books = new HashMap<>();
        Map<Long, User> users = new HashMap<>();

        try (PreparedStatement statement = connection.prepareStatement(SQL_FIND_BY_ACTIVE)) {
            statement.setBoolean(1, active);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                orderMapper.extractWithRelationsFromResultSet(rs, orders, books, users);
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
        return new ArrayList<>(orders.values());
    }

    @Override
    public List<Order> findAllByActiveAndUser_Username(boolean active, String username) {
        Map<Long, Order> orders = new HashMap<>();
        Map<Long, Book> books = new HashMap<>();
        Map<Long, User> users = new HashMap<>();

        try (PreparedStatement statement = connection.prepareStatement(SQL_FIND_BY_ACTIVE_AND_USERNAME)) {
            statement.setBoolean(1, active);
            statement.setString(2, username);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                orderMapper.extractWithRelationsFromResultSet(rs, orders, books, users);
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
        return new ArrayList<>(orders.values());
    }

    @Override
    public void create(Order entity) {
        try (PreparedStatement statement = connection.prepareStatement(SQL_INSERT)) {
            statement.setLong(1, entity.getBook().getBookId());
            statement.setLong(2, entity.getUser().getId());
            statement.setBoolean(3, entity.isActive());
            statement.setDate(4, Date.valueOf(entity.getEndDate()));
            statement.setDate(5, Date.valueOf(entity.getStartDate()));
            statement.execute();
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Order> findById(long id) {
        Order order = null;
        Map<Long, Order> orders = new HashMap<>();
        Map<Long, Book> books = new HashMap<>();
        Map<Long, User> users = new HashMap<>();
        try (PreparedStatement statement = connection.prepareStatement(SQL_FIND_BY_ID)) {
            statement.setLong(1, id);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                order = orderMapper.extractWithRelationsFromResultSet(rs, orders, books, users);
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
        return Optional.ofNullable(order);
    }

    @Override
    public List<Order> findAll() {
        Map<Long, Order> orders = new HashMap<>();
        Map<Long, Book> books = new HashMap<>();
        Map<Long, User> users = new HashMap<>();
        try (PreparedStatement statement = connection.prepareStatement(SQL_FIND_ALL)) {
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                orderMapper.extractWithRelationsFromResultSet(rs, orders, books, users);
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
        return new ArrayList<>(orders.values());
    }

    @Override
    public void update(Order entity) {
        try (PreparedStatement statement = connection.prepareStatement(SQL_UPDATE)) {
            statement.setBoolean(1, entity.isActive());
            statement.setLong(2, entity.getOrderId());
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