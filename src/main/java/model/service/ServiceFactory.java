package model.service;

public abstract class ServiceFactory {
    private static ServiceFactory serviceFactory;


    public static ServiceFactory getInstance() {
        if (serviceFactory == null) {
            synchronized (ServiceFactory.class) {
                if (serviceFactory == null) {
                    serviceFactory = new ServiceFactoryImpl();
                }
            }
        }
        return serviceFactory;
    }

    public abstract BookService createBookService();

    public abstract UserService createUserService();

    public abstract OrderService createOrderService();

    public abstract TagService createTagService();

    public abstract AuthorService createAuthorService();
}
