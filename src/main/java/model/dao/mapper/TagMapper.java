package model.dao.mapper;

import model.entity.Tag;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class TagMapper implements ObjectMapper<Tag>{
    @Override
    public Tag extractFromResultSet(ResultSet rs) throws SQLException {
        return null;
    }

    @Override
    public Tag makeUnique(Map<Integer, Tag> cache, Tag teacher) {
        return null;
    }
}
