package model.entity;

public class Tag {
    private Long tagId;
    private String name;
    private String nameUa;

    public Long getTagId() {
        return tagId;
    }

    public void setTagId(Long tagId) {
        this.tagId = tagId;
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

    public Tag(String name) {
        this.name = name;
    }

    public Tag() {
    }

    @Override
    public String toString() {
        return "Tag{" +
                "tagId=" + tagId +
                ", name='" + name + '\'' +
                ", nameUa='" + nameUa + '\'' +
                '}';
    }

    public Tag(Long tagId, String name, String nameUa) {
        this.tagId = tagId;
        this.name = name;
        this.nameUa = nameUa;
    }
}
