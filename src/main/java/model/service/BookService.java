package model.service;

import com.atomikos.icatch.jta.UserTransactionImp;
import model.dao.BookDao;
import model.dao.DaoFactory;
import model.dao.ShelfDao;
import model.dto.BookDTO;
import model.dto.FilterDTO;
import model.entity.*;
import model.exception.CustomException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.transaction.SystemException;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

/**
 * This service-class consists all operations with books and smaller part of them(tags and authors).
 *
 * @author Rostyslav Pryimak
 */
public class BookService {
    private final DaoFactory daoFactory;
    private final TagService tagService;
    private final AuthorService authorService;
    private final static Logger log = LogManager.getLogger(BookService.class);

    /**
     * Common constructor for creating BookService with tag- and author- services
     * and dao for book and shelf
     *
     * @param daoFactory    - daoFactory implementation for creating book and shelf dao
     * @param authorService - service, which is used for return list of authors from String array
     * @param tagService    - service, which is used for return list of tags from String array
     */
    public BookService(DaoFactory daoFactory, TagService tagService,
                       AuthorService authorService) {
        this.daoFactory = daoFactory;
        this.tagService = tagService;
        this.authorService = authorService;
    }

    /**
     * Method, which return available books for user request
     *
     * @param pageable - object, which is used for implementing pagination
     * @param locale   - locale, extracted from request
     * @return - result of getting books from database
     */
    public List<BookDTO> getAvailableBooks(Pageable pageable, Locale locale) {
        log.info("locale - " + locale);
        try (BookDao bookDao = daoFactory.createBookDao()) {
            return bookDao.findAllByAvailableIsTrue(pageable)
                    .stream()
                    .map(book -> buildBookDTO(book, locale))
                    .collect(Collectors.toList());
        }
    }

    /**
     * Method, which 'map' book, which we retrieve from repository to DTO object
     *
     * @param book   - entity, which will be converted
     * @param locale - locale, extracted from request
     * @return - DTO object of book
     */
    private BookDTO buildBookDTO(Book book, Locale locale) {
        return BookDTO.Builder.aBookDTO()
                .id(book.getBookId())
                .authors(getArrayOfAuthors(book, locale))
                .tag(getTagNameByLocale(book.getTag(), locale))
                .name(getBookNameByLocale(book, locale))
                .build();
    }

    /**
     * Method, which return book name depending on user locale
     *
     * @param book   - entity, that contains localized names
     * @param locale - locale, extracted from request
     * @return - return localized name of book
     */
    private String getBookNameByLocale(Book book, Locale locale) {
        return locale.equals(Locale.ENGLISH) ? book.getName() : book.getNameUa();
    }

    /**
     * Method, which return tag name depending on user locale
     *
     * @param tag    - entity, that contains localized names
     * @param locale - locale, extracted from request
     * @return - return localized name of tag
     */
    private String getTagNameByLocale(Tag tag, Locale locale) {
        return locale.equals(Locale.ENGLISH) ?
                tag.getName() : tag.getNameUa();
    }

    /**
     * Method, which 'map' book authors to String array, getting only localized name
     *
     * @param book   - entity, that contains List of authors
     * @param locale - locale, extracted from request
     * @return - array of localized authors name of book authors
     */
    private String[] getArrayOfAuthors(Book book, Locale locale) {
        return book.getAuthors()
                .stream()
                .map(author -> getAuthorsByLocale(author, locale))
                .toArray(String[]::new);
    }

    /**
     * Method, which return author name depending on user locale
     *
     * @param author - entity, that contains localized names
     * @param locale - locale, extracted from request
     * @return - return localized name of author
     */
    private String getAuthorsByLocale(Author author, Locale locale) {
        return locale.equals(Locale.ENGLISH) ?
                author.getName() : author.getNameUa();
    }

    /**
     * Method, which save new book, which was created in client by admin
     *
     * @param locale  - locale, extracted from request
     * @param bookDTO - DTO object of created by admin book
     */
    public void saveNewBookFromClient(BookDTO bookDTO, Locale locale) {
        log.info("create book - " + bookDTO);
        UserTransactionImp utx = new UserTransactionImp();
        try (BookDao bookDao = daoFactory.createBookDao();
             ShelfDao shelfDao = daoFactory.createShelfDao()) {
            utx.begin();
            Shelf shelf = shelfDao.findByBookId(null)
                    .orElseThrow(() -> new CustomException("shelf.not.found"));
            Book book = BuildBookFromClient(bookDTO, shelf, locale);
            bookDao.create(book);
            shelf.setBook(bookDao.findByName(book.getName())
                    .orElseThrow(() -> new CustomException("book.not.found")));
            shelfDao.update(shelf);
            try {
                utx.commit();
            } catch (Exception e) {
                try {
                    utx.rollback();
                } catch (SystemException systemException) {
                    log.fatal("cannot perform transaction {}", systemException.getMessage());
                }
            }
        } catch (Exception e) {
            log.error("cannot perform transaction {}", e.getMessage());
            throw new CustomException("book.already.exist");
        }
    }

