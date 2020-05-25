package controller.command.impl.admin;

import com.fasterxml.jackson.databind.ObjectMapper;
import controller.command.Command;
import model.dto.BookDTO;
import model.exception.BookAlreadyExistException;
import model.service.BookService;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Locale;

public class EditBookCommand implements Command {
    private final BookService bookService;

    public EditBookCommand(BookService bookService) {
        this.bookService = bookService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        try {
            bookService.editBookAndSave(getBookDTOFromRequest(request),
                    (Locale) request.getSession().getAttribute("language"));
        } catch (IOException e) {
            throw new BookAlreadyExistException("oops");
        }
        return "{}";
    }

    private BookDTO getBookDTOFromRequest(HttpServletRequest request) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(request.getReader(), BookDTO.class);
    }
}
