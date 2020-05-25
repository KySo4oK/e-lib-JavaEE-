package model.service;

import model.dao.BookDao;
import model.dao.DaoFactory;
import model.dao.ShelfDao;
import model.dto.BookDTO;
import model.dto.FilterDTO;
import model.entity.Author;
import model.entity.Book;
import model.entity.Shelf;
import model.entity.Tag;
import model.exception.BookNotFoundException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class BookService {
    private final DaoFactory daoFactory = DaoFactory.getInstance();
    private final TagService tagService;
    private final AuthorService authorService;
    private static final Log log
            = LogFactory.getLog(BookService.class);

    public BookService(TagService tagService,
                       AuthorService authorService) {
        this.tagService = tagService;
        this.authorService = authorService;
    }

    public List<BookDTO> getAvailableBooks(/*Pageable pageable*/Locale locale) {
        log.info("locale - " + locale);
        try (BookDao bookDao = daoFactory.createBookDao()) {
            return bookDao.findAllByAvailableIsTrue(/*pageable*/)
                    .stream()
                    .map(book -> buildBookDTO(book, locale))
                    .collect(Collectors.toList());
        }
    }

    private BookDTO buildBookDTO(Book book, Locale locale) {
        return BookDTO.Builder.aBookDTO()
                .id(book.getBookId())
                .authors(getArrayOfAuthors(book, locale))
                .tag(getTagNameByLocale(book.getTag(), locale))
                .name(getBookNameByLocale(book, locale))
                .build();
    }

    private String getBookNameByLocale(Book book, Locale locale) {
        return locale.equals(Locale.ENGLISH) ? book.getName() : book.getNameUa();
    }


    private String getTagNameByLocale(Tag tag, Locale locale) {
        return locale.equals(Locale.ENGLISH) ?
                tag.getName() : tag.getNameUa();
    }

    private String[] getArrayOfAuthors(Book book, Locale locale) {
        return book.getAuthors()
                .stream()
                .map(author -> getAuthorsByLocale(author, locale))
                .toArray(String[]::new);
    }

    private String getAuthorsByLocale(Author author, Locale locale) {
        return locale.equals(Locale.ENGLISH) ?
                author.getName() : author.getNameUa();
    }

    //@Transactional
    public void saveNewBookFromClient(BookDTO bookDTO, Locale locale) {
        log.info("create book - " + bookDTO);
        try (BookDao bookDao = daoFactory.createBookDao();
             ShelfDao shelfDao = daoFactory.createShelfDao()) {
            Shelf shelf = shelfDao.findByBookId(null)
                    .orElseThrow(() -> new RuntimeException("not available shelf"));//todo change exc
            Book book = BuildBookFromClient(bookDTO, shelf, locale);
            bookDao.create(book);
            shelf.setBook(bookDao.findByName(book.getName())
                    .orElseThrow(() -> new BookNotFoundException("failed saving")));
            shelfDao.update(shelf);
        }
    }

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

    public List<BookDTO> getAvailableBooksByFilter(FilterDTO filterDTO,/*, Pageable pageable*/Locale locale) {//todo
        return getBooksByFilter(filterDTO,/*, pageable*/locale)
                .stream()
                .map(book -> buildBookDTO(book, locale))
                .collect(Collectors.toList());

    }

    private List<Book> getBooksByFilter(FilterDTO filterDTO,/*, Pageable pageable*/Locale locale) {
        try (BookDao bookDao = daoFactory.createBookDao()) {
            return locale.equals(Locale.ENGLISH) ?
                    bookDao.getBooksByFilter(
                            filterDTO.getName(),
                            filterDTO.getAuthors(),
                            filterDTO.getTags()/*, pageable*/) :
                    bookDao.getBooksByFilterUa(
                            filterDTO.getName(),
                            filterDTO.getAuthors(),
                            filterDTO.getTags()/*, pageable*/);
        }
    }

    public void editBookAndSave(BookDTO bookDTO, Locale locale) throws BookNotFoundException {
        log.info("save book - " + bookDTO);
        try (BookDao bookDao = daoFactory.createBookDao()) {
            bookDao.update(getEditedBook(bookDTO, locale));//todo normal updating
        }
    }

    private Book getEditedBook(BookDTO bookDTO, Locale locale) {
        try (BookDao bookDao = daoFactory.createBookDao()) {
            Book book = bookDao
                    .findById(bookDTO.getId())
                    .orElseThrow(() -> new BookNotFoundException("book not exist"));
            book.setAuthors(authorService.getAuthorsFromStringArray(bookDTO.getAuthors(), locale));
            book.setTag(tagService.getTagByString(bookDTO.getTag(), locale));
            return book;
        }
    }

    public void deleteBook(long id) throws BookNotFoundException {
        log.info("delete book with id - " + id);
        try (BookDao bookDao = daoFactory.createBookDao()) {
            bookDao.delete(id);
        }
    }
}