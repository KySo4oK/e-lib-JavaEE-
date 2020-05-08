package model.entity;

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
}
