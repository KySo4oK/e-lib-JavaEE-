package controller.command.impl.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import controller.command.Command;
import controller.util.LocaleExtractor;
import controller.util.PageableExtractor;
import controller.util.Validator;
import model.dto.BookDTO;
import model.dto.FilterDTO;
import model.exception.CustomException;
import model.service.BookService;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

public class GetAvailableBooksByFilterCommand implements Command {
    private final BookService bookService;

    public GetAvailableBooksByFilterCommand(BookService bookService) {
        this.bookService = bookService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        try {
            return getJsonOfBookList(getDTOOfAvailableBooksByFilter(request));
        } catch (IOException e) {
            throw new CustomException("error.server");
        }
    }

    private List<BookDTO> getDTOOfAvailableBooksByFilter(HttpServletRequest request) throws IOException {
        FilterDTO filterDTO = getFilterDTOFromRequest(request);
        Validator.checkFilterDTO(filterDTO);
        return bookService
                .getAvailableBooksByFilter(filterDTO,
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