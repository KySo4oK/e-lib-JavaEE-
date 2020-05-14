package model.dao.mapper.impl;

import model.dao.mapper.ObjectMapper;
import model.entity.Book;
import model.entity.Shelf;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class ShelfMapper implements ObjectMapper<Shelf> {
    private final BookMapper bookMapper = new BookMapper();

    @Override
    public Shelf extractFromResultSet(ResultSet rs) throws SQLException {
        Shelf shelf = new Shelf();
        shelf.setShelfId(rs.getLong("shelf_id"));
        if (rs.getLong("booKId") != 0) {
            Book book = bookMapper.extractFromResultSet(rs);
            shelf.setBook(book);
            book.setShelf(shelf);
        }
        return shelf;
    }

    @Override
    public Shelf makeUnique(Map<Long, Shelf> cache, Shelf shelf) {
        cache.putIfAbsent(shelf.getShelfId(), shelf);
        return cache.get(shelf.getShelfId());
    }
}
