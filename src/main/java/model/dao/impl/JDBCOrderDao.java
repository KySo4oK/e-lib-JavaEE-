package model.dao.impl;

import model.dao.OrderDao;
import model.dao.mapper.impl.BookMapper;
import model.dao.mapper.impl.OrderMapper;
import model.dao.mapper.impl.UserMapper;
import model.entity.Book;
import model.entity.Order;
import model.entity.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JDBCOrderDao implements OrderDao {
    private Connection connection;
    private OrderMapper orderMapper = new OrderMapper();
    private BookMapper bookMapper = new BookMapper();
    private UserMapper userMapper = new UserMapper();

    public JDBCOrderDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Order> findAllByActive(Boolean active) {
        List<Order> resultList = new ArrayList<>();
        Map<Long, Order> orders = new HashMap<>();
        Map<Long, Book> books = new HashMap<>();
        Map<Long, User> users = new HashMap<>();
        try {
            PreparedStatement statement = connection.prepareStatement(SQL_FIND_BY_ACTIVE);
            statement.setBoolean(1, active);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Order order = orderMapper.extractFromResultSet(rs);
                order = orderMapper.makeUnique(orders, order);
                Book book = bookMapper.extractFromResultSet(rs);
                book = bookMapper.makeUnique(books, book);
                User user = userMapper.extractFromResultSet(rs);
                user = userMapper.makeUnique(users, user);
                order.setUser(user);
                order.setBook(book);
                resultList.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultList;
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
        try {
            PreparedStatement statement = connection.prepareStatement(SQL_FIND_BY_ID);
            statement.setLong(1, id);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                order = orderMapper.extractFromResultSet(rs);
                order.setBook(bookMapper.extractFromResultSet(rs));
                order.setUser(userMapper.extractFromResultSet(rs));
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
        try {
            PreparedStatement statement = connection.prepareStatement(SQL_FIND_ALL);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Order order = orderMapper.extractFromResultSet(rs);
                order = orderMapper.makeUnique(orders, order);
                Book book = bookMapper.extractFromResultSet(rs);
                book = bookMapper.makeUnique(books, book);
                User user = userMapper.extractFromResultSet(rs);
                user = userMapper.makeUnique(users, user);
                order.setUser(user);
                order.setBook(book);
                resultList.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultList;
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
