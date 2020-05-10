package model.dao;

import model.entity.Author;

public interface AuthorDao extends GenericDao<Author> {
    String SQL_FIND_ALL = "select * from author";
    String SQL_FIND_BY_ID = SQL_FIND_ALL + " where author_id = ?";
    String SQL_FIND_BY_NAME = SQL_FIND_ALL + " where name = ?";
    String SQL_FIND_BY_NAME_UA = SQL_FIND_ALL + " where name_ua = ?";
    String SQL_INSERT = "insert into author (author_id, name, name_ua) values (?, ?, ?)";
    String SQL_UPDATE = "update author set name where author_id = ?";
    String SQL_DELETE = "delete from author where author_id = ?";

    Author findByName(String name);

    Author findByNameUa(String nameUa);
}
