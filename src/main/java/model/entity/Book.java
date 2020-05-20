package model.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Book {
    private Long bookId;
    private String name;
    private String nameUa;
    private List<Author> authors = new ArrayList<>();
    private Tag tag;
    private boolean available = true;
    private Shelf shelf;

    public Book() {
    }

    public Book(Long bookId, String name, String nameUa, List<Author> authors, Tag tag, boolean available, Shelf shelf) {
        this.bookId = bookId;
        this.name = name;
        this.nameUa = nameUa;
        this.authors = authors;
        this.tag = tag;
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
                nameUa.equals(book.nameUa);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookId, name, nameUa, authors, tag, available);
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

    public Tag getTag() {
        return tag;
    }

    public void setTag(Tag tag) {
        this.tag = tag;
    }

    @Override
    public String toString() {
        return "Book{" +
                "bookId=" + bookId +
                ", name='" + name + '\'' +
                ", nameUa='" + nameUa + '\'' +
                ", authors=" + authors +
                ", tag=" + tag +
                ", available=" + available +
                ", shelf=" + shelf +
                '}';
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


    public static final class Builder {
        private Long bookId;
        private String name;
        private String nameUa;
        private List<Author> authors = new ArrayList<>();
        private Tag tag;
        private boolean available = true;
        private Shelf shelf;

        private Builder() {
        }

        public static Builder aBook() {
            return new Builder();
        }

        public Builder bookId(Long bookId) {
            this.bookId = bookId;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder nameUa(String nameUa) {
            this.nameUa = nameUa;
            return this;
        }

        public Builder authors(List<Author> authors) {
            this.authors = authors;
            return this;
        }

        public Builder tag(Tag tag) {
            this.tag = tag;
            return this;
        }

        public Builder available(boolean available) {
            this.available = available;
            return this;
        }

        public Builder shelf(Shelf shelf) {
            this.shelf = shelf;
            return this;
        }

        public Book build() {
            return new Book(bookId, name, nameUa, authors, tag, available, shelf);
        }
    }
}
