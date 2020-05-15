package controller.command.impl.admin;

import controller.command.Command;
import model.service.BookService;

import javax.servlet.http.HttpServletRequest;

public class DeleteBookCommand implements Command {
    private final BookService bookService;

    public DeleteBookCommand(BookService bookService) {
        this.bookService = bookService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        bookService.deleteBook(Integer.parseInt(request.getPathInfo().split("/")[1]));//todo with better style
        return "{}";
    }
}
