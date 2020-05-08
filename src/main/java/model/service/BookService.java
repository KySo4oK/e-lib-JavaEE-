package model.service;

import model.dto.BookDTO;
import model.dto.FilterDTO;
import model.entity.Author;
import model.entity.Book;
import model.entity.Shelf;
import model.entity.Tag;
import model.exception.BookNotFoundException;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class BookService {
    private final BookRepository bookRepository;
    private final ShelfRepository shelfRepository;
    private final TagService tagService;
    private final AuthorService authorService;

    public BookService(BookRepository bookRepository,
                       ShelfRepository shelfRepository,
                       TagService tagService,
                       AuthorService authorService) {
        this.bookRepository = bookRepository;
        this.shelfRepository = shelfRepository;
        this.tagService = tagService;
        this.authorService = authorService;
    }

    public List<BookDTO> getAvailableBooks(Pageable pageable) {
        return bookRepository.findAllByAvailableIsTrue(pageable)
                .stream()
                .map(this::buildBookDTO)
                .collect(Collectors.toList());
    }

    private BookDTO buildBookDTO(Book book) {
        return BookDTO.Builder.aBookDTO()
                .id(book.getBookId())
                .authors(getArrayOfAuthors(book))
                .tags(getArrayOfTags(book))
                .name(getBookNameByLocale(book))
                .build();
    }

    private String getBookNameByLocale(Book book) {
        return LocaleContextHolder.getLocale().equals(Locale.ENGLISH) ? book.getName() : book.getNameUa();
    }

    private String[] getArrayOfTags(Book book) {
        return book.getTags()
                .stream()
                .map(this::getTagsByLocale)
                .toArray(String[]::new);
    }

    private String getTagsByLocale(Tag tag) {
        return LocaleContextHolder.getLocale().equals(Locale.ENGLISH) ?
                tag.getName() : tag.getNameUa();
    }

    private String[] getArrayOfAuthors(Book book) {
        return book.getAuthors()
                .stream()
                .map(this::getAuthorsByLocale)
                .toArray(String[]::new);
    }

    private String getAuthorsByLocale(Author author) {
        return LocaleContextHolder.getLocale().equals(Locale.ENGLISH) ?
                author.getName() : author.getNameUa();
    }

    //@Transactional
    public void saveNewBookFromClient(BookDTO bookDTO) {
        log.info("create book {}", bookDTO);
        Shelf shelf = shelfRepository.findByBookIsNull().orElse(new Shelf());
        Book book = BuildBookFromClient(bookDTO, shelf);
        bookRepository.save(book);
        shelf.setBook(book);
        shelfRepository.save(shelf);
    }

    private Book BuildBookFromClient(BookDTO bookDTO, Shelf shelf) {
        return Book.Builder.aBook()
                .name(bookDTO.getName())
                .nameUa(bookDTO.getNameUa())
                .shelf(shelf)
                .authors(authorService.getAuthorsFromStringArray(bookDTO.getAuthors()))
                .tags(tagService.getTagsByStringArray(bookDTO.getTags()))
                .available(true)
                .build();
    }

    public List<BookDTO> getAvailableBooksByFilter(FilterDTO filterDTO, Pageable pageable) {
        return getBooksByFilter(filterDTO, pageable)
                .stream()
                .map(this::buildBookDTO)
                .collect(Collectors.toList());

    }

    private List<Book> getBooksByFilter(FilterDTO filterDTO, Pageable pageable) {
        return LocaleContextHolder.getLocale().equals(Locale.ENGLISH) ?
                bookRepository.getBooksByFilter(
                        filterDTO.getName(),
                        filterDTO.getAuthors(),
                        filterDTO.getTags(), pageable) :
                bookRepository.getBooksByFilterUa(
                        filterDTO.getName(),
                        filterDTO.getAuthors(),
                        filterDTO.getTags(), pageable);
    }

    public void editBookAndSave(BookDTO bookDTO) throws BookNotFoundException {
        log.info("save book {}", bookDTO);
        bookRepository.save(getEditedBook(bookDTO));
    }

    private Book getEditedBook(BookDTO bookDTO) {
        Book book = bookRepository
                .findById(bookDTO.getId())
                .orElseThrow(() -> new BookNotFoundException("book not exist"));
        book.setAuthors(authorService.getAuthorsFromStringArray(bookDTO.getAuthors()));
        book.setTags(tagService.getTagsByStringArray(bookDTO.getTags()));
        return book;
    }

    public void deleteBook(long id) throws BookNotFoundException {
        log.info("delete book with id {}", id);
        bookRepository.deleteById(id);
    }
}