package model.dao.mapper.impl;

import model.dao.mapper.ObjectMapper;
import model.entity.Tag;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class TagMapper implements ObjectMapper<Tag> {
    @Override
    public Tag extractFromResultSet(ResultSet rs) throws SQLException {
        Tag tag = new Tag();
        tag.setTagId(rs.getLong("tagId"));
        tag.setName(rs.getString("tagName"));
        tag.setNameUa(rs.getString("tagNameUa"));
        return tag;
    }

    @Override
    public Tag makeUnique(Map<Long, Tag> cache, Tag tag) {
        cache.putIfAbsent(tag.getTagId(), tag);
        return cache.get(tag.getTagId());
    }
}
