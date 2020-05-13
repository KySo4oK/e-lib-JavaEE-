package model.entity;

import java.util.Objects;

public class Author {
    private Long authorId;
    private String name;
    private String nameUa;

    public Author(String name) {
        this.name = name;
    }

    public Author(Long authorId, String name, String nameUa) {
        this.authorId = authorId;
        this.name = name;
        this.nameUa = nameUa;
    }

    public Author() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Author author = (Author) o;
        return Objects.equals(authorId, author.authorId) &&
                name.equals(author.name) &&
                nameUa.equals(author.nameUa);
    }

    @Override
    public int hashCode() {
        return Objects.hash(authorId, name, nameUa);
    }

    @Override
    public String toString() {
        return "Author{" +
                "authorId=" + authorId +
                ", name='" + name + '\'' +
                ", nameUa='" + nameUa + '\'' +
                '}';
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
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

    public static final class Builder {
        private Long authorId;
        private String name;
        private String nameUa;

        private Builder() {
        }

        public static Builder anAuthor() {
            return new Builder();
        }

        public Builder authorId(Long authorId) {
            this.authorId = authorId;
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

        public Author build() {
            Author author = new Author();
            author.setAuthorId(authorId);
            author.setName(name);
            author.setNameUa(nameUa);
            return author;
        }
    }
}
