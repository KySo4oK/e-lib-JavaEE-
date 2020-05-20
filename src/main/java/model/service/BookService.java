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

import java.util.List;
import java.util.stream.Collectors;

public class BookService {
    private final DaoFactory daoFactory = DaoFactory.getInstance();
    private final TagService tagService;
    private final AuthorService authorService;
    private static final org.apache.logging.log4j.Logger log
            = org.apache.logging.log4j.LogManager.getLogger(BookService.class);

    public BookService(TagService tagService,
                       AuthorService authorService) {
        this.tagService = tagService;
        this.authorService = authorService;
    }

    public List<BookDTO> getAvailableBooks(/*Pageable pageable*/) {
        //return bookDao.findAllByAvailableIsTrue(pageable) //todo
        try (BookDao bookDao = daoFactory.createBookDao()) {
            return bookDao.findAll()
                    .stream()
                    .map(this::buildBookDTO)
                    .collect(Collectors.toList());
        }
    }

    private BookDTO buildBookDTO(Book book) {
        return BookDTO.Builder.aBookDTO()
                .id(book.getBookId())
                .authors(getArrayOfAuthors(book))
                .tag(getTagNameByLocale(book.getTag()))
                .name(getBookNameByLocale(book))
                .build();
    }

    private String getBookNameByLocale(Book book) {
        return /*LocaleContextHolder.getLocale().equals(Locale.ENGLISH)*/true ? book.getName() : book.getNameUa();
    }


    private String getTagNameByLocale(Tag tag) {
        return /*LocaleContextHolder.getLocale().equals(Locale.ENGLISH)*/true ?
                tag.getName() : tag.getNameUa();
    }

    private String[] getArrayOfAuthors(Book book) {
        return book.getAuthors()
                .stream()
                .map(this::getAuthorsByLocale)
                .toArray(String[]::new);
    }

    private String getAuthorsByLocale(Author author) {
        return /*LocaleContextHolder.getLocale().equals(Locale.ENGLISH)*/true ?
                author.getName() : author.getNameUa();
    }

    //@Transactional
    public void saveNewBookFromClient(BookDTO bookDTO) {
        log.info("create book {}", bookDTO);
        try (BookDao bookDao = daoFactory.createBookDao();
             ShelfDao shelfDao = daoFactory.createShelfDao()) {
            Shelf shelf = shelfDao.findByBookId(null).orElse(new Shelf());
            Book book = BuildBookFromClient(bookDTO, shelf);
            bookDao.create(book);
            shelf.setBook(book);
            shelfDao.create(shelf);
        }
    }

    private Book BuildBookFromClient(BookDTO bookDTO, Shelf shelf) {
        return Book.Builder.aBook()
                .name(bookDTO.getName())
                .nameUa(bookDTO.getNameUa())
                .shelf(shelf)
                .authors(authorService.getAuthorsFromStringArray(bookDTO.getAuthors()))
                .tag(tagService.getTagByString(bookDTO.getTag()))
                .available(true)
                .build();
    }

    public List<BookDTO> getAvailableBooksByFilter(FilterDTO filterDTO/*, Pageable pageable*/) {//todo
        return getBooksByFilter(filterDTO/*, pageable*/)
                .stream()
                .map(this::buildBookDTO)
                .collect(Collectors.toList());

    }

    private List<Book> getBooksByFilter(FilterDTO filterDTO/*, Pageable pageable*/) {
        try (BookDao bookDao = daoFactory.createBookDao()) {
            return /*LocaleContextHolder.getLocale().equals(Locale.ENGLISH)*/true ?
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

    public void editBookAndSave(BookDTO bookDTO) throws BookNotFoundException {
        log.info("save book {}", bookDTO);
        try (BookDao bookDao = daoFactory.createBookDao()) {
            bookDao.update(getEditedBook(bookDTO));//todo normal updating
        }
    }

    private Book getEditedBook(BookDTO bookDTO) {
        try (BookDao bookDao = daoFactory.createBookDao()) {
            Book book = bookDao
                    .findById(bookDTO.getId())
                    .orElseThrow(() -> new BookNotFoundException("book not exist"));
            book.setAuthors(authorService.getAuthorsFromStringArray(bookDTO.getAuthors()));
            book.setTag(tagService.getTagByString(bookDTO.getTag()));
            return book;
        }
    }

    public void deleteBook(long id) throws BookNotFoundException {
        log.info("delete book with id {}", id);
        try (BookDao bookDao = daoFactory.createBookDao()) {
            bookDao.delete(id);
        }
    }
}