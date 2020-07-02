package controller.command.impl;

import controller.command.Command;
import controller.util.Validator;
import model.dto.UserDTO;
import model.entity.User;
import model.exception.CustomException;
import model.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class LoginCommand implements Command {
    private final UserService userService;
    private final static Logger log = LogManager.getLogger(LoginCommand.class);

    public LoginCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        String name = request.getParameter("username");
        String pass = request.getParameter("password");
        if (request.getParameter("username") == null) return "/login.jsp";
        try {
            Validator.checkLogin(UserDTO.Builder.anUserDTO()
                    .username(name)
                    .password(pass)
                    .build());
        } catch (CustomException e) {
            return "/login.jsp";
        }
        User.ROLE role;
        try {
            role = userService.getRoleByUser(request.getParameter("username"),
                    request.getParameter("password"));
        } catch (RuntimeException e) {
            return "/login.jsp";
        }

        if (CommandUtility.checkUserIsLogged(request, name)) {
            log.info("user with this username logged");
            return "redirect:/login";
        }
        CommandUtility.setUserRole(request, role, name);
        return role.equals(User.ROLE.ADMIN) ? "redirect:/admin/active" : "redirect:/user/active";
    }
}