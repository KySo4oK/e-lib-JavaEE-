package controller.filters;

import model.entity.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class ServletSecurityFilter implements Filter {
    private static final org.apache.logging.log4j.Logger log
            = org.apache.logging.log4j.LogManager.getLogger(ServletSecurityFilter.class);

    public void destroy() {
    }

    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        System.out.println("auth filter");
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpSession session = req.getSession();
        System.out.println(session.getAttribute("role"));
        log.info(session.getAttribute("role"));
        if (session.getAttribute("role") == null) {
            setGuestRole(session);
        }
        System.out.println(req.getRequestURI());
        if (!checkAccess(req, User.ROLE.valueOf(session.getAttribute("role").toString()))) {
            resp.sendRedirect("redirect:/error");
            return;
        }
        chain.doFilter(request, response);
    }

    private boolean isLogoutCommand(HttpServletRequest req) {
        return uriContains(req, "logout");
    }

    private boolean checkAccess(HttpServletRequest req, User.ROLE role) {
        if (checkForUserRole(req, role, User.ROLE.USER, "user") ||
                checkForUserRole(req, role, User.ROLE.ADMIN, "admin") || uriContains(req, "error")) {
            return true;
        }
        return checkForGuest(req, role);
    }

    private boolean checkForGuest(HttpServletRequest req, User.ROLE role) {
        return role.equals(User.ROLE.UNKNOWN) && (!uriContains(req, "admin") && !(uriContains(req, "user"))
                && !isLogoutCommand(req));
    }

    private boolean checkForUserRole(HttpServletRequest req, User.ROLE role, User.ROLE user, String user2) {
        return role.equals(user) &&
                (uriContains(req, user2) || isLogoutCommand(req)) && notLogin(req);
    }

    private boolean notLogin(HttpServletRequest req) {
        return !uriContains(req, "login");
    }

    private boolean uriContains(HttpServletRequest req, String role) {
        return req.getRequestURI().contains(role);
    }

    private void setGuestRole(HttpSession session) {
        session.setAttribute("role", User.ROLE.UNKNOWN.toString());
    }

    public void init(FilterConfig fConfig) throws ServletException {
    }

}
