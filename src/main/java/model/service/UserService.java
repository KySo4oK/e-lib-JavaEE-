package model.service;

import controller.util.PasswordEncoder;
import model.dao.DaoFactory;
import model.dao.UserDao;
import model.entity.User;
import model.exception.CustomException;

public class UserService {
    private final DaoFactory daoFactory;
    private final PasswordEncoder encoder;

    public UserService(DaoFactory daoFactory, PasswordEncoder encoder) {
        this.daoFactory = daoFactory;
        this.encoder = encoder;
    }

    public void saveUser(User user) {
        try (UserDao dao = daoFactory.createUserDao()) {
            user.setPassword(encoder.encode(user.getPassword()));
            dao.create(user);
        }
    }

    public User.ROLE getRoleByUser(String username, String password) {
        try (UserDao dao = daoFactory.createUserDao()) {
            User user = dao.findByUsername(username)
                    .orElseThrow(() -> new CustomException("user.already.exist"));
            if (user.getPassword().equals(encoder.encode(password))) {
                return user.getRole();
            }
        }
        throw new RuntimeException();
    }
}