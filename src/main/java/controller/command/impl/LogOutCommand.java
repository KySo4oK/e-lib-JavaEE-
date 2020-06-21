package controller.command.impl;


import controller.command.Command;
import model.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashSet;

public class LogOutCommand implements Command {
    private final static Logger log = LogManager.getLogger(LogOutCommand.class);

    @Override
    public String execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        ServletContext context = session.getServletContext();
        String userName = (String) session.getAttribute("username");
        CommandUtility.setUserRole(request, User.ROLE.UNKNOWN, "Guest");
        HashSet<String> loggedUsers = (HashSet<String>) context.getAttribute("loggedUsers");
        log.info("logged users - " + loggedUsers);
        loggedUsers.remove(userName);
        session.setAttribute("loggedUsers", loggedUsers);
        context.setAttribute("loggedUsers", loggedUsers);
        log.info("remove from logged users - " + userName);
        return "redirect:/index";
    }
}
