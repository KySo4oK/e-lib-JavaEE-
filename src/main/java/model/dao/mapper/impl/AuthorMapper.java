package model.dao.mapper.impl;

import model.dao.mapper.ObjectMapper;
import model.entity.Author;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class AuthorMapper implements ObjectMapper<Author> {
    @Override
    public Author extractWithoutRelationsFromResultSet(ResultSet rs) throws SQLException {
        return Author.Builder.anAuthor()
                .authorId(rs.getLong("authorId"))
                .name(rs.getString("authorName"))
                .nameUa(rs.getString("authorNameUa"))
                .build();
    }

    @Override
    public Author makeUnique(Map<Long, Author> cache, Author author) {
        cache.putIfAbsent(author.getAuthorId(), author);
        return cache.get(author.getAuthorId());
    }
}
