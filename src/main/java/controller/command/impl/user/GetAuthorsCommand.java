package controller.command.impl.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import controller.command.Command;
import controller.util.LocaleExtractor;
import model.exception.AuthorNotFoundException;
import model.service.AuthorService;

import javax.servlet.http.HttpServletRequest;

public class GetAuthorsCommand implements Command {
    private final AuthorService authorService;

    public GetAuthorsCommand(AuthorService authorService) {
        this.authorService = authorService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        try {
            return new ObjectMapper().writeValueAsString(authorService
                    .getAllAuthors(LocaleExtractor.extractFromRequest(request)));
        } catch (JsonProcessingException e) {
            throw new AuthorNotFoundException("author not found"); // todo
        }
    }
}
