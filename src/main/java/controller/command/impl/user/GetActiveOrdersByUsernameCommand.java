package controller.command.impl.user;

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

public class GetActiveOrdersByUsernameCommand implements Command {
    private final OrderService orderService;
    private final static Logger log = LogManager.getLogger(GetActiveOrdersByUsernameCommand.class);

    public GetActiveOrdersByUsernameCommand(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        try {
            return getJsonOfActiveOrdersByUserName(request
                            .getSession()
                            .getAttribute("username")
                            .toString(),
                    LocaleExtractor.extractFromRequest(request));
        } catch (JsonProcessingException e) {
            throw new CustomException("error.bad.request");
        }
    }

    private String getJsonOfActiveOrdersByUserName(String username, Locale locale)
            throws JsonProcessingException {
        log.info("request locale - " + locale);
        return new ObjectMapper()
                .writeValueAsString(orderService.getActiveOrdersByUserName(username, locale));
    }
}
