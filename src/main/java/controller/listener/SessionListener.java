package controller.listener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.HashSet;


public class SessionListener implements HttpSessionListener {
    private static final Log log = LogFactory.getLog(SessionListener.class);

    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {

    }

    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        HashSet<String> loggedUsers = (HashSet<String>) httpSessionEvent
                .getSession().getServletContext()
                .getAttribute("loggedUsers");
        String userName = (String) httpSessionEvent.getSession()
                .getAttribute("username");
        loggedUsers.remove(userName);
        httpSessionEvent.getSession().setAttribute("loggedUsers", loggedUsers);
        log.info("remove from logged users - " + userName);
    }
}
