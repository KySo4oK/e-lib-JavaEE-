package model.dao.mapper.impl;

import model.dao.mapper.ObjectMapper;
import model.entity.Author;
import model.entity.Book;
import model.entity.Shelf;
import model.entity.Tag;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.stream.Collectors;

public class BookMapper implements ObjectMapper<Book> {
    private final ShelfMapper shelfMapper = new ShelfMapper();
    private final TagMapper tagMapper = new TagMapper();
    private final AuthorMapper authorMapper = new AuthorMapper();

    @Override
    public Book extractFromResultSet(ResultSet rs) throws SQLException {
        Book book = new Book();
        book.setBookId(rs.getLong("bookId"));
        book.setAvailable(rs.getBoolean("available"));
        book.setName(rs.getString("bookName"));
        book.setNameUa(rs.getString("bookNameUa"));
        setShelfIfNeeded(rs, book);
        return book;
    }

    public Book fullExtractFromResultSet(ResultSet rs,
                                         Map<Long, Book> books,
                                         Map<Long, Tag> tags,
                                         Map<Long, Author> authors) throws SQLException {
        Book book = extractFromResultSet(rs);
        book = makeUnique(books, book);
        Tag tag = tagMapper.extractFromResultSet(rs);
        Author author = authorMapper.extractFromResultSet(rs);
        tag = tagMapper.makeUnique(tags, tag);
        author = authorMapper.makeUnique(authors, author);
        book.getAuthors().add(author);
        book.getTags().add(tag);
        book.setTags(book.getTags().stream().distinct().collect(Collectors.toList()));
        book.setAuthors(book.getAuthors().stream().distinct().collect(Collectors.toList()));
        return book;
    }

    private void setShelfIfNeeded(ResultSet rs, Book book) throws SQLException {
        if (rs.getLong("shelf_id") != 0L) {
            Shelf shelf = shelfMapper.extractFromResultSet(rs);
            book.setShelf(shelf);
            shelf.setBook(book);
        }
    }

    @Override
    public Book makeUnique(Map<Long, Book> cache, Book book) {
        cache.putIfAbsent(book.getBookId(), book);
        return cache.get(book.getBookId());
    }
}