    /**
     * Method, which create book entity from DTO object and shelf
     *
     * @param bookDTO - DTO object of created by admin book
     * @param shelf   - entity, which will be hold book
     * @param locale  - locale, extracted from request
     * @return - created book entity
     */
    private Book BuildBookFromClient(BookDTO bookDTO, Shelf shelf, Locale locale) {
        return Book.Builder.aBook()
                .name(bookDTO.getName())
                .nameUa(bookDTO.getNameUa())
                .shelf(shelf)
                .authors(authorService.getAuthorsFromStringArray(bookDTO.getAuthors(), locale))
                .tag(tagService.getTagByString(bookDTO.getTag(), locale))
                .available(true)
                .build();
    }

    /**
     * Method, which return DTO objects of available books for user request by filter
     *
     * @param filterDTO - DTO object, which is used for filtering books
     * @param pageable  - object, which is used for implementing pagination
     * @param locale    - locale, extracted from request
     * @return - DTO result of getting books from database by filter
     */
    public List<BookDTO> getAvailableBooksByFilter(FilterDTO filterDTO, Pageable pageable, Locale locale) {
        return getBooksByFilter(filterDTO, pageable, locale)
                .stream()
                .map(book -> buildBookDTO(book, locale))
                .collect(Collectors.toList());
    }

    /**
     * Method, which get books from database by filter and locale
     *
     * @param filterDTO - DTO object, which is used for filtering books
     * @param pageable  - object, which is used for implementing pagination
     * @param locale    - locale, extracted from request
     * @return - result of getting books from database by filter and locale
     */
    private List<Book> getBooksByFilter(FilterDTO filterDTO, Pageable pageable, Locale locale) {
        try (BookDao bookDao = daoFactory.createBookDao()) {
            return locale.equals(Locale.ENGLISH) ?
                    bookDao.getBooksByFilter(
                            filterDTO.getName(),
                            filterDTO.getAuthors(),
                            filterDTO.getTags(), pageable) :
                    bookDao.getBooksByFilterUa(
                            filterDTO.getName(),
                            filterDTO.getAuthors(),
                            filterDTO.getTags(), pageable);
        }
    }

    /**
     * Method, which implement editing book from client
     *
     * @param bookDTO - DTO object of edited book by admin
     */
    public void editBookAndSave(BookDTO bookDTO) {
        log.info("save book - " + bookDTO);
        try (BookDao bookDao = daoFactory.createBookDao()) {
            bookDao.update(getEditedBook(bookDTO));
        }
    }

    /**
     * Method, which 'map' edited DTO object to entity
     *
     * @param bookDTO - DTO object of edited book by admin
     * @return - edited book entity
     */
    private Book getEditedBook(BookDTO bookDTO) {
        try (BookDao bookDao = daoFactory.createBookDao()) {
            Book book = bookDao
                    .findById(bookDTO.getId())
                    .orElseThrow(() -> new CustomException("book.not.found"));
            book.setNameUa(bookDTO.getNameUa());
            book.setName(bookDTO.getName());
            return book;
        }
    }

    /**
     * Method, which delete book by admin request
     *
     * @param id - id of book, which will be deleted
     */
    public void deleteBook(long id) {
        log.info("delete book with id - " + id);
        try (BookDao bookDao = daoFactory.createBookDao()) {
            bookDao.delete(id);
        }
    }

    /**
     * Method, which get full books from database by filter and locale, that contains two names
     *
     * @param filterDTO - DTO object, which is used for filtering books
     * @param pageable  - object, which is used for implementing pagination
     * @param locale    - locale, extracted from request
     * @return - result of getting books from database by filter and locale
     */
    public List<BookDTO> getAvailableFullBooksByFilter(FilterDTO filterDTO, Pageable pageable, Locale locale) {
        return getBooksByFilter(filterDTO, pageable, locale)
                .stream()
                .map(book -> buildFullBookDTO(book, locale))
                .collect(Collectors.toList());
    }

    /**
     * Method, which return available full books for admin request,
     * that contains two names
     *
     * @param pageable - object, which is used for implementing pagination
     * @param locale   - locale, extracted from request
     * @return - result of getting books from database
     */
    public List<BookDTO> getAvailableFullBooks(Pageable pageable, Locale locale) {
        try (BookDao bookDao = daoFactory.createBookDao()) {
            return bookDao.findAllByAvailableIsTrue(pageable)
                    .stream()
                    .map(book -> buildFullBookDTO(book, locale))
                    .collect(Collectors.toList());
        }
    }

    /**
     * Method, which 'full map'(means that contains two names) book, which we retrieve from repository to DTO object
     *
     * @param book   - entity, which will be converted
     * @param locale - locale, extracted from request
     * @return - DTO object of book
     */
    private BookDTO buildFullBookDTO(Book book, Locale locale) {
        return BookDTO.Builder.aBookDTO()
                .id(book.getBookId())
                .authors(getArrayOfAuthors(book, locale))
                .tag(getTagNameByLocale(book.getTag(), locale))
                .name(book.getName())
                .nameUa(book.getNameUa())
                .build();
    }
}