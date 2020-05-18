package controller;

import controller.command.Command;
import controller.command.impl.*;
import controller.command.impl.admin.*;
import controller.command.impl.user.*;
import model.dao.*;
import model.service.*;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class Servlet extends javax.servlet.http.HttpServlet { //todo change collections for safe
    private Map<String, Command> commands = new HashMap<>();
    private static final org.apache.logging.log4j.Logger log
            = org.apache.logging.log4j.LogManager.getLogger(Servlet.class);
    private static final String REDIRECT = "redirect:";

    public void init(ServletConfig servletConfig) {

        servletConfig.getServletContext()
                .setAttribute("loggedUsers", new HashSet<String>());

        DaoFactory daoFactory = DaoFactory.getInstance();
        UserDao userDao = daoFactory.createUserDao();
        TagDao tagDao = daoFactory.createTagDao();
        OrderDao orderDao = daoFactory.createOrderDao();
        ShelfDao shelfDao = daoFactory.createShelfDao();
        AuthorDao authorDao = daoFactory.createAuthorDao();
        BookDao bookDao = daoFactory.createBookDao();//todo use smt better
        BookService bookService =
                new BookService(bookDao, shelfDao, new TagService(tagDao), new AuthorService(authorDao));
        UserService userService = new UserService();
        OrderService orderService = new OrderService(orderDao, bookDao, shelfDao, userDao);
        TagService tagService = new TagService(tagDao);
        AuthorService authorService = new AuthorService(authorDao);

        commands.put("logout", new LogOutCommand());
        commands.put("login", new LoginCommand(userService));
        commands.put("exception", new ExceptionCommand());
        commands.put("registration", new RegistrationCommand(userService));
        commands.put("user", new UserCommand());
        commands.put("error", new ErrorCommand());
        commands.put("admin", new AdminCommand());
        commands.put("index", new IndexCommand());
        commands.put("user/prospectus", new ProspectusCommand());
        commands.put("admin/bookManage", new BookManageCommand());
        commands.put("admin/add", new AddBookCommand(bookService));
        commands.put("admin/edit", new EditBookCommand(bookService));
        commands.put("admin/delete/{id}", new DeleteBookCommand(bookService));//todo change key
        commands.put("admin/active", new GetActiveOrdersCommand(orderService));
        commands.put("admin/passive", new GetPassiveOrdersCommand(orderService));
        commands.put("admin/permit", new PermitOrderCommand(orderService));
        commands.put("user/active", new GetActiveOrdersByUsernameCommand(orderService));
        commands.put("user/passive", new GetPassiveOrdersByUsernameCommand(orderService));
        commands.put("user/return", new ReturnBookCommand(orderService));
        commands.put("user/order", new OrderBookCommand(orderService));
        commands.put("user-admin/filter/{page}/{number}", new GetAvailableBooksByFilterCommand(bookService));//todo
        commands.put("user-admin/books/{page}/{number}", new GetAvailableBooksCommand(bookService));//todo
        commands.put("user-admin/tags", new GetTagsCommand(tagService));
        commands.put("user-admin/authors", new GetAuthorsCommand(authorService));
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws javax.servlet.ServletException, IOException {
        processRequest(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws javax.servlet.ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String path = request.getRequestURI();
        System.out.println(path);
        log.info("path " + path);
        path = path.replaceFirst("/", "");
        System.out.println(path);
        Command command = commands.getOrDefault(path,
                (r) -> "index");
        String page = command.execute(request);
        if (page.contains(REDIRECT)) {
            response.sendRedirect(/*request.getContextPath() + */page.replace(REDIRECT, ""));
        } else if (page.contains("{")) {
            response.getWriter().print(page);
        } else {
            request.getRequestDispatcher(page).forward(request, response);
        }
    }
}
