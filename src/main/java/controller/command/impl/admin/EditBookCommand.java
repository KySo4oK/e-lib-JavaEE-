package controller.command.impl.admin;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import controller.command.Command;
import controller.util.LocaleExtractor;
import controller.util.SuccessJsonResponse;
import model.dto.BookDTO;
import model.exception.CustomException;
import model.service.BookService;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class EditBookCommand implements Command {
    private final BookService bookService;

    public EditBookCommand(BookService bookService) {
        this.bookService = bookService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        try {
            bookService.editBookAndSave(getBookDTOFromRequest(request));
            return SuccessJsonResponse.create("book.edited",
                    LocaleExtractor.extractFromRequest(request));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new CustomException("error.bad.request");
        }
    }

    private BookDTO getBookDTOFromRequest(HttpServletRequest request) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(request.getReader(), BookDTO.class);
    }
}