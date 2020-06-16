package model.dao.mapper;

import model.entity.AuthorSearch;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class AuthorSearchMapper implements ObjectMapper<AuthorSearch> {
    @Override
    public AuthorSearch extractFromResultSet(ResultSet rs) throws SQLException {
        return null;
    }

    @Override
    public AuthorSearch makeUnique(Map<Long, AuthorSearch> cache, AuthorSearch teacher) {
        return null;
    }
}
