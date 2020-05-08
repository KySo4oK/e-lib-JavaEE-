package controller.command.impl;

import controller.command.Command;

import javax.servlet.http.HttpServletRequest;

public class ErrorCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        return "/WEB-INF/error.jsp";
    }
}
