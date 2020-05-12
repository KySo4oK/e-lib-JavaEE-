package model.dao;

import model.entity.User;

public interface UserDao extends GenericDao<User> {
    String SQL_FIND_ALL = "select user_id as \"userId\", email, password, phone, role, username from users";
    String SQL_FIND_BY_ID = SQL_FIND_ALL + " where user_id = ?";
    String SQL_FIND_BY_USERNAME = SQL_FIND_ALL + " where username = ?";
    String SQL_INSERT = "insert into users (username, password, role, email, phone) values (?, ?, ?, ?, ?)";
    String SQL_UPDATE = "update users set password where user_id = ?";
    String SQL_DELETE = "delete from users where user_id = ?";

    User findByUsername(String username);
}
