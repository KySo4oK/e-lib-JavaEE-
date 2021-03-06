package model.dao.mapper.impl;

import model.dao.mapper.ObjectMapper;
import model.entity.Book;
import model.entity.Shelf;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class ShelfMapper implements ObjectMapper<Shelf> {
    private final BookMapper bookMapper;

    public ShelfMapper() {
        this.bookMapper = new BookMapper(this);
    }

    public ShelfMapper(BookMapper bookMapper) {
        this.bookMapper = bookMapper;
    }

    @Override
    public Shelf extractWithoutRelationsFromResultSet(ResultSet rs) throws SQLException {
        return new Shelf(rs.getLong("shelf_id"));
    }

    public Shelf extractWithRelationsFromResultSet(ResultSet rs) throws SQLException {
        Shelf shelf = extractWithoutRelationsFromResultSet(rs);
        setBookIfNeeded(rs, shelf);
        return shelf;
    }

    private void setBookIfNeeded(ResultSet rs, Shelf shelf) throws SQLException {
        if (rs.getLong("booKId") != 0) {
            Book book = bookMapper.extractWithoutRelationsFromResultSet(rs);
            shelf.setBook(book);
            book.setShelf(shelf);
        }
    }

    @Override
    public Shelf makeUnique(Map<Long, Shelf> cache, Shelf shelf) {
        cache.putIfAbsent(shelf.getShelfId(), shelf);
        return cache.get(shelf.getShelfId());
    }
}
