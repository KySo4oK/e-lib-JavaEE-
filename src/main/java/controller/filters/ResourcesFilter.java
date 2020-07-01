package controller.filters;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ResourcesFilter implements Filter {
    private static final String absolutePathToApp = "C:\\Users\\KySo4oK\\IdeaProjects\\e-lib(JavaEE)\\src\\main\\webapp";

    public void destroy() {
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException {
        HttpServletRequest req = (HttpServletRequest) request;
        String path = req.getRequestURI().substring(req.getContextPath().length());
        try {
            response.setContentType("text/css");
            response.getWriter().print(Files.readString(Path.of(absolutePathToApp + path)));
        } catch (IOException e) {
            ((HttpServletResponse) response).sendError(404);
        }
    }

    public void init(FilterConfig config) {
    }
}