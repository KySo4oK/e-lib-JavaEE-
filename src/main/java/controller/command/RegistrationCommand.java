package controller.command;

import model.entity.User;
import model.service.UserService;

import javax.servlet.http.HttpServletRequest;

public class RegistrationCommand implements Command {
    private UserService userService = new UserService();//todo fix to using one service

    @Override
    public String execute(HttpServletRequest request) {
        if (request.getParameter("username") == null &&
                request.getParameter("password") == null) {
            return "/reg.jsp";
        }
        userService.saveUser(new User(
                request.getParameter("username"),
                request.getParameter("password")));
        return "redirect:/login";
    }
}
