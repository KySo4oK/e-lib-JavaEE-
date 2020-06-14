package controller.command.impl.admin;

import com.fasterxml.jackson.databind.ObjectMapper;
import controller.command.Command;
import controller.util.LocaleExtractor;
import model.dto.BookDTO;
import model.exception.BookAlreadyExistException;
import model.service.BookService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class AddBookCommand implements Command {
    private final BookService bookService;
    private final static Logger log = LogManager.getLogger(AddBookCommand.class);

    public AddBookCommand(BookService bookService) {
        this.bookService = bookService;
    }

    @Override
    public String execute(HttpServletRequest request) {

        try {
            BookDTO bookDTO = getBookDTOFromRequest(request);
            log.info("trying save - " + bookDTO);
            bookService.saveNewBookFromClient(bookDTO,
                    LocaleExtractor.extractFromRequest(request));
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
