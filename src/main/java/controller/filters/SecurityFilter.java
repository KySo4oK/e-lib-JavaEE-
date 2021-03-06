package controller.filters;

import model.entity.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class SecurityFilter implements Filter {

    public void destroy() {
    }

    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpSession session = req.getSession();
        if (session.getAttribute("role") == null) {
            setGuestRole(session);
        }
        User.ROLE role = User.ROLE.valueOf(session.getAttribute("role").toString());
        if (!checkAccess(req, role)) {
            if (role.equals(User.ROLE.UNKNOWN)) {
                resp.sendRedirect("/login");
            }
            resp.sendError(403);
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

    public void init(FilterConfig fConfig) {
    }
}