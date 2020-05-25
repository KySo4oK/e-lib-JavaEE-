package model.service;

import model.dao.AuthorDao;
import model.dao.DaoFactory;
import model.entity.Author;
import model.exception.AuthorNotFoundException;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

public class AuthorService {
    private final DaoFactory daoFactory = DaoFactory.getInstance();
    private static final org.apache.logging.log4j.Logger log
            = org.apache.logging.log4j.LogManager.getLogger(AuthorService.class);

    public List<String> getAllAuthors(Locale locale) {
        try (AuthorDao authorDao = daoFactory.createAuthorDao()) {
            return authorDao.findAll()
                    .stream()
                    .map(a -> getNameByLocale(a, locale))
                    .collect(Collectors.toList());
        }
    }

    private String getNameByLocale(Author author, Locale locale) {
        return locale.equals(Locale.ENGLISH) ? author.getName() : author.getNameUa();
    }

    public List<Author> getAuthorsFromStringArray(String[] authors, Locale locale) {
        log.info("get authors from array {}", Arrays.toString(authors));
        return Arrays.stream(authors)
                .map(x -> getByNameWithLocale(x, locale)
                        .orElseThrow(() -> new AuthorNotFoundException("can not found author")))
                .collect(Collectors.toList());
    }

    private Optional<Author> getByNameWithLocale(String author, Locale locale) {
        try (AuthorDao authorDao = daoFactory.createAuthorDao()) {
            return locale.equals(Locale.ENGLISH) ?
                    authorDao.findByName(author) : authorDao.findByNameUa(author);
        }

    }
}
