package controller.command.impl.admin;

import com.fasterxml.jackson.databind.ObjectMapper;
import controller.command.Command;
import model.dto.BookDTO;
import model.exception.BookAlreadyExistException;
import model.service.BookService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Locale;

public class AddBookCommand implements Command {
    private final BookService bookService;
    private final static Log log = LogFactory.getLog(AddBookCommand.class);

    public AddBookCommand(BookService bookService) {
        this.bookService = bookService;
    }

    @Override
    public String execute(HttpServletRequest request) {

        try {
            BookDTO bookDTO = getBookDTOFromRequest(request);
            log.info("trying save - " + bookDTO);
            bookService.saveNewBookFromClient(bookDTO,
                    (Locale) request.getSession().getAttribute("language"));
        } catch (IOException e) {
            log.info("failed read json");
            throw new BookAlreadyExistException("oops");
        }
        return "{}";
    }

    private BookDTO getBookDTOFromRequest(HttpServletRequest request) throws IOException {
        return new ObjectMapper().readValue(request.getReader(), BookDTO.class);
    }
}
