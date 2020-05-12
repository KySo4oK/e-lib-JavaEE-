package controller.command.impl;

import controller.command.Command;
import model.dto.UserDTO;
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
        userService.saveUser(new User(UserDTO.Builder.anUserDTO()
                .email(request.getParameter("email"))
                .password(request.getParameter("password"))
                .phone(request.getParameter("phone"))
                .username(request.getParameter("username"))
                .build()));
        return "redirect:/login";
    }
}
