package model.service;

import model.dao.DaoFactory;
import model.dao.UserDao;
import model.entity.User;

import java.util.List;

public class UserService {
    private final DaoFactory daoFactory = DaoFactory.getInstance();

    public List<User> getAllUsers() {
        try (UserDao dao = daoFactory.createUserDao()) {
            return dao.findAll();
        }
    }

    public void saveUser(User user) {
        try (UserDao dao = daoFactory.createUserDao()) {
            dao.create(user);
        }
    }

    public User.ROLE getRoleByUser(String username, String password) {
        try (UserDao dao = daoFactory.createUserDao()) {
            User user = dao.findByUsername(username).orElseThrow(() -> new RuntimeException("oops"));
            if (user != null) {
                if (user.getPassword().equals(password)) {
                    return user.getRole();
                }
            }
        }
        return User.ROLE.UNKNOWN;
    }
}
