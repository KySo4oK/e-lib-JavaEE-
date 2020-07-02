package model.dao.mapper.impl;

import model.dao.mapper.ObjectMapper;
import model.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class UserMapper implements ObjectMapper<User> {

    public User extractWithoutRelationsFromResultSet(ResultSet rs) throws SQLException {
        return User.Builder.anUser()
                .id(rs.getLong("userId"))
                .username(rs.getString("username"))
                .email(rs.getString("email"))
                .phone(rs.getString("phone"))
                .password(rs.getString("password"))
                .role(User.ROLE.valueOf(rs.getString("role")))
                .build();
    }

    public User makeUnique(Map<Long, User> cache,
                           User user) {
        cache.putIfAbsent(user.getId(), user);
        return cache.get(user.getId());
    }
}
