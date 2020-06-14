package model.service;

import model.dao.DaoFactory;
import model.dao.TagDao;
import model.entity.Tag;
import model.exception.TagNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

public class TagService {
    private final DaoFactory daoFactory = DaoFactory.getInstance();
    private final static Logger log = LogManager.getLogger(TagService.class);

    public List<String> getAllTags(Locale locale) {
        try (TagDao tagDao = daoFactory.createTagDao()) {
            return tagDao.findAll()
                    .stream()
                    .map(tag -> getTagsByLocale(tag, locale))
                    .collect(Collectors.toList());

        }
    }

    private String getTagsByLocale(Tag tag, Locale locale) {
        return locale.equals(Locale.ENGLISH) ?
                tag.getName() : tag.getNameUa();
    }

    public Tag getTagByString(String tagName, Locale locale) {
        return getByNameAndLocale(tagName, locale)
                .orElseThrow(() -> new TagNotFoundException("can not found tagName"));
    }

    private Optional<Tag> getByNameAndLocale(String tag, Locale locale) {
        try (TagDao tagDao = daoFactory.createTagDao()) {
            return locale.equals(Locale.ENGLISH) ?
                    tagDao.findByName(tag) : tagDao.findByNameUa(tag);
        }
    }
}
