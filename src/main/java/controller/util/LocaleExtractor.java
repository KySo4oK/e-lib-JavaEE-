package controller.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

public class LocaleExtractor {
    private static final Log log = LogFactory.getLog(LocaleExtractor.class);

    public static Locale extractFromRequest(HttpServletRequest request) {
        Object localeObj = request.getSession().getAttribute("language");
        log.info(localeObj);
        return localeObj.toString().equals("en") ? Locale.ENGLISH : new Locale("ua");
    }
}
