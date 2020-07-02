package controller.command.impl.admin;

import controller.command.Command;
import controller.util.LocaleExtractor;
import model.service.OrderService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class AdminActiveOrdersCommand implements Command {
    private final OrderService orderService;
    private final static Logger log = LogManager.getLogger(AdminActiveOrdersCommand.class);

    public AdminActiveOrdersCommand(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        request.setAttribute("orders",
                orderService.getActiveOrders(LocaleExtractor.extractFromRequest(request)));
        return "/WEB-INF/admin/admin-active.jsp";
    }
}