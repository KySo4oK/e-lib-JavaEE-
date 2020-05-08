package model.service;

import model.entity.Tag;
import model.exception.TagNotFoundException;


import java.util.*;
import java.util.stream.Collectors;

public class TagService {
    private final TagRepository tagRepository;

    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    public List<String> getAllTags() {
        return tagRepository.findAll()
                .stream()
                .map(this::getTagsByLocale)
                .collect(Collectors.toList());
    }

    private String getTagsByLocale(Tag tag) {
        return LocaleContextHolder.getLocale().equals(Locale.ENGLISH) ?
                tag.getName() : tag.getNameUa();
    }

    public List<Tag> getTagsByStringArray(String[] tags) {
        log.info("get tags from array {}", Arrays.toString(tags));
        return Arrays.stream(tags)
                .map(x -> getByNameAndLocale(x)
                        .orElseThrow(() -> new TagNotFoundException("can not found tag")))
                .collect(Collectors.toList());
    }

    private Optional<Tag> getByNameAndLocale(String tag) {
        return LocaleContextHolder.getLocale().equals(Locale.ENGLISH) ?
                tagRepository.findByName(tag) : tagRepository.findByNameUa(tag);
    }
}
