package controller;

import controller.command.Command;
import controller.command.impl.*;
import controller.command.impl.admin.*;
import controller.command.impl.user.*;
import model.service.ServiceFactory;

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
    private final Map<String, Command> commands = new HashMap<>();
    private static final org.apache.logging.log4j.Logger log
            = org.apache.logging.log4j.LogManager.getLogger(Servlet.class);
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
        commands.put("user-admin/filter", new GetAvailableBooksByFilterCommand(serviceFactory.createBookService()));
        commands.put("user-admin/books", new GetAvailableBooksCommand(serviceFactory.createBookService()));
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
    protected void doPut(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
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
