package controller.command.impl.admin;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import controller.command.Command;
import controller.util.LocaleExtractor;
import model.exception.CustomException;
import model.service.OrderService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

public class GetActiveOrdersCommand implements Command {
    private final OrderService orderService;
    private final static Logger log = LogManager.getLogger(GetActiveOrdersCommand.class);

    public GetActiveOrdersCommand(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        try {
            return getJsonOfActiveOrders(LocaleExtractor.extractFromRequest(request));
        } catch (JsonProcessingException e) {
            throw new CustomException("error.server");
        }
    }

    private String getJsonOfActiveOrders(Locale locale) throws JsonProcessingException {
        log.info("request locale - " + locale);
        return new ObjectMapper().writeValueAsString(orderService.getActiveOrders(locale));
    }
}
