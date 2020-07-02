package controller.command.impl.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import controller.command.Command;
import controller.util.LocaleExtractor;
import controller.util.SuccessJsonResponse;
import model.dto.BookDTO;
import model.exception.CustomException;
import model.service.OrderService;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class OrderBookCommand implements Command {
    private final OrderService orderService;

    public OrderBookCommand(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        try {
            orderService.createAndSaveNewOrder(getBookDTOFromRequest(request), getUserNameFromRequest(request));
            return SuccessJsonResponse.create("order.created",
                    LocaleExtractor.extractFromRequest(request));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new CustomException("error.bad.request");
        }
    }

    private String getUserNameFromRequest(HttpServletRequest request) {
        return request.getSession().getAttribute("username").toString();
    }

    private BookDTO getBookDTOFromRequest(HttpServletRequest request) throws IOException {
        return new ObjectMapper().readValue(request.getReader(), BookDTO.class);
    }
}