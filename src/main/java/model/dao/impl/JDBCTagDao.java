package model.dao.impl;

import model.dao.TagDao;
import model.dao.mapper.impl.TagMapper;
import model.entity.Tag;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.*;

public class JDBCTagDao implements TagDao {
    private final Connection connection;
    private final TagMapper tagMapper = new TagMapper();
    private final static Logger log = LogManager.getLogger(JDBCTagDao.class);

    public JDBCTagDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Optional<Tag> findByName(String name) {
        return findTagByName(name, SQL_FIND_BY_NAME);
    }

    private Optional<Tag> findTagByName(String name, String sqlFindByName) {
        Tag tag = null;
        try (PreparedStatement statement = connection.prepareStatement(sqlFindByName)) {
            statement.setString(1, name);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                tag = tagMapper.extractFromResultSet(rs);
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
        return Optional.ofNullable(tag);
    }

    @Override
    public Optional<Tag> findByNameUa(String nameUa) {
        return findTagByName(nameUa, SQL_FIND_BY_NAME_UA);
    }

    @Override
    public void create(Tag entity) {
        try (PreparedStatement statement = connection.prepareStatement(SQL_INSERT)) {
            statement.setString(1, entity.getName());
            statement.setString(2, entity.getNameUa());
            statement.execute();
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Tag> findById(long id) {
        Tag tag = null;
        try (PreparedStatement statement = connection.prepareStatement(SQL_FIND_BY_ID)) {
            statement.setLong(1, id);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                tag = tagMapper
                        .extractFromResultSet(rs);
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
        return Optional.ofNullable(tag);
    }

    @Override
    public List<Tag> findAll() {
        List<Tag> resultList = new ArrayList<>();
        try (Statement st = connection.createStatement()) {
            ResultSet rs = st.executeQuery(SQL_FIND_ALL);
            while (rs.next()) {
                resultList.add(tagMapper.extractFromResultSet(rs));
            }
            return resultList;
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Tag entity) {
        try (PreparedStatement statement = connection.prepareStatement(SQL_UPDATE)) {
            statement.setString(1, entity.getName());
            statement.setLong(2, entity.getTagId());
            statement.execute();
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(long id) {
        try (PreparedStatement statement = connection.prepareStatement(SQL_DELETE)) {
            statement.setLong(1, id);
            statement.execute();
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
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
