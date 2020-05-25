package controller.command.impl.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import controller.command.Command;
import controller.util.LocaleExtractor;
import controller.util.PageableExtractor;
import model.dto.BookDTO;
import model.exception.BookNotFoundException;
import model.service.BookService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class GetAvailableBooksCommand implements Command {
    private final BookService bookService;
    private static final Log log = LogFactory.getLog(GetAvailableBooksCommand.class);

    public GetAvailableBooksCommand(BookService bookService) {
        this.bookService = bookService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        log.info("executing command");
        try {
            return getJsonOfBookList(getAvailableBooksByFilter(request));
        } catch (Exception e) {
            log.info(e);
            throw new BookNotFoundException("not found");//todo
        }
    }

    private List<BookDTO> getAvailableBooksByFilter(HttpServletRequest request) {
        return bookService
                .getAvailableBooks(PageableExtractor.extractPageableFromUri(request.getRequestURI()),
                        LocaleExtractor.extractFromRequest(request));
    }

    private String getJsonOfBookList(List<BookDTO> list) throws IOException {
        return new ObjectMapper().writeValueAsString(list);
    }
}
