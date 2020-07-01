package controller.command.impl.admin;

import com.fasterxml.jackson.core.JsonProcessingException;
import controller.command.Command;
import controller.util.LocaleExtractor;
import controller.util.SuccessJsonResponse;
import model.service.BookService;

import javax.servlet.http.HttpServletRequest;

public class DeleteBookCommand implements Command {
    private final BookService bookService;

    public DeleteBookCommand(BookService bookService) {
        this.bookService = bookService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        bookService.deleteBook(extractBookId(request));
        try {
            return SuccessJsonResponse.create("book.deleted",
                    LocaleExtractor.extractFromRequest(request));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private int extractBookId(HttpServletRequest request) {
        return Integer.parseInt(request.getRequestURI().split("/")[3]);
    }
}
