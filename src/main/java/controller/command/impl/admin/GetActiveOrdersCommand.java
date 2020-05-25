package controller.command.impl.admin;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import controller.command.Command;
import model.exception.OrderNotFoundException;
import model.service.OrderService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

public class GetActiveOrdersCommand implements Command {
    private final OrderService orderService;
    private static final Log log = LogFactory.getLog(GetActiveOrdersCommand.class);

    public GetActiveOrdersCommand(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        try {
            return getJsonOfActiveOrders((Locale) request.getSession().getAttribute("language"));
        } catch (JsonProcessingException e) {
            throw new OrderNotFoundException("order not found"); //todo
        }
    }

    private String getJsonOfActiveOrders(Locale locale) throws JsonProcessingException {
        log.info("request locale - " + locale);
        return new ObjectMapper().writeValueAsString(orderService.getActiveOrders(locale));
    }
}
