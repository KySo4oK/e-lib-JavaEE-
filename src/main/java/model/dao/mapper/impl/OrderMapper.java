package model.dao.mapper.impl;

import model.dao.mapper.ObjectMapper;
import model.entity.Book;
import model.entity.Order;
import model.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class OrderMapper implements ObjectMapper<Order> {
    @Override
    public Order extractFromResultSet(ResultSet rs) throws SQLException {
        Order order = new Order();
        order.setOrderId(rs.getLong("order_id"));// todo add book and user id
        order.setActive(rs.getBoolean("active"));
        order.setEndDate(rs.getDate("end_date").toLocalDate());
        order.setStartDate(rs.getDate("start_date").toLocalDate());
        return order;
    }

    @Override
    public Order makeUnique(Map<Long, Order> cache, Order order) {
        cache.putIfAbsent(order.getOrderId(), order);
        return cache.get(order.getOrderId());
    }
}
