package controller.command.impl.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import controller.command.Command;
import controller.util.LocaleExtractor;
import controller.util.SuccessJsonResponse;
import model.dto.OrderDTO;
import model.exception.CustomException;
import model.service.OrderService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class ReturnBookCommand implements Command {
    private final OrderService orderService;
    private final static Logger log = LogManager.getLogger(ReturnBookCommand.class);

    public ReturnBookCommand(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        log.info("returning book");
        try {
            orderService.returnBook(getOrderDTOFromRequest(request));
            return SuccessJsonResponse.create("book.returned",
                    LocaleExtractor.extractFromRequest(request));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            log.info("failed returning - " + e);
            throw new CustomException("error.bad.request");
        }

    }

    private OrderDTO getOrderDTOFromRequest(HttpServletRequest request) throws IOException {
        return new ObjectMapper().readValue(request.getReader(), OrderDTO.class);
    }
}
