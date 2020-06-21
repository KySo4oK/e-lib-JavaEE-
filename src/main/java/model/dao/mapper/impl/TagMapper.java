package model.dao.mapper.impl;

import model.dao.mapper.ObjectMapper;
import model.entity.Tag;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class TagMapper implements ObjectMapper<Tag> {
    @Override
    public Tag extractFromResultSet(ResultSet rs) throws SQLException {
        return Tag.Builder.aTag()
                .tagId(rs.getLong("tagId"))
                .name(rs.getString("tagName"))
                .nameUa(rs.getString("tagNameUa"))
                .build();
    }

    @Override
    public Tag makeUnique(Map<Long, Tag> cache, Tag tag) {
        cache.putIfAbsent(tag.getTagId(), tag);
        return cache.get(tag.getTagId());
    }
}
