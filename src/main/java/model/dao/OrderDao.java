package model.dao;

import model.entity.Order;

public interface OrderDao extends GenericDao<Order> {
    String SQL_FIND_ALL = "select order_id,\n" +
            "       active,\n" +
            "       end_date,\n" +
            "       start_date,\n" +
            "       book.book_id   as \"bookId\",\n" +
            "       book.name_ua   as \"bookNameUa\",\n" +
            "       book.name      as \"bookName\",\n" +
            "       book.available as \"available\",\n" +
            "       users.username as \"username\",\n" +
            "       users.user_id  as \"userId\",\n" +
            "       users.email    as \"email\",\n" +
            "       users.password as \"password\",\n" +
            "       users.role     as \"role\",\n" +
            "       users.phone    as \"phone\"\n" +
            "from orders\n" +
            "         left join book on orders.book_id = book.book_id\n" +
            "         left join users on orders.user_id = users.user_id";
    String SQL_FIND_BY_ID = SQL_FIND_ALL + " where order_id = ?";
    String SQL_FIND_BY_ACTIVE = SQL_FIND_ALL + " where active = ?";
    String SQL_INSERT = "insert into orders (book_id, user_id, active, end_date, start_date) values (?, ?, ?, ?, ?)";
    String SQL_UPDATE = "update orders set active = ? where order_id = ?";
    String SQL_DELETE = "delete from orders where order_id = ?";

    Order findByActive(Boolean active);
}
