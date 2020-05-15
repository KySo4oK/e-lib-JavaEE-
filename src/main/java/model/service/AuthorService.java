package model.service;

import model.dao.AuthorDao;
import model.entity.Author;
import model.exception.AuthorNotFoundException;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

public class AuthorService {
    private final AuthorDao authorDao;
    private static final org.apache.logging.log4j.Logger log
            = org.apache.logging.log4j.LogManager.getLogger(AuthorService.class);

    public AuthorService(AuthorDao authorDao) {
        this.authorDao = authorDao;
    }

    public List<String> getAllAuthors() {
        return
                authorDao.findAll()
                        .stream()
                        .map(this::getNameByLocale)
                        .collect(Collectors.toList());
    }

    private String getNameByLocale(Author author) {
        return /*LocaleContextHolder.getLocale().equals(Locale.ENGLISH)*/true ? author.getName() : author.getNameUa();
    }

//    public List<Author> getAuthorsFromStringArray(String[] authors) {
//        log.info("get authors from array {}", Arrays.toString(authors));
//        return Arrays.stream(authors)
//                .map(x -> getByNameWithLocale(x)
//                        .orElseThrow(() -> new AuthorNotFoundException("can not found author")))
//                .collect(Collectors.toList());
//    }

//    private Optional<Author> getByNameWithLocale(String author) {
//        return Optional.of(/*LocaleContextHolder.getLocale().equals(Locale.ENGLISH)*/true ?
//                authorDao.findByName(author) : authorDao.findByNameUa(author));
//    }
}
