package controller.command;


import model.entity.User;

import javax.servlet.http.HttpServletRequest;

public class LogOutCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        // ToDo delete current user (context & session)
        System.out.println("in logout command");
        CommandUtility.setUserRole(request, User.ROLE.UNKNOWN, "Guest");
        return "redirect:/index";
    }
}
