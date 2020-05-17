package controller.command.impl.admin;

import com.fasterxml.jackson.databind.ObjectMapper;
import controller.command.Command;
import model.dto.BookDTO;
import model.exception.BookAlreadyExistException;
import model.service.BookService;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class AddBookCommand implements Command {
    private final BookService bookService;

    public AddBookCommand(BookService bookService) {
        this.bookService = bookService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        try {
            bookService.saveNewBookFromClient(getBookDTOFromRequest(request));
        } catch (IOException e) {
            throw new BookAlreadyExistException("oops");
        }
        return "{}";
    }

    private BookDTO getBookDTOFromRequest(HttpServletRequest request) throws IOException {
        return new ObjectMapper().readValue(request.getReader(), BookDTO.class);
    }
}
