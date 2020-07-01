package controller.command.impl.admin;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import controller.command.Command;
import controller.util.LocaleExtractor;
import controller.util.SuccessJsonResponse;
import model.dto.OrderDTO;
import model.exception.CustomException;
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
            return SuccessJsonResponse.create("order.permitted",
                    LocaleExtractor.extractFromRequest(request));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new CustomException("error.bad.request");
        }
    }

    private OrderDTO getOrderDTOFromRequest(HttpServletRequest request) throws IOException {
        return new ObjectMapper().readValue(request.getReader(), OrderDTO.class);
    }
}
