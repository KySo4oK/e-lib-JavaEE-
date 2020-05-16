package controller;

import controller.command.Command;
import controller.command.impl.*;
import controller.command.impl.admin.*;
import controller.command.impl.user.ProspectusCommand;
import controller.command.impl.user.UserCommand;
import model.dao.*;
import model.service.AuthorService;
import model.service.BookService;
import model.service.TagService;
import model.service.UserService;

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
        commands.put("add", new AddBookCommand(bookService));
        commands.put("edit", new EditBookCommand(bookService));
        commands.put("delete/{id}", new DeleteBookCommand(bookService));//todo change key
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
        path = path.replaceAll("/", "");
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
