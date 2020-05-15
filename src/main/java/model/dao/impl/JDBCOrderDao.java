package model.dao.impl;

import model.dao.OrderDao;
import model.dao.mapper.impl.BookMapper;
import model.dao.mapper.impl.OrderMapper;
import model.dao.mapper.impl.UserMapper;
import model.entity.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class JDBCOrderDao implements OrderDao {
    private final Connection connection;
    private final OrderMapper orderMapper = new OrderMapper();

    public JDBCOrderDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Order> findAllByActive(Boolean active) {
        List<Order> resultList = new ArrayList<>();
        Map<Long, Order> orders = new HashMap<>();
        Map<Long, Book> books = new HashMap<>();
        Map<Long, User> users = new HashMap<>();
        Map<Long, Author> authors = new HashMap<>();
        Map<Long, Tag> tags = new HashMap<>();

        try {
            PreparedStatement statement = connection.prepareStatement(SQL_FIND_BY_ACTIVE);
            statement.setBoolean(1, active);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Order order = orderMapper.fullExtractFromResultSet(rs, orders, books, tags, authors, users);
                resultList.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultList.stream().distinct().collect(Collectors.toList());
    }

    @Override
    public void create(Order entity) {
        try {
            PreparedStatement statement = connection.prepareStatement(SQL_INSERT);
            statement.setLong(1, entity.getBook().getBookId());//todo
            statement.setLong(2, entity.getUser().getId());
            statement.setBoolean(3, entity.isActive());
            statement.setDate(4, Date.valueOf(entity.getEndDate()));
            statement.setDate(5, Date.valueOf(entity.getStartDate()));
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Order findById(int id) {
        Order order = null;
        Map<Long, Order> orders = new HashMap<>();
        Map<Long, Book> books = new HashMap<>();
        Map<Long, User> users = new HashMap<>();
        Map<Long, Author> authors = new HashMap<>();
        Map<Long, Tag> tags = new HashMap<>();
        try {
            PreparedStatement statement = connection.prepareStatement(SQL_FIND_BY_ID);
            statement.setLong(1, id);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                order = orderMapper.fullExtractFromResultSet(rs, orders, books, tags, authors, users);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return order;
    }

    @Override
    public List<Order> findAll() {
        List<Order> resultList = new ArrayList<>();
        Map<Long, Order> orders = new HashMap<>();
        Map<Long, Book> books = new HashMap<>();
        Map<Long, User> users = new HashMap<>();
        Map<Long, Author> authors = new HashMap<>();
        Map<Long, Tag> tags = new HashMap<>();
        try {
            PreparedStatement statement = connection.prepareStatement(SQL_FIND_ALL);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Order order = orderMapper.fullExtractFromResultSet(rs, orders, books, tags, authors, users);
                resultList.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultList.stream().distinct().collect(Collectors.toList());
    }

    @Override
    public void update(Order entity) {
        try {
            PreparedStatement statement = connection.prepareStatement(SQL_UPDATE);
            statement.setBoolean(1, entity.isActive());
            statement.setLong(2, entity.getOrderId());
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
