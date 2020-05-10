package model.dao.impl;

import model.dao.OrderDao;
import model.dao.mapper.ObjectMapper;
import model.dao.mapper.impl.OrderMapper;
import model.dao.mapper.impl.TagMapper;
import model.entity.Order;

import java.sql.*;
import java.util.List;

public class JDBCOrderDao implements OrderDao {
    private Connection connection;
    private OrderMapper orderMapper = new OrderMapper();

    public JDBCOrderDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Order findByActive(Boolean active) {
        Order order = null;
        try {
            PreparedStatement statement = connection.prepareStatement(SQL_FIND_BY_ACTIVE);
            statement.setBoolean(1, active);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                order = orderMapper.extractFromResultSet(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return order;
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
        return null;
    }

    @Override
    public List<Order> findAll() {
        return null;
    }

    @Override
    public void update(Order entity) {

    }

    @Override
    public void delete(int id) {

    }

    @Override
    public void close() {

    }
}
