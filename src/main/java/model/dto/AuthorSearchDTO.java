package model.dto;

import java.util.Arrays;

public class AuthorSearchDTO {
    private String username;
    private String[] authors;
    private String searchDate;

    @Override
    public String toString() {
        return "AuthorSearchDTO{" +
                "username='" + username + '\'' +
                ", authors=" + Arrays.toString(authors) +
                ", searchDate='" + searchDate + '\'' +
                '}';
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String[] getAuthors() {
        return authors;
    }

    public void setAuthors(String[] authors) {
        this.authors = authors;
    }

    public String getSearchDate() {
        return searchDate;
    }

    public void setSearchDate(String searchDate) {
        this.searchDate = searchDate;
    }
}
