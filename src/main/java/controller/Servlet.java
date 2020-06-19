package controller;

import controller.command.Command;
import controller.command.impl.*;
import controller.command.impl.admin.*;
import controller.command.impl.user.*;
import model.service.ServiceFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class Servlet extends javax.servlet.http.HttpServlet {
    private final Map<String, Command> commands = new ConcurrentHashMap<>();
    private final static Logger log = LogManager.getLogger(Servlet.class);
    private static final String REDIRECT = "redirect:";

    public void init(ServletConfig servletConfig) {
        servletConfig.getServletContext()
                .setAttribute("loggedUsers", new HashSet<String>());
        putAllCommands();
    }

    private void putAllCommands() {
        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        commands.put("logout", new LogOutCommand());
        commands.put("login", new LoginCommand(serviceFactory.createUserService()));
        commands.put("exception", new ExceptionCommand());
        commands.put("registration", new RegistrationCommand(serviceFactory.createUserService()));
        commands.put("user", new UserCommand());
        commands.put("error", new ErrorCommand());
        commands.put("admin", new AdminCommand());
        commands.put("index", new IndexCommand());
        commands.put("user/prospectus", new ProspectusCommand());
        commands.put("admin/bookManage", new BookManageCommand());
        commands.put("admin/add", new AddBookCommand(serviceFactory.createBookService()));
        commands.put("admin/edit", new EditBookCommand(serviceFactory.createBookService()));
        commands.put("admin/delete", new DeleteBookCommand(serviceFactory.createBookService()));
        commands.put("admin/active", new GetActiveOrdersCommand(serviceFactory.createOrderService()));
        commands.put("admin/passive", new GetPassiveOrdersCommand(serviceFactory.createOrderService()));
        commands.put("admin/permit", new PermitOrderCommand(serviceFactory.createOrderService()));
        commands.put("user/active", new GetActiveOrdersByUsernameCommand(serviceFactory.createOrderService()));
        commands.put("user/passive", new GetPassiveOrdersByUsernameCommand(serviceFactory.createOrderService()));
        commands.put("user/return", new ReturnBookCommand(serviceFactory.createOrderService()));
        commands.put("user/order", new OrderBookCommand(serviceFactory.createOrderService()));
        commands.put("user/filter", new GetAvailableBooksByFilterCommand(serviceFactory.createBookService()));
        commands.put("user/books", new GetAvailableBooksCommand(serviceFactory.createBookService()));
        commands.put("admin/filter", new GetAvailableFullBooksByFilterCommand(serviceFactory.createBookService()));
        commands.put("admin/books", new GetAvailableFullBooksCommand(serviceFactory.createBookService()));
        commands.put("user-admin/tags", new GetTagsCommand(serviceFactory.createTagService()));
        commands.put("user-admin/authors", new GetAuthorsCommand(serviceFactory.createAuthorService()));
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws javax.servlet.ServletException, IOException {
        processRequest(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws javax.servlet.ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String path = request.getRequestURI();
        log.info("request path - " + path);
        path = getPathForChoosingCommand(path);
        Command command = commands.getOrDefault(path,
                (r) -> "index");
        String result = command.execute(request);
        if (containsJson(result)) {
            response.getWriter().print(result);
        } else if (result.contains(REDIRECT)) {
            response.sendRedirect(result.replace(REDIRECT, ""));
        } else {
            request.getRequestDispatcher(result).forward(request, response);
        }
    }

    private boolean containsJson(String page) {
        return page.contains("{") || page.contains("[");
    }

    private String getPathForChoosingCommand(String path) {
        return Arrays.stream(path.replaceFirst("/", "").split("/"))
                .limit(2).collect(Collectors.joining("/"));
    }
}
