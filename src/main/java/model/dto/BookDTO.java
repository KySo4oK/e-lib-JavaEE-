package model.dto;

import java.util.Arrays;

public class BookDTO {
    private Long id;
    private String name;
    private String nameUa;
    private String[] tags;
    private String[] authors;

    public BookDTO() {
    }

    public BookDTO(Long id, String name, String nameUa, String[] tags, String[] authors) {
        this.id = id;
        this.name = name;
        this.nameUa = nameUa;
        this.tags = tags;
        this.authors = authors;
    }

    @Override
    public String toString() {
        return "BookDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", nameUa='" + nameUa + '\'' +
                ", tags=" + Arrays.toString(tags) +
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

    public String[] getTags() {
        return tags;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }

    public String[] getAuthors() {
        return authors;
    }

    public void setAuthors(String[] authors) {
        this.authors = authors;
    }

    public static final class Builder {
        private Long id;
        private String name;
        private String nameUa;
        private String[] tags;
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

        public Builder tags(String[] tags) {
            this.tags = tags;
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
            bookDTO.setTags(tags);
            bookDTO.setAuthors(authors);
            return bookDTO;
        }
    }
}
