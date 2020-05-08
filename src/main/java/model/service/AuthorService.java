package model.service;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
@Component
public class AuthorService {
    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public List<String> getAllAuthors() {
        return
                authorRepository.findAll()
                        .stream()
                        .map(this::getNameByLocale)
                        .collect(Collectors.toList());
    }

    private String getNameByLocale(Author author) {
        return LocaleContextHolder.getLocale().equals(Locale.ENGLISH) ? author.getName() : author.getNameUa();
    }

    public List<Author> getAuthorsFromStringArray(String[] authors) {
        log.info("get authors from array {}", Arrays.toString(authors));
        return Arrays.stream(authors)
                .map(x -> getByNameWithLocale(x)
                        .orElseThrow(() -> new AuthorNotFoundException("can not found author")))
                .collect(Collectors.toList());
    }

    private Optional<Author> getByNameWithLocale(String author) {
        return LocaleContextHolder.getLocale().equals(Locale.ENGLISH) ?
                authorRepository.findByName(author) : authorRepository.findByNameUa(author);
    }
}
