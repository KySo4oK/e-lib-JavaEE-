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

public class RegistrationCommand implements Command {
    private final UserService userService;
    private final static Logger log = LogManager.getLogger(RegistrationCommand.class);

    public RegistrationCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        if (username == null) return "/reg.jsp";
        log.info("trying process registration for " + username + " with " + password);
        UserDTO userDTO = UserDTO.Builder.anUserDTO()
                .email(request.getParameter("email"))
                .password(password)
                .phone(request.getParameter("phone"))
                .username(username)
                .build();
        try {
            Validator.checkRegistration(userDTO);
        } catch (CustomException e) {
            return "/reg.jsp";
        }
        userService.saveUser(new User(userDTO));
        return "redirect:/login";
    }
}