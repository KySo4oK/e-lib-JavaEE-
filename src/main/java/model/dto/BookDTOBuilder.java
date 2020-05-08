package model.dto;

public class BookDTOBuilder {
    private Long id;
    private String name;
    private String nameUa;
    private String[] tags;
    private String[] authors;

    public BookDTOBuilder id(Long id) {
        this.id = id;
        return this;
    }

    public BookDTOBuilder name(String name) {
        this.name = name;
        return this;
    }

    public BookDTOBuilder nameUa(String nameUa) {
        this.nameUa = nameUa;
        return this;
    }

    public BookDTOBuilder tags(String[] tags) {
        this.tags = tags;
        return this;
    }

    public BookDTOBuilder authors(String[] authors) {
        this.authors = authors;
        return this;
    }

    public BookDTO createBookDTO() {
        return new BookDTO(id, name, nameUa, tags, authors);
    }
}