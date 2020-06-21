package model.entity;

import javax.persistence.Entity;
import java.util.Objects;

@Entity(name = "shelf")
public class Shelf {
    private Long shelfId;
    private Book book;

    public Shelf() {
    }

    public Shelf(Long shelf_id) {
        this.shelfId = shelf_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Shelf shelf = (Shelf) o;
        return shelfId.equals(shelf.shelfId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(shelfId);
    }

    @Override
    public String toString() {
        return "Shelf{" +
                "shelfId=" + shelfId +
                '}';
    }

    public Long getShelfId() {
        return shelfId;
    }

    public void setShelfId(Long shelfId) {
        this.shelfId = shelfId;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }
}
