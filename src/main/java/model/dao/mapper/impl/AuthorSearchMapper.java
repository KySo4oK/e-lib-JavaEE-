package model.dao.mapper.impl;

import model.dao.mapper.ObjectMapper;
import model.entity.AuthorSearch;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class AuthorSearchMapper implements ObjectMapper<AuthorSearch> {
    @Override
    public AuthorSearch extractFromResultSet(ResultSet rs) throws SQLException {
        AuthorSearch authorSearch = new AuthorSearch();
        authorSearch.setSearchDate(rs.getDate("search_date").toLocalDate());
        authorSearch.setAuthorSearchId(rs.getLong("author_search_id"));
        return authorSearch;
    }

    @Override
    public AuthorSearch makeUnique(Map<Long, AuthorSearch> cache, AuthorSearch authorSearch) {
        cache.putIfAbsent(authorSearch.getAuthorSearchId(), authorSearch);
        return cache.get(authorSearch.getAuthorSearchId());
    }
}
