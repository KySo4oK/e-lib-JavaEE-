package model.dao.mapper;

import model.entity.Book;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class BookMapper implements ObjectMapper<Book>{
    @Override
    public Book extractFromResultSet(ResultSet rs) throws SQLException {
        return null;
    }

    @Override
    public Book makeUnique(Map<Integer, Book> cache, Book teacher) {
        return null;
    }
}
