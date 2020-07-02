package model.dao.mapper.impl;

import model.dao.mapper.ObjectMapper;
import model.entity.Author;
import model.entity.Book;
import model.entity.Tag;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class BookMapper implements ObjectMapper<Book> {
    private final ShelfMapper shelfMapper;
    private final TagMapper tagMapper = new TagMapper();
    private final AuthorMapper authorMapper = new AuthorMapper();

    public BookMapper(ShelfMapper shelfMapper) {
        this.shelfMapper = shelfMapper;
    }

    public BookMapper() {
        this.shelfMapper = new ShelfMapper(this);
    }

    @Override
    public Book extractWithoutRelationsFromResultSet(ResultSet rs) throws SQLException {
        return Book.Builder.aBook()
                .bookId(rs.getLong("bookId"))
                .available(rs.getBoolean("available"))
                .name(rs.getString("bookName"))
                .nameUa(rs.getString("bookNameUa"))
                .build();
    }

    public Book extractWithRelationsFromResultSet(ResultSet rs,
                                                  Map<Long, Book> books,
                                                  Map<Long, Tag> tags,
                                                  Map<Long, Author> authors) throws SQLException {
        Book book = makeUnique(books, extractWithoutRelationsFromResultSet(rs));
        book.getAuthors().add(authorMapper.makeUnique(authors, authorMapper.extractWithoutRelationsFromResultSet(rs)));
        book.setTag(tagMapper.makeUnique(tags, tagMapper.extractWithoutRelationsFromResultSet(rs)));
        return book;
    }

    @Override
    public Book makeUnique(Map<Long, Book> cache, Book book) {
        cache.putIfAbsent(book.getBookId(), book);
        return cache.get(book.getBookId());
    }
}
