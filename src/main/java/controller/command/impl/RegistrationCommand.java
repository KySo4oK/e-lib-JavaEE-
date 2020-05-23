package controller.command.impl;

import controller.command.Command;
import model.dto.UserDTO;
import model.entity.User;
import model.service.UserService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.http.HttpServletRequest;

public class RegistrationCommand implements Command {
    private final UserService userService;
    private static final Log log = LogFactory.getLog(RegistrationCommand.class);

    public RegistrationCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        log.info("trying process registration for " + username + " with " + password);
        if (username == null && password == null) {
            return "/reg.jsp";
        }
        userService.saveUser(new User(UserDTO.Builder.anUserDTO()
                .email(request.getParameter("email"))
                .password(password)
                .phone(request.getParameter("phone"))
                .username(username)
                .build()));
        return "redirect:/login";
    }
}
