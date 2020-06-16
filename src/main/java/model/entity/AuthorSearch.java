package model.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AuthorSearch {
    private Long authorSearchId;
    private List<Author> authors = new ArrayList<>();
    private User user;
    private LocalDate searchDate;

    @Override
    public String toString() {
        return "AuthorSearch{" +
                "authorSearchId=" + authorSearchId +
                ", authors=" + authors +
                ", user=" + user +
                ", searchDate=" + searchDate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthorSearch that = (AuthorSearch) o;
        return Objects.equals(authorSearchId, that.authorSearchId) &&
                authors.equals(that.authors) &&
                user.equals(that.user) &&
                Objects.equals(searchDate, that.searchDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(authorSearchId, authors, user, searchDate);
    }

    public Long getAuthorSearchId() {
        return authorSearchId;
    }

    public void setAuthorSearchId(Long authorSearchId) {
        this.authorSearchId = authorSearchId;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDate getSearchDate() {
        return searchDate;
    }

    public void setSearchDate(LocalDate searchDate) {
        this.searchDate = searchDate;
    }

    public static final class Builder {
        private Long authorSearchId;
        private List<Author> authors = new ArrayList<>();
        private User user;
        private LocalDate searchDate;

        private Builder() {
        }

        public static Builder anAuthorSearch() {
            return new Builder();
        }

        public Builder authorSearchId(Long authorSearchId) {
            this.authorSearchId = authorSearchId;
            return this;
        }

        public Builder authors(List<Author> authors) {
            this.authors = authors;
            return this;
        }

        public Builder user(User user) {
            this.user = user;
            return this;
        }

        public Builder searchDate(LocalDate searchDate) {
            this.searchDate = searchDate;
            return this;
        }

        public AuthorSearch build() {
            AuthorSearch authorSearch = new AuthorSearch();
            authorSearch.setAuthorSearchId(authorSearchId);
            authorSearch.setAuthors(authors);
            authorSearch.setUser(user);
            authorSearch.setSearchDate(searchDate);
            return authorSearch;
        }
    }
}