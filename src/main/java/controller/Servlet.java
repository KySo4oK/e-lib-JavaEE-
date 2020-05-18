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
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.stream.Collectors;

public class Servlet extends javax.servlet.http.HttpServlet { //todo change collections for safe
    private Map<String, Command> commands = new HashMap<>();
    private static final org.apache.logging.log4j.Logger log
            = org.apache.logging.log4j.LogManager.getLogger(Servlet.class);
    private static final String REDIRECT = "redirect:";

    public void init(ServletConfig servletConfig) {

        servletConfig.getServletContext()
                .setAttribute("loggedUsers", new HashSet<String>());
        BookService bookService =
                new BookService(new TagService(), new AuthorService());
        UserService userService = new UserService();
        OrderService orderService = new OrderService();
        TagService tagService = new TagService();
        AuthorService authorService = new AuthorService();

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
        commands.put("admin/delete", new DeleteBookCommand(bookService));
        commands.put("admin/active", new GetActiveOrdersCommand(orderService));
        commands.put("admin/passive", new GetPassiveOrdersCommand(orderService));
        commands.put("admin/permit", new PermitOrderCommand(orderService));
        commands.put("user/active", new GetActiveOrdersByUsernameCommand(orderService));
        commands.put("user/passive", new GetPassiveOrdersByUsernameCommand(orderService));
        commands.put("user/return", new ReturnBookCommand(orderService));
        commands.put("user/order", new OrderBookCommand(orderService));
        commands.put("user-admin/filter", new GetAvailableBooksByFilterCommand(bookService));
        commands.put("user-admin/books", new GetAvailableBooksCommand(bookService));
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
        log.info("path " + path);
        path = getPathForChoosingCommand(path);
        System.out.println(path);
        Command command = commands.getOrDefault(path,
                (r) -> "index");
        String page = command.execute(request);
        if (page.contains(REDIRECT)) {
            response.sendRedirect(/*request.getContextPath() + */page.replace(REDIRECT, ""));
        } else if (page.contains("{") || page.contains("[")) {
            response.getWriter().print(page);
        } else {
            request.getRequestDispatcher(page).forward(request, response);
        }
    }

    private String getPathForChoosingCommand(String path) {
        return Arrays.stream(path.replaceFirst("/", "").split("/"))
                .limit(2).collect(Collectors.joining("/"));
    }
}
