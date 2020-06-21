package model.dao.mapper.impl;

import model.dao.mapper.ObjectMapper;
import model.entity.Book;
import model.entity.Order;
import model.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class OrderMapper implements ObjectMapper<Order> {
    private final BookMapper bookMapper = new BookMapper();
    private final UserMapper userMapper = new UserMapper();

    @Override
    public Order extractFromResultSet(ResultSet rs) throws SQLException {
        return Order.Builder.anOrder()
                .orderId(rs.getLong("order_id"))
                .active(rs.getBoolean("active"))
                .endDate(rs.getDate("end_date").toLocalDate())
                .startDate(rs.getDate("start_date").toLocalDate())
                .build();
    }

    public Order fullExtractFromResultSet(ResultSet rs,
                                          Map<Long, Order> orders,
                                          Map<Long, Book> books,
                                          Map<Long, User> users) throws SQLException {
        Order order = extractFromResultSet(rs);
        order = makeUnique(orders, order);
        Book book = bookMapper.extractFromResultSet(rs);
        book = bookMapper.makeUnique(books, book);
        User user = userMapper.extractFromResultSet(rs);
        user = userMapper.makeUnique(users, user);
        order.setUser(user);
        order.setBook(book);
        return order;
    }

    @Override
    public Order makeUnique(Map<Long, Order> cache, Order order) {
        cache.putIfAbsent(order.getOrderId(), order);
        return cache.get(order.getOrderId());
    }
}
