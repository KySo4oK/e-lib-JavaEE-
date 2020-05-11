package model.dao;

import model.entity.Tag;

public interface TagDao extends GenericDao<Tag> {
    String SQL_FIND_ALL = "select tag_id as \"tagId\", name as \"tagName\", name_ua as \"tagNameUa\" from tag";
    String SQL_FIND_BY_ID = SQL_FIND_ALL + " where tag_id = ?";
    String SQL_FIND_BY_NAME = SQL_FIND_ALL + " where name = ?";
    String SQL_FIND_BY_NAME_UA = SQL_FIND_ALL + " where name_ua = ?";
    String SQL_INSERT = "insert into tag (name, name_ua) values (?, ?)";
    String SQL_UPDATE = "update tag set name where tag_id = ?";
    String SQL_DELETE = "delete from tag where tag_id = ?";

    Tag findByName(String name);

    Tag findByNameUa(String nameUa);
}
