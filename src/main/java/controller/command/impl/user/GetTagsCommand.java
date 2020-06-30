package controller.command.impl.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import controller.command.Command;
import controller.util.LocaleExtractor;
import model.exception.CustomException;
import model.service.TagService;

import javax.servlet.http.HttpServletRequest;

public class GetTagsCommand implements Command {
    private final TagService tagsService;

    public GetTagsCommand(TagService tagsService) {
        this.tagsService = tagsService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        try {
            return new ObjectMapper().writeValueAsString(tagsService
                    .getAllTags(LocaleExtractor.extractFromRequest(request)));
        } catch (JsonProcessingException e) {
            throw new CustomException("error.server");
        }
    }
}
