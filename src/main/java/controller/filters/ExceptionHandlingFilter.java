package controller.filters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import controller.util.LocaleExtractor;
import controller.util.MessageSource;
import model.exception.CustomException;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.Locale;

public class ExceptionHandlingFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
            throws ServletException, IOException {
        try {
            chain.doFilter(req, resp);
        } catch (RuntimeException e) {
            sendErrorWithLocalizedMessageByLocale(resp,
                    e instanceof CustomException ? e.getMessage() : "error.unknown",
                    LocaleExtractor.extractFromRequest((HttpServletRequest) req));
        }
    }

    public void init(FilterConfig config) {
    }

    private void sendErrorWithLocalizedMessageByLocale(ServletResponse resp, String messageKey, Locale locale)
            throws IOException {
        ((HttpServletResponse) resp).setStatus(402);
        resp.getWriter().print(
                writeJson(Collections.singletonMap("error",
                        MessageSource.getMessage(messageKey, locale))));
    }

    private String writeJson(Object object) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(object);
    }
}