package model.dao.mapper;

import model.entity.Author;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class AuthorMapper implements ObjectMapper<Author>{
    @Override
    public Author extractFromResultSet(ResultSet rs) throws SQLException {
        return null;
    }

    @Override
    public Author makeUnique(Map<Integer, Author> cache, Author teacher) {
        return null;
    }
}
