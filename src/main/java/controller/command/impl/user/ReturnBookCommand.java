package controller.command.impl.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import controller.command.Command;
import model.dto.OrderDTO;
import model.exception.OrderNotFoundException;
import model.service.OrderService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class ReturnBookCommand implements Command {
    private final OrderService orderService;
    private static final Log log = LogFactory.getLog(ReturnBookCommand.class);

    public ReturnBookCommand(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        log.info("returning book");
        try {
            orderService.returnBook(getOrderDTOFromRequest(request));
        } catch (IOException e) {
            log.info("failed returning - " + e);
            throw new OrderNotFoundException("order not found");//todo use more specific exc
        }
        return "{}";
    }

    private OrderDTO getOrderDTOFromRequest(HttpServletRequest request) throws IOException {
        return new ObjectMapper().readValue(request.getReader(), OrderDTO.class);
    }
}
