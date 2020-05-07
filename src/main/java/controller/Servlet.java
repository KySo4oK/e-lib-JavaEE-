package controller;

import controller.command.*;

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

        commands.put("logout",
                new LogOutCommand());
        commands.put("login",
                new LoginCommand());
        commands.put("exception", new ExceptionCommand());
        commands.put("registration", new RegistrationCommand());
        commands.put("user", new UserCommand());
        commands.put("error", new ErrorCommand());
        commands.put("admin", new AdminCommand());
        commands.put("index", new IndexCommand());
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
        } else {
            request.getRequestDispatcher(page).forward(request, response);
        }
    }
}
