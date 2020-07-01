package model.service;

import controller.util.SHA1PasswordEncoder;
import model.dao.DaoFactory;

public class ServiceFactoryImpl extends ServiceFactory {
    @Override
    public BookService createBookService() {
        return new BookService(DaoFactory.getInstance(), createTagService(), createAuthorService());
    }

    @Override
    public UserService createUserService() {
        return new UserService(DaoFactory.getInstance(), new SHA1PasswordEncoder());
    }

    @Override
    public OrderService createOrderService() {
        return new OrderService(DaoFactory.getInstance());
    }

    @Override
    public TagService createTagService() {
        return new TagService(DaoFactory.getInstance());
    }

    @Override
    public AuthorService createAuthorService() {
        return new AuthorService(DaoFactory.getInstance());
    }
}
