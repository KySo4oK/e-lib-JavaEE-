package controller.command.impl.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import controller.command.Command;
import model.dto.BookDTO;
import model.dto.FilterDTO;
import model.exception.BookNotFoundException;
import model.service.BookService;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

public class GetAvailableBooksCommand implements Command {
    private final BookService bookService;

    public GetAvailableBooksCommand(BookService bookService) {
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
                .getAvailableBooks();//todo
    }

    private String getJsonOfBookList(List<BookDTO> list) throws IOException {
        return new ObjectMapper().writeValueAsString(list);
    }
}
