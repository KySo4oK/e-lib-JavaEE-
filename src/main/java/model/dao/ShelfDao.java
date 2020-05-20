package model.dao;

import model.entity.Shelf;

import java.util.Optional;

public interface ShelfDao extends GenericDao<Shelf> {
    String SQL_FIND_ALL = "select book.book_id     as \"bookId\",\n" +
            "       book.name        as \"bookName\",\n" +
            "       book.name_ua     as \"bookNameUa\",\n" +
            "       book.available,\n" +
            "       shelf.shelf_id   as \"shelf_id\"\n" +
            "from shelf\n" +
            "         left join book on shelf.book_id = book.book_id";
    String SQL_FIND_BY_ID = SQL_FIND_ALL + " where shelf_id = ?";
    String SQL_FIND_BY_BOOK_ID = SQL_FIND_ALL + " where book.book_id = ?";
    String SQL_INSERT = "insert into shelf (book_id) values (?)";
    String SQL_UPDATE = "update shelf set name where shelf_id = ?";
    String SQL_DELETE = "delete from shelf where shelf_id = ?";

    Optional<Shelf> findByBookId(Long bookId);
}
