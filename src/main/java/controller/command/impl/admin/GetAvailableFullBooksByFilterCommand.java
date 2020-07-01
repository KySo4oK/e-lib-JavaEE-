package controller.command.impl.admin;

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

public class GetAvailableFullBooksByFilterCommand implements Command {
    private final BookService bookService;

    public GetAvailableFullBooksByFilterCommand(BookService bookService) {
        this.bookService = bookService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        try {
            return getJsonOfBookList(getDTOOfAvailableBooksByFilter(request));
        } catch (IOException e) {
            throw new CustomException("error.bad.request");
        }
    }

    private List<BookDTO> getDTOOfAvailableBooksByFilter(HttpServletRequest request) throws IOException {
        FilterDTO filterDTO = getFilterDTOFromRequest(request);
        Validator.checkFilterDTO(filterDTO);
        return bookService
                .getAvailableFullBooksByFilter(filterDTO,
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
