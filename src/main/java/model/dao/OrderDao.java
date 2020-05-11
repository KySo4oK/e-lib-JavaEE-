package model.dao;

import model.entity.Order;

public interface OrderDao extends GenericDao<Order> {
    String SQL_FIND_ALL = "select order_id, user_id as \"orderUserId\"," +
            " book_id as \"orderBookId\", active, end_date, start_date from orders";
    String SQL_FIND_BY_ID = SQL_FIND_ALL + " where order_id = ?";
    String SQL_FIND_BY_ACTIVE = SQL_FIND_ALL + " where active = ?";
    String SQL_INSERT = "insert into orders (book_id, user_id, active, end_date, start_date) values (?, ?, ?, ?, ?)";
    String SQL_UPDATE = "update orders set active where order_id = ?";
    String SQL_DELETE = "delete from orders where order_id = ?";

    Order findByActive(Boolean active);
}
