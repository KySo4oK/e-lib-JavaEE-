package model.dao.mapper.impl;

import model.dao.mapper.ObjectMapper;
import model.entity.TagSearch;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class TagSearchMapper implements ObjectMapper<TagSearch> {
    @Override
    public TagSearch extractFromResultSet(ResultSet rs) throws SQLException {
        TagSearch tagSearch = new TagSearch();
        tagSearch.setSearchDate(rs.getDate("search_date").toLocalDate());
        tagSearch.setTagSearchId(rs.getLong("tag_search_id"));
        return tagSearch;
    }

    @Override
    public TagSearch makeUnique(Map<Long, TagSearch> cache, TagSearch tagSearch) {
        cache.putIfAbsent(tagSearch.getTagSearchId(), tagSearch);
        return cache.get(tagSearch.getTagSearchId());
    }
}
