package controller.filters;

import controller.util.LocaleExtractor;
import controller.util.MessageSource;
import model.exception.CustomException;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class ExceptionHandlingFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
            throws ServletException, IOException {
        try {
            chain.doFilter(req, resp);
        } catch (CustomException e) {
            throw new RuntimeException(
                    MessageSource.getMessage(e.getMessage(),
                            LocaleExtractor.extractFromRequest((HttpServletRequest) req)));
        } catch (RuntimeException e) {
            throw new RuntimeException(
                    MessageSource.getMessage("error.unknown",
                            LocaleExtractor.extractFromRequest((HttpServletRequest) req)));
        }
    }

    public void init(FilterConfig config) {
    }
}