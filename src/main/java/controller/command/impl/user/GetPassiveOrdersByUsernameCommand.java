package controller.command.impl.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import controller.command.Command;
import controller.util.LocaleExtractor;
import model.exception.OrderNotFoundException;
import model.service.OrderService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

public class GetPassiveOrdersByUsernameCommand implements Command {
    private final OrderService orderService;
    private final static Logger log = LogManager.getLogger(GetPassiveOrdersByUsernameCommand.class);

    public GetPassiveOrdersByUsernameCommand(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        try {
            return getJsonOfPassiveOrdersByUserName(request.getSession().getAttribute("username").toString(),
                    LocaleExtractor.extractFromRequest(request));
        } catch (JsonProcessingException e) {
            throw new OrderNotFoundException("order not found"); //todo
        }
    }

    private String getJsonOfPassiveOrdersByUserName(String username, Locale locale) throws JsonProcessingException {
        log.info("request locale - " + locale);
        return new ObjectMapper().writeValueAsString(orderService.getPassiveOrdersByUserName(username, locale));
    }
}
