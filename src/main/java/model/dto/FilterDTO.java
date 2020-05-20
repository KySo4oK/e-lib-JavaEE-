package model.dto;

import java.util.Arrays;

public class FilterDTO {
    private String name;
    private String tag;
    private String[] authors;

    @Override
    public String toString() {
        return "FilterDTO{" +
                "name='" + name + '\'' +
                ", tag='" + tag + '\'' +
                ", authors=" + Arrays.toString(authors) +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String[] getAuthors() {
        return authors;
    }

    public void setAuthors(String[] authors) {
        this.authors = authors;
    }
}
