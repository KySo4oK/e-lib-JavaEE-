package controller.command.impl.admin;

import com.fasterxml.jackson.databind.ObjectMapper;
import controller.command.Command;
import controller.util.LocaleExtractor;
import controller.util.PageableExtractor;
import model.dto.BookDTO;
import model.dto.FilterDTO;
import model.exception.BookNotFoundException;
import model.service.BookService;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

public class GetAvailableFullBooksByFilterCommand implements Command {
    private final BookService bookService;

    public GetAvailableFullBooksByFilterCommand(BookService bookService) {
        this.bookService = bookService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        try {
            return getJsonOfBookList(getAvailableBooksByFilter(request));//todo pagination
        } catch (IOException e) {
            throw new BookNotFoundException("not found");//todo
        }
    }

    private List<BookDTO> getAvailableBooksByFilter(HttpServletRequest request) throws IOException {
        return bookService
                .getAvailableFullBooksByFilter(getFilterDTOFromRequest(request),
                        PageableExtractor.extractPageableFromUri(request.getRequestURI()),
                        LocaleExtractor.extractFromRequest(request));
    }

    private String getJsonOfBookList(List<BookDTO> list) throws IOException {
        return new ObjectMapper().writeValueAsString(list);
    }

    private FilterDTO getFilterDTOFromRequest(HttpServletRequest request) throws IOException {
        return new ObjectMapper().readValue(request.getReader(), FilterDTO.class);
    }
}
