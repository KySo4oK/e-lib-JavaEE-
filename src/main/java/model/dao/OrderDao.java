package model.dao;

import model.entity.Order;

import java.util.List;

public interface OrderDao extends GenericDao<Order> {
    String SQL_FIND_ALL = "select order_id,\n" +
            "       active,\n" +
            "       end_date,\n" +
            "       start_date,\n" +
            "       book.book_id     as \"bookId\",\n" +
            "       book.name_ua     as \"bookNameUa\",\n" +
            "       book.name        as \"bookName\",\n" +
            "       book.available   as \"available\",\n" +
            "       users.username   as \"username\",\n" +
            "       users.user_id    as \"userId\",\n" +
            "       users.email      as \"email\",\n" +
            "       users.password   as \"password\",\n" +
            "       users.role       as \"role\",\n" +
            "       users.phone      as \"phone\",\n" +
            "       tag.tag_id       as \"tagId\",\n" +
            "       tag.name_ua      as \"tagNameUa\",\n" +
            "       tag.name         as \"tagName\",\n" +
            "       author.author_id as \"authorId\",\n" +
            "       author.name_ua   as \"authorNameUa\",\n" +
            "       author.name      as \"authorName\",\n" +
            "       shelf.shelf_id   as \"shelf_id\"\n" +
            "from orders\n" +
            "         left join book on orders.book_id = book.book_id\n" +
            "         left join book_tag on book.book_id = book_tag.book_id\n" +
            "         left join tag on book_tag.tag_id = tag.tag_id\n" +
            "         left join book_author on book.book_id = book_author.book_id\n" +
            "         left join author on book_author.author_id = author.author_id\n" +
            "         left join shelf on book.book_id = shelf.book_id\n" +
            "         left join users on orders.user_id = users.user_id";
    String SQL_FIND_BY_ID = SQL_FIND_ALL + " where order_id = ?";
    String SQL_FIND_BY_ACTIVE = SQL_FIND_ALL + " where active = ?";
    String SQL_FIND_BY_ACTIVE_AND_USERNAME = SQL_FIND_ALL + " where active = ? and users.username = ?";
    String SQL_INSERT = "insert into orders (book_id, user_id, active, end_date, start_date) values (?, ?, ?, ?, ?)";
    String SQL_UPDATE = "update orders set active = ? where order_id = ?";
    String SQL_DELETE = "delete from orders where order_id = ?";

    List<Order> findAllByActive(Boolean active);

    List<Order> findAllByActiveAndUser_Username(boolean active, String username);
}
