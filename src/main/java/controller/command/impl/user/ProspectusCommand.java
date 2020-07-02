package controller.command.impl.user;

import controller.command.Command;

import javax.servlet.http.HttpServletRequest;

public class ProspectusCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        return "/WEB-INF/user/prospectus.jsp";
    }
}