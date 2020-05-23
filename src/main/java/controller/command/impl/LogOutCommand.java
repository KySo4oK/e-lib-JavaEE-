package controller.command.impl;


import controller.command.Command;
import model.entity.User;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashSet;

public class LogOutCommand implements Command {
    private static final Log log = LogFactory.getLog(LogOutCommand.class);

    @Override
    public String execute(HttpServletRequest request) {
        CommandUtility.setUserRole(request, User.ROLE.UNKNOWN, "Guest");
        HttpSession session = request.getSession();
        ServletContext context = session.getServletContext();
        HashSet<String> loggedUsers = (HashSet<String>) context.getAttribute("loggedUsers");
        String username = (String) session.getAttribute("username");
        loggedUsers.remove(username);
        context.setAttribute("loggedUsers", loggedUsers);
        log.info("logout - " + username);
        return "redirect:/index";
    }
}
