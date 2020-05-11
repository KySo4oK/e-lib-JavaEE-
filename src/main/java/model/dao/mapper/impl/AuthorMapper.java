package model.dao.mapper.impl;

import model.dao.mapper.ObjectMapper;
import model.entity.Author;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class AuthorMapper implements ObjectMapper<Author> {
    @Override
    public Author extractFromResultSet(ResultSet rs) throws SQLException {
        Author author = new Author();
        author.setAuthorId(rs.getLong("authorId"));
        author.setName(rs.getString("authorName"));
        author.setNameUa(rs.getString("authorNameUa"));
        return author;
    }

    @Override
    public Author makeUnique(Map<Long, Author> cache, Author author) {
        cache.putIfAbsent(author.getAuthorId(), author);
        return cache.get(author.getAuthorId());
    }
}
