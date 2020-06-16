package model.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TagSearch {
    private Long tagSearchId;
    private List<Tag> tags = new ArrayList<>();
    private User user;
    private LocalDate searchDate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TagSearch tagSearch = (TagSearch) o;
        return Objects.equals(tagSearchId, tagSearch.tagSearchId) &&
                tags.equals(tagSearch.tags) &&
                user.equals(tagSearch.user) &&
                Objects.equals(searchDate, tagSearch.searchDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tagSearchId, tags, user, searchDate);
    }

    @Override
    public String toString() {
        return "TagSearch{" +
                "tagSearchId=" + tagSearchId +
                ", tags=" + tags +
                ", user=" + user +
                ", searchDate=" + searchDate +
                '}';
    }

    public LocalDate getSearchDate() {
        return searchDate;
    }

    public void setSearchDate(LocalDate searchDate) {
        this.searchDate = searchDate;
    }

    public Long getTagSearchId() {
        return tagSearchId;
    }

    public void setTagSearchId(Long tagSearchId) {
        this.tagSearchId = tagSearchId;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public static final class Builder {
        private Long tagSearchId;
        private List<Tag> tags = new ArrayList<>();
        private User user;
        private LocalDate searchDate;

        private Builder() {
        }

        public static Builder aTagSearch() {
            return new Builder();
        }

        public Builder tagSearchId(Long tagSearchId) {
            this.tagSearchId = tagSearchId;
            return this;
        }

        public Builder tags(List<Tag> tags) {
            this.tags = tags;
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

        public Builder but() {
            return aTagSearch().tagSearchId(tagSearchId).tags(tags).user(user).searchDate(searchDate);
        }

        public TagSearch build() {
            TagSearch tagSearch = new TagSearch();
            tagSearch.setTagSearchId(tagSearchId);
            tagSearch.setTags(tags);
            tagSearch.setUser(user);
            tagSearch.setSearchDate(searchDate);
            return tagSearch;
        }
    }
}
