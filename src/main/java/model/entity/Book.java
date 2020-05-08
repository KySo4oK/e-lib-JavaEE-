package model.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Book {
    private Long bookId;
    private String name;
    private String nameUa;
    private List<Author> authors = new ArrayList<>();
    private List<Tag> tags = new ArrayList<>();
    private boolean available = true;
    private Shelf shelf;

    public Book() {
    }

    public Book(Long bookId, String name, String nameUa, List<Author> authors, List<Tag> tags, boolean available, Shelf shelf) {
        this.bookId = bookId;
        this.name = name;
        this.nameUa = nameUa;
        this.authors = authors;
        this.tags = tags;
        this.available = available;
        this.shelf = shelf;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return available == book.available &&
                bookId.equals(book.bookId) &&
                name.equals(book.name) &&
                nameUa.equals(book.nameUa) &&
                authors.equals(book.authors) &&
                tags.equals(book.tags);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookId, name, nameUa, authors, tags, available);
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameUa() {
        return nameUa;
    }

    public void setNameUa(String nameUa) {
        this.nameUa = nameUa;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public Shelf getShelf() {
        return shelf;
    }

    public void setShelf(Shelf shelf) {
        this.shelf = shelf;
    }
}
