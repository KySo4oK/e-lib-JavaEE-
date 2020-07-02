package controller.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

public class LocaleExtractor {
    private final static Logger log = LogManager.getLogger(LocaleExtractor.class);

    public static Locale extractFromRequest(HttpServletRequest request) {
        Object localeObj = request.getSession().getAttribute("language");
        log.info(localeObj);
        return localeObj.toString().equals("en") ? Locale.ENGLISH : new Locale("ua");
    }
}