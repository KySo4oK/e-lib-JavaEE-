package controller.util;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

public class LocaleExtractor {

    public static Locale extractFromRequest(HttpServletRequest request) {
        Object localeObj = request.getSession().getAttribute("language");
        return localeObj.toString().equals("en") ? Locale.ENGLISH : new Locale("ua");
    }
}