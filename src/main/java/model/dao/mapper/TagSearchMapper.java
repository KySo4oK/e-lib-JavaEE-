package model.dao.mapper;

import model.entity.TagSearch;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class TagSearchMapper implements ObjectMapper<TagSearch> {
    @Override
    public TagSearch extractFromResultSet(ResultSet rs) throws SQLException {
        return null;
    }

    @Override
    public TagSearch makeUnique(Map<Long, TagSearch> cache, TagSearch teacher) {
        return null;
    }
}
