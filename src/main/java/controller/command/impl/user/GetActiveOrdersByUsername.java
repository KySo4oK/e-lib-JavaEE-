package controller.command.impl.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import controller.command.Command;
import model.exception.OrderNotFoundException;
import model.service.OrderService;

import javax.servlet.http.HttpServletRequest;

public class GetActiveOrdersByUsername implements Command {
    private final OrderService orderService;

    public GetActiveOrdersByUsername(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        try {
            return getJsonOfActiveOrdersByUserName(request.getSession().getAttribute("username").toString());
        } catch (JsonProcessingException e) {
            throw new OrderNotFoundException("order not found"); //todo
        }
    }

    private String getJsonOfActiveOrdersByUserName(String username) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(orderService.getActiveOrdersByUserName(username));
    }
}
