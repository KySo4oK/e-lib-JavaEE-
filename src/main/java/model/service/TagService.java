package model.service;

import model.dao.DaoFactory;
import model.dao.TagDao;
import model.entity.Tag;
import model.exception.TagNotFoundException;


import java.util.*;
import java.util.stream.Collectors;

public class TagService {
    private final TagDao tagDao = DaoFactory.getInstance().createTagDao();

    public List<String> getAllTags() {
        return tagDao.findAll()
                .stream()
                .map(this::getTagsByLocale)
                .collect(Collectors.toList());
    }

    private String getTagsByLocale(Tag tag) {
        return /*LocaleContextHolder.getLocale().equals(Locale.ENGLISH)*/true ? //todo l10n
                tag.getName() : tag.getNameUa();
    }

    public List<Tag> getTagsByStringArray(String[] tags) {
        //log.info("get tags from array {}", Arrays.toString(tags)); todo logging
        return Arrays.stream(tags)
                .map(x -> getByNameAndLocale(x)
                        .orElseThrow(() -> new TagNotFoundException("can not found tag")))
                .collect(Collectors.toList());
    }

    private Optional<Tag> getByNameAndLocale(String tag) {
        return /*LocaleContextHolder.getLocale().equals(Locale.ENGLISH)*/true ? //todo l10n
                tagDao.findByName(tag) : tagDao.findByNameUa(tag);
    }
}
