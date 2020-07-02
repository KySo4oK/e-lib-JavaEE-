package controller.command.impl.admin;

import controller.command.Command;

import javax.servlet.http.HttpServletRequest;

public class AdminPassiveOrdersCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        return "/WEB-INF/admin/admin-passive.jsp";
    }
}
