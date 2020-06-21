package model.service;

import controller.util.PasswordEncoder;
import model.dao.DaoFactory;
import model.dao.UserDao;
import model.entity.User;

import java.util.List;

public class UserService {
    private final DaoFactory daoFactory = DaoFactory.getInstance();
    private final PasswordEncoder encoder;

    public UserService(PasswordEncoder encoder) {
        this.encoder = encoder;
    }

    public List<User> getAllUsers() {
        try (UserDao dao = daoFactory.createUserDao()) {
            return dao.findAll();
        }
    }

    public void saveUser(User user) {
        try (UserDao dao = daoFactory.createUserDao()) {
            user.setPassword(encoder.encode(user.getPassword()));
            dao.create(user);
        }
    }

    public User.ROLE getRoleByUser(String username, String password) {
        try (UserDao dao = daoFactory.createUserDao()) {
            User user = dao.findByUsername(username).orElseThrow(RuntimeException::new);
            if (user.getPassword().equals(encoder.encode(password))) {
                return user.getRole();
            }
        }
        throw new RuntimeException();
    }
}