package model.dao;

import model.entity.AuthorSearch;

public interface AuthorSearchDao extends GenericDao<AuthorSearch> {
    String SQL_DELETE = "delete from author_search where author_search_id = ?";
    String SQL_UPDATE = "update author_search set search_date = ? where author_search_id = ?";
    String SQL_INSERT = "insert into author_search (user_id, author_id) values (?, ?)";
}
