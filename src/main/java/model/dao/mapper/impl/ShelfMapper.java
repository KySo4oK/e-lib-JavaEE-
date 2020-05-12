package model.dao.mapper.impl;

import model.dao.mapper.ObjectMapper;
import model.entity.Shelf;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class ShelfMapper implements ObjectMapper<Shelf> {
    @Override
    public Shelf extractFromResultSet(ResultSet rs) throws SQLException {
        Shelf shelf = new Shelf();
        shelf.setShelfId(rs.getLong("shelf_id"));
        return shelf;
    }

    @Override
    public Shelf makeUnique(Map<Long, Shelf> cache, Shelf shelf) {
        cache.putIfAbsent(shelf.getShelfId(), shelf);
        return cache.get(shelf.getShelfId());
    }
}
