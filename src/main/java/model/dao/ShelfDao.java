package model.dao;

import model.entity.Shelf;

public interface ShelfDao extends GenericDao<Shelf> {
    String SQL_FIND_ALL = "select shelf.shelf_id, book.book_id as \"bookId\", book.name as \"bookName\",\n" +
            "       book.name_ua as \"bookNameUa\", book.available as \"available\"\n" +
            "from shelf left join book on shelf.book_id = book.book_id";
    String SQL_FIND_BY_ID = SQL_FIND_ALL + " where shelf_id = ?";
    String SQL_FIND_BY_BOOK_ID = SQL_FIND_ALL + " where book.book_id = ?";
    String SQL_INSERT = "insert into shelf (book_id) values (?)";
    String SQL_UPDATE = "update shelf set name where shelf_id = ?";
    String SQL_DELETE = "delete from shelf where shelf_id = ?";

    Shelf findByBookId(Long bookId);
}
