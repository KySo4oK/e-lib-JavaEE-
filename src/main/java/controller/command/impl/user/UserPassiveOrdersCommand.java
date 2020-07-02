package controller.command.impl.user;

import controller.command.Command;
import controller.util.LocaleExtractor;
import model.service.OrderService;

import javax.servlet.http.HttpServletRequest;

public class UserPassiveOrdersCommand implements Command {
    private final OrderService orderService;

    public UserPassiveOrdersCommand(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        request.setAttribute("orders",
                orderService.getPassiveOrdersByUserName(
                        request.getSession().getAttribute("username").toString(),
                        LocaleExtractor.extractFromRequest(request)));
        return "/WEB-INF/user/user-passive.jsp";
    }
}