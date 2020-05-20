package model.service;

import model.dao.DaoFactory;
import model.dao.TagDao;
import model.entity.Tag;
import model.exception.TagNotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class TagService {
    private final DaoFactory daoFactory = DaoFactory.getInstance();

    public List<String> getAllTags() {
        try (TagDao tagDao = daoFactory.createTagDao()) {
            return tagDao.findAll()
                    .stream()
                    .map(this::getTagsByLocale)
                    .collect(Collectors.toList());

        }
    }

    private String getTagsByLocale(Tag tag) {
        return /*LocaleContextHolder.getLocale().equals(Locale.ENGLISH)*/true ? //todo l10n
                tag.getName() : tag.getNameUa();
    }

    public Tag getTagByString(String tagName) {
        //log.info("get tagName from array {}", Arrays.toString(tagName)); todo logging
        return getByNameAndLocale(tagName)
                .orElseThrow(() -> new TagNotFoundException("can not found tagName"));
    }

    private Optional<Tag> getByNameAndLocale(String tag) {
        try (TagDao tagDao = daoFactory.createTagDao()) {
            return /*LocaleContextHolder.getLocale().equals(Locale.ENGLISH)*/true ? //todo l10n
                    tagDao.findByName(tag) : tagDao.findByNameUa(tag);
        }
    }
}
