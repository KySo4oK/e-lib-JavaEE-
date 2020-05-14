package model.dao.mapper.impl;

import model.dao.mapper.ObjectMapper;
import model.entity.Book;
import model.entity.Shelf;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class BookMapper implements ObjectMapper<Book> {
    private final ShelfMapper shelfMapper = new ShelfMapper();
    @Override
    public Book extractFromResultSet(ResultSet rs) throws SQLException {
        Book book = new Book();
        book.setBookId(rs.getLong("bookId"));
        book.setAvailable(rs.getBoolean("available"));
        book.setName(rs.getString("bookName"));
        book.setNameUa(rs.getString("bookNameUa"));
        if (rs.getLong("shelf_id") != 0L) {
            Shelf shelf = shelfMapper.extractFromResultSet(rs);
            book.setShelf(shelf);
            shelf.setBook(book);
        }
        return book;
    }

    @Override
    public Book makeUnique(Map<Long, Book> cache, Book book) {
        cache.putIfAbsent(book.getBookId(), book);
        return cache.get(book.getBookId());
    }
}
