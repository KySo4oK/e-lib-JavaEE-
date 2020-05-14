package model.dao.impl;

import model.dao.TagDao;
import model.dao.mapper.impl.TagMapper;
import model.entity.Tag;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JDBCTagDao implements TagDao {
    private final Connection connection;
    private final TagMapper tagMapper = new TagMapper();

    public JDBCTagDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Tag findByName(String name) {
        return findTagByName(name, SQL_FIND_BY_NAME);
    }

    private Tag findTagByName(String name, String sqlFindByName) {
        Tag tag = null;
        try {
            PreparedStatement statement = connection.prepareStatement(sqlFindByName);
            statement.setString(1, name);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                tag = tagMapper.extractFromResultSet(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tag;
    }

    @Override
    public Tag findByNameUa(String nameUa) {
        return findTagByName(nameUa, SQL_FIND_BY_NAME_UA);
    }

    @Override
    public void create(Tag entity) {
        try {
            PreparedStatement statement = connection.prepareStatement(SQL_INSERT);
            statement.setString(1, entity.getName());
            statement.setString(2, entity.getNameUa());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Tag findById(int id) {
        Tag tag = null;
        try {
            PreparedStatement statement = connection.prepareStatement(SQL_FIND_BY_ID);
            statement.setLong(1, id);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                tag = tagMapper
                        .extractFromResultSet(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tag;
    }

    @Override
    public List<Tag> findAll() {
        Map<Long, Tag> tags = new HashMap<>();
        try (Statement st = connection.createStatement()) {
            ResultSet rs = st.executeQuery(SQL_FIND_ALL);
            while (rs.next()) {
                Tag tag = tagMapper
                        .extractFromResultSet(rs);
                tag = tagMapper //useless now
                        .makeUnique(tags, tag);
            }
            return new ArrayList<>(tags.values());
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void update(Tag entity) {
        try {
            PreparedStatement statement = connection.prepareStatement(SQL_UPDATE);
            statement.setString(1, entity.getName());
            statement.setLong(2, entity.getTagId());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        try {
            PreparedStatement statement = connection.prepareStatement(SQL_DELETE);
            statement.setLong(1, id);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
