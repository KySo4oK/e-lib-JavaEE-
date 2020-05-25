package controller.command.impl.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import controller.command.Command;
import model.exception.TagNotFoundException;
import model.service.TagService;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

public class GetTagsCommand implements Command {
    private final TagService tagsService;

    public GetTagsCommand(TagService tagsService) {
        this.tagsService = tagsService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        try {
            return new ObjectMapper().writeValueAsString(tagsService
                    .getAllTags((Locale) request.getSession().getAttribute("language")));
        } catch (JsonProcessingException e) {
            throw new TagNotFoundException("tag not found");//todo use more specific exc
        }
    }
}
