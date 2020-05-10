package model.dao.mapper;

import model.entity.Shelf;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class ShelfMapper implements ObjectMapper<Shelf> {
    @Override
    public Shelf extractFromResultSet(ResultSet rs) throws SQLException {
        return null;
    }

    @Override
    public Shelf makeUnique(Map<Integer, Shelf> cache, Shelf teacher) {
        return null;
    }
}
