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

public class GetPassiveOrdersCommand implements Command {
    private final OrderService orderService;
    private static final Log log = LogFactory.getLog(GetPassiveOrdersCommand.class);

    public GetPassiveOrdersCommand(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        try {
            return getJsonOfPassiveOrders((Locale) request.getSession().getAttribute("language"));
        } catch (JsonProcessingException e) {
            throw new OrderNotFoundException("order not found"); //todo
        }
    }

    private String getJsonOfPassiveOrders(Locale locale) throws JsonProcessingException {
        log.info("request locale - " + locale);
        return new ObjectMapper().writeValueAsString(orderService.getPassiveOrders(locale));
    }
}
