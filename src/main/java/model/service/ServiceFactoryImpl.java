package model.service;

import controller.util.SHA1PasswordEncoder;

public class ServiceFactoryImpl extends ServiceFactory {
    @Override
    public BookService createBookService() {
        return new BookService(createTagService(), createAuthorService());
    }

    @Override
    public UserService createUserService() {
        return new UserService(new SHA1PasswordEncoder());
    }

    @Override
    public OrderService createOrderService() {
        return new OrderService();
    }

    @Override
    public TagService createTagService() {
        return new TagService();
    }

    @Override
    public AuthorService createAuthorService() {
        return new AuthorService();
    }
}
