package model.entity;

import java.util.List;

public class BookBuilder {
    private Long bookId;
    private String name;
    private String nameUa;
    private List<Author> authors;
    private List<Tag> tags;
    private boolean available;
    private Shelf shelf;

    public BookBuilder bookId(Long bookId) {
        this.bookId = bookId;
        return this;
    }

    public BookBuilder name(String name) {
        this.name = name;
        return this;
    }

    public BookBuilder nameUa(String nameUa) {
        this.nameUa = nameUa;
        return this;
    }

    public BookBuilder authors(List<Author> authors) {
        this.authors = authors;
        return this;
    }

    public BookBuilder tags(List<Tag> tags) {
        this.tags = tags;
        return this;
    }

    public BookBuilder available(boolean available) {
        this.available = available;
        return this;
    }

    public BookBuilder shelf(Shelf shelf) {
        this.shelf = shelf;
        return this;
    }

    public Book createBook() {
        return new Book(bookId, name, nameUa, authors, tags, available, shelf);
    }
}