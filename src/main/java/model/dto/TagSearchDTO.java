package model.dto;

import java.util.Arrays;

public class TagSearchDTO {
    private String username;
    private String[] tags;
    private String searchDate;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String[] getTags() {
        return tags;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }

    public String getSearchDate() {
        return searchDate;
    }

    public void setSearchDate(String searchDate) {
        this.searchDate = searchDate;
    }

    @Override
    public String toString() {
        return "TagSearchDTO{" +
                "username='" + username + '\'' +
                ", tags=" + Arrays.toString(tags) +
                ", searchDate='" + searchDate + '\'' +
                '}';
    }
}
