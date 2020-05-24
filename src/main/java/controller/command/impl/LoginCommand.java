package controller.command.impl;

import controller.command.Command;
import model.entity.User;
import model.service.UserService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.http.HttpServletRequest;

public class LoginCommand implements Command {
    private final UserService userService;
    private static final Log log = LogFactory.getLog(LoginCommand.class);

    public LoginCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        String name = request.getParameter("username");
        String pass = request.getParameter("password");
        log.info("trying login - " + name + " with - " + pass);
        if (request.getParameter("username") == null) {//todo validation
            return "/login.jsp";
        }
        User.ROLE role = (userService.getRoleByUser(request.getParameter("username"),
                request.getParameter("password")));
        log.info("role from db - " + role);

        if (role == User.ROLE.UNKNOWN) {
            return "/login.jsp";
        }

        if (CommandUtility.checkUserIsLogged(request, name)) {
            log.info("user with this username logged");
            return "redirect:/login";
        }

        if (role.equals(User.ROLE.ADMIN)) {
            CommandUtility.setUserRole(request, User.ROLE.ADMIN, name);
            return "redirect:/admin";
        } else {
            CommandUtility.setUserRole(request, User.ROLE.USER, name);
            return "redirect:/user";
        }
    }
}
