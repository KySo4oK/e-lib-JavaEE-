package controller.command.impl.admin;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import controller.command.Command;
import model.exception.OrderNotFoundException;
import model.service.OrderService;

import javax.servlet.http.HttpServletRequest;

public class GetActiveOrdersCommand implements Command {
    private final OrderService orderService;

    public GetActiveOrdersCommand(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        try {
            return getJsonOfActiveOrders();
        } catch (JsonProcessingException e) {
            throw new OrderNotFoundException("order not found"); //todo
        }
    }

    private String getJsonOfActiveOrders() throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(orderService.getActiveOrders());
    }
}