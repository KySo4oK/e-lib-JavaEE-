package model.dto;

import java.util.Arrays;

public class FilterDTO {
    private String name;
    private String[] tags;

    public String[] getTags() {
        return tags;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }

    private String[] authors;

    @Override
    public String toString() {
        return "FilterDTO{" +
                "name='" + name + '\'' +
                ", tags='" + Arrays.toString(tags) + '\'' +
                ", authors=" + Arrays.toString(authors) +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String[] getAuthors() {
        return authors;
    }

    public void setAuthors(String[] authors) {
        this.authors = authors;
    }

    public static final class Builder {
        private String name;
        private String[] tags;
        private String[] authors;

        private Builder() {
        }

        public static Builder aFilterDTO() {
            return new Builder();
        }

        public Builder name(String name) {
            this.name = name;
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

        public FilterDTO build() {
            FilterDTO filterDTO = new FilterDTO();
            filterDTO.setName(name);
            filterDTO.setTags(tags);
            filterDTO.setAuthors(authors);
            return filterDTO;
        }
    }
}
