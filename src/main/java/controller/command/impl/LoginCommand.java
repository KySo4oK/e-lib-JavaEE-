package controller.command.impl;

import controller.command.Command;
import model.entity.User;
import model.service.UserService;

import javax.servlet.http.HttpServletRequest;

public class LoginCommand implements Command {
    private final UserService userService;
    private static final org.apache.logging.log4j.Logger log
            = org.apache.logging.log4j.LogManager.getLogger(LoginCommand.class);

    public LoginCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        System.out.println("logincom");
        String name = request.getParameter("username");
        String pass = request.getParameter("password");
        log.info(name + pass);

//        if (name == null || name.equals("") || pass == null || pass.equals("")) {
//            //System.out.println("Not");
//            return "/login.jsp";
//        }
//        System.out.println(name + " " + pass);
//        //System.out.println("Yes!");
        if (request.getParameter("username") == null) {
            return "/login.jsp";
        }
        User.ROLE role = (userService.getRoleByUser(request.getParameter("username"),
                request.getParameter("password")));
        System.out.println(role + "rolllleee");


        if (CommandUtility.checkUserIsLogged(request, name)) {
            return "redirect:/login";
        }

        if (role.equals(User.ROLE.ADMIN)) {
            CommandUtility.setUserRole(request, User.ROLE.ADMIN, name);
            return "redirect:/admin";
        } else if (role.equals(User.ROLE.USER)) {
            CommandUtility.setUserRole(request, User.ROLE.USER, name);
            return "redirect:/user";
        } else {
            CommandUtility.setUserRole(request, User.ROLE.UNKNOWN, name);
            return "/login.jsp";
        }
    }
}
