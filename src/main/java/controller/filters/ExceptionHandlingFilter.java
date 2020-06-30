package controller.filters;

import model.exception.CustomException;

import javax.servlet.*;
import java.io.IOException;

public class ExceptionHandlingFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        try {
            chain.doFilter(req, resp);
        } catch (CustomException e) {
        } catch (RuntimeException e) {
        }
    }

    public void init(FilterConfig config) {
    }
}