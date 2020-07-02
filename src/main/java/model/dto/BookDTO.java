package model.dto;

import java.util.Arrays;
import java.util.Objects;

public class BookDTO {
    private Long id;
    private String name;
    private String nameUa;
    private String tag;
    private String[] authors;

    public BookDTO() {
    }

    public BookDTO(Long id, String name, String nameUa, String tag, String[] authors) {
        this.id = id;
        this.name = name;
        this.nameUa = nameUa;
        this.tag = tag;
        this.authors = authors;
    }

    @Override
    public String toString() {
        return "BookDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", nameUa='" + nameUa + '\'' +
                ", tag='" + tag + '\'' +
                ", authors=" + Arrays.toString(authors) +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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


    public String[] getAuthors() {
        return authors;
    }

    public void setAuthors(String[] authors) {
        this.authors = authors;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookDTO bookDTO = (BookDTO) o;
        return Objects.equals(id, bookDTO.id) &&
                name.equals(bookDTO.name) &&
                Objects.equals(nameUa, bookDTO.nameUa) &&
                tag.equals(bookDTO.tag) &&
                Arrays.equals(authors, bookDTO.authors);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, name, nameUa, tag);
        result = 31 * result + Arrays.hashCode(authors);
        return result;
    }

    public static final class Builder {
        private Long id;
        private String name;
        private String nameUa;
        private String tag;
        private String[] authors;

        private Builder() {
        }

        public static Builder aBookDTO() {
            return new Builder();
        }

        public Builder id(Long id) {
            this.id = id;
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

        public Builder tag(String tag) {
            this.tag = tag;
            return this;
        }

        public Builder authors(String[] authors) {
            this.authors = authors;
            return this;
        }

        public BookDTO build() {
            BookDTO bookDTO = new BookDTO();
            bookDTO.setId(id);
            bookDTO.setName(name);
            bookDTO.setNameUa(nameUa);
            bookDTO.setTag(tag);
            bookDTO.setAuthors(authors);
            return bookDTO;
        }
    }
}