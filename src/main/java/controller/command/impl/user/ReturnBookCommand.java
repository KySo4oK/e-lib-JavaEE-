package controller.command.impl.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import controller.command.Command;
import model.dto.OrderDTO;
import model.exception.OrderNotFoundException;
import model.service.OrderService;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class ReturnBookCommand implements Command {
    private final OrderService orderService;

    public ReturnBookCommand(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        try {
            orderService.returnBook(getOrderDTOFromRequest(request));
        } catch (IOException e) {
            throw new OrderNotFoundException("order not found");//todo use more specific exc
        }
        return "{}";
    }

    private OrderDTO getOrderDTOFromRequest(HttpServletRequest request) throws IOException {
        return new ObjectMapper().readValue(request.getReader(), OrderDTO.class);
    }
}
