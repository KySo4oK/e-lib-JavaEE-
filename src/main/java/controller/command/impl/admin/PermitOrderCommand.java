package controller.command.impl.admin;

import com.fasterxml.jackson.databind.ObjectMapper;
import controller.command.Command;
import model.dto.OrderDTO;
import model.exception.OrderNotFoundException;
import model.service.OrderService;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class PermitOrderCommand implements Command {
    private final OrderService orderService;

    public PermitOrderCommand(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        try {
            orderService.permitOrder(getOrderDTOFromRequest(request));
        } catch (IOException e) {
            throw new OrderNotFoundException("order not found");//todo use more specific exc
        }
        return "{}";
    }

    private OrderDTO getOrderDTOFromRequest(HttpServletRequest request) throws IOException {
        return new ObjectMapper().readValue(request.getReader(), OrderDTO.class);
    }
}
