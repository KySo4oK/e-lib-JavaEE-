package model.dao;

import model.entity.Book;

public interface BookDao extends GenericDao<Book> {
    String SQL_FIND_ALL = "select * from book";
    String SQL_FIND_BY_ID = SQL_FIND_ALL + " book_id = ?";
    String SQL_FIND_BY_NAME = SQL_FIND_ALL + " where name = ?";
    String SQL_FIND_BY_NAME_UA = SQL_FIND_ALL + " where name_ua = ?";
    String SQL_INSERT = "insert into book (book_id, name, name_ua, available) values (?, ?, ?, ?)";
    String SQL_UPDATE = "update book set available where book_id = ?";
    String SQL_DELETE = "delete from book where book_id = ?";

    Book findByName(String name);

    Book findByNameUa(String nameUa);
}
