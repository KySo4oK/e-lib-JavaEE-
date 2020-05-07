package controller.command;

import javax.servlet.http.HttpServletRequest;

public class AdminCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        return "/WEB-INF/admin/admin.jsp";
    }
}
