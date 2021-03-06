package controller.command.impl.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import controller.command.Command;
import controller.util.LocaleExtractor;
import controller.util.PageableExtractor;
import model.dto.BookDTO;
import model.exception.CustomException;
import model.service.BookService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

public class GetAvailableBooksCommand implements Command {
    private final BookService bookService;
    private final static Logger log = LogManager.getLogger(GetAvailableBooksCommand.class);

    public GetAvailableBooksCommand(BookService bookService) {
        this.bookService = bookService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        try {
            return getJsonOfBookList(getDTOOfAvailableBooksByFilter(request));
        } catch (Exception e) {
            log.info(e);
            throw new CustomException("error.bad.request");
        }
    }

    private List<BookDTO> getDTOOfAvailableBooksByFilter(HttpServletRequest request) {
        return bookService
                .getAvailableBooks(PageableExtractor.extractPageableFromUri(request.getRequestURI()),
                        LocaleExtractor.extractFromRequest(request));
    }

    private String getJsonOfBookList(List<BookDTO> list) throws IOException {
        return new ObjectMapper().writeValueAsString(list);
    }
}