package controller.command.impl.admin;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import controller.command.Command;
import controller.util.LocaleExtractor;
import controller.util.SuccessJsonResponse;
import controller.util.Validator;
import model.dto.BookDTO;
import model.exception.CustomException;
import model.service.BookService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Locale;

public class AddBookCommand implements Command {
    private final BookService bookService;
    private final static Logger log = LogManager.getLogger(AddBookCommand.class);

    public AddBookCommand(BookService bookService) {
        this.bookService = bookService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        Locale locale = LocaleExtractor.extractFromRequest(request);
        try {
            BookDTO bookDTO = getBookDTOFromRequest(request);
            Validator.checkNewBook(bookDTO);
            log.info("trying save - " + bookDTO);
            bookService.saveNewBookFromClient(bookDTO, locale);
            return SuccessJsonResponse.create("book.added", locale);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            log.info("failed read json");
            throw new CustomException("error.bad.request");
        }
    }

    private BookDTO getBookDTOFromRequest(HttpServletRequest request) throws IOException {
        return new ObjectMapper().readValue(request.getReader(), BookDTO.class);
    }
}