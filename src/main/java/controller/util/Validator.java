package controller.util;


import model.dto.BookDTO;
import model.dto.FilterDTO;
import model.dto.UserDTO;
import model.exception.CustomException;

import java.util.regex.Pattern;

public class Validator {
    public static void checkNewBook(BookDTO bookDTO) {
        if (Pattern.matches(ValidationRegex.bookName, bookDTO.getName()) &&
                Pattern.matches(ValidationRegex.bookNameUa, bookDTO.getNameUa()) &&
                bookDTO.getAuthors().length > 0 &&
                bookDTO.getTag() != null) {
            return;
        }
        throw new CustomException("impossible.book.values");
    }

    public static void checkFilterDTO(FilterDTO filterDTO) {
        if (filterDTO.getName().length() <= 2 ||
                filterDTO.getAuthors().length == 0 ||
                filterDTO.getTags().length == 0) {
            throw new CustomException("impossible.filter.values");
        }
    }

    public static void checkRegistration(UserDTO userDTO) {
        if (checkLoginData(userDTO)
                && Pattern.matches(ValidationRegex.phoneRegex, userDTO.getPhone())
                && Pattern.matches(ValidationRegex.emailRegex, userDTO.getEmail())) {
            return;
        }
        throw new CustomException("bad.credentials");
    }

    public static void checkLogin(UserDTO userDTO) {
        if (checkLoginData(userDTO)) {
            return;
        }
        throw new CustomException("bad.credentials");
    }

    private static boolean checkLoginData(UserDTO userDTO) {
        return Pattern.matches(ValidationRegex.usernameRegex, userDTO.getUsername())
                && Pattern.matches(ValidationRegex.passwordRegex, userDTO.getPassword());
    }


}