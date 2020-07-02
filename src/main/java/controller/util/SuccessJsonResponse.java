package controller.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Collections;
import java.util.Locale;

public class SuccessJsonResponse {
    public static String create(String messageKey, Locale locale) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(Collections.singletonMap("message",
                MessageSource.getMessage(messageKey, locale)));
    }
}