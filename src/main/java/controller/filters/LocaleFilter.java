package controller.filters;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;

public class LocaleFilter implements javax.servlet.Filter {
    private final static Logger log = LogManager.getLogger(LocaleFilter.class);

    public void destroy() {
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        log.info("request lang - " + req.getParameter("language"));
        log.info("session lang - " + req.getSession().getAttribute("language"));
        if (req.getParameter("language") != null) {
            req.getSession().setAttribute("language", req.getParameter("language"));
        } else if (req.getSession().getAttribute("language") == null) {
            req.getSession().setAttribute("language", Locale.ENGLISH);
        }
        chain.doFilter(request, response);
    }

    public void init(FilterConfig config) {
    }

}
