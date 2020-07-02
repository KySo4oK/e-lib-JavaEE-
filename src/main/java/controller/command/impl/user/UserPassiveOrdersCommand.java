package controller.command.impl.user;

import controller.command.Command;

import javax.servlet.http.HttpServletRequest;

public class UserPassiveOrdersCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        return "/WEB-INF/user/user-passive.jsp";
    }
}
