package model.dao;

import model.entity.Shelf;

public interface ShelfDao extends GenericDao<Shelf> {
    String SQL_FIND_ALL = "select shelf_id, book_id as \"shelfBookId\" from shelf";
    String SQL_FIND_BY_ID = SQL_FIND_ALL + " where shelf_id = ?";
    String SQL_FIND_BY_BOOK_ID = SQL_FIND_ALL + " where book_id = ?";
    String SQL_INSERT = "insert into shelf (book_id) values (?)";
    String SQL_UPDATE = "update shelf set name where shelf_id = ?";
    String SQL_DELETE = "delete from shelf where shelf_id = ?";

    Shelf findByBookId(Long bookId);
}
