package model.service;

import model.dao.BookDao;
import model.dao.DaoFactory;
import model.dao.ShelfDao;
import model.dto.BookDTO;
import model.dto.FilterDTO;
import model.entity.*;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.ArgumentMatchers;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

public class BookServiceTest {
    private final DaoFactory daoFactory = Mockito.mock(DaoFactory.class);
    private final BookDao bookDao = Mockito.mock(BookDao.class);
    private final TagService tagService = Mockito.mock(TagService.class);
    private final AuthorService authorService = Mockito.mock(AuthorService.class);
    private final BookService bookService = new BookService(daoFactory, tagService, authorService);
    private final Book book = Book.Builder.aBook()
            .bookId(1L)
            .name("eng")
            .nameUa("ua")
            .authors(List.of())
            .tag(Tag.Builder.aTag().name("tag").nameUa("tag-ua").build())
            .available(true)
            .build();
    private final BookDTO bookDTO = BookDTO.Builder.aBookDTO()
            .name(book.getName())
            .id(book.getBookId())
            .tag(book.getTag().getName())
            .authors(new String[]{})
            .build();
    private final Pageable pageable = Pageable.Builder.aPageable()
            .page(0)
            .number(10)
            .build();

    @Test
    public void getAvailableBooks() {
        Mockito.when(daoFactory.createBookDao()).thenReturn(bookDao);
        Mockito.when(bookDao.findAllByAvailableIsTrue(pageable))
                .thenReturn(List.of());
        Assert.assertTrue(bookService.getAvailableBooks(pageable, Locale.ENGLISH).isEmpty());
        Mockito.when(bookDao.findAllByAvailableIsTrue(pageable))
                .thenReturn(List.of(book));
        Assert.assertEquals(List.of(bookDTO), bookService.getAvailableBooks(pageable, Locale.ENGLISH));
        bookDTO.setName(book.getNameUa());
        bookDTO.setTag(book.getTag().getNameUa());
        Assert.assertEquals(List.of(bookDTO), bookService.getAvailableBooks(pageable, new Locale("ua")));
    }

    @Test
    public void getAvailableBooksByFilter() {
        Mockito.when(daoFactory.createBookDao()).thenReturn(bookDao);
        FilterDTO filterDTO = FilterDTO.Builder.aFilterDTO()
                .name("")
                .authors(new String[]{})
                .tags(new String[]{})
                .build();
        Mockito.when(bookDao.getBooksByFilter(
                filterDTO.getName(),
                filterDTO.getAuthors(),
                filterDTO.getTags(), pageable))
                .thenReturn(List.of());
        Assert.assertTrue(bookService.getAvailableBooksByFilter(filterDTO, pageable, Locale.ENGLISH).isEmpty());
        Mockito.when(bookDao.getBooksByFilter(
                filterDTO.getName(),
                filterDTO.getAuthors(),
                filterDTO.getTags(), pageable))
                .thenReturn(List.of(book));
        Assert.assertEquals(List.of(bookDTO),
                bookService.getAvailableBooksByFilter(filterDTO, pageable, Locale.ENGLISH));
        bookDTO.setName(book.getNameUa());
        Mockito.when(bookDao.getBooksByFilterUa(
                filterDTO.getName(),
                filterDTO.getAuthors(),
                filterDTO.getTags(), pageable))
                .thenReturn(List.of(book));
        bookDTO.setTag(book.getTag().getNameUa());
        Assert.assertEquals(List.of(bookDTO),
                bookService.getAvailableBooksByFilter(filterDTO, pageable, new Locale("ua")));
    }

    @Test
    public void saveNewBookFromClient() {
        Shelf shelf = new Shelf();
        ShelfDao shelfDao = Mockito.mock(ShelfDao.class);
        Mockito.when(daoFactory.createBookDao()).thenReturn(bookDao);
        Mockito.when(daoFactory.createShelfDao()).thenReturn(shelfDao);
        Mockito.when(shelfDao.findByBookId(null))
                .thenReturn(Optional.of(shelf));
        Mockito.when(tagService.getTagByString(book.getTag().getName(), Locale.ENGLISH))
                .thenReturn(book.getTag());
        bookDTO.setName(book.getName());
        bookDTO.setNameUa(book.getNameUa());
        Mockito.when(bookDao.findByName(book.getName())).thenReturn(Optional.of(book));
        bookDTO.setTag(book.getTag().getName());
        bookDTO.setId(book.getBookId());
        bookService.saveNewBookFromClient(bookDTO, Locale.ENGLISH);
        Assert.assertEquals(book, shelf.getBook());
        book.setShelf(shelf);
        book.setBookId(null);
        Mockito.verify(bookDao, Mockito.times(1))
                .create(ArgumentMatchers.eq(book));
        Mockito.verify(shelfDao, Mockito.times(1))
                .update(ArgumentMatchers.eq(shelf));
    }

    @Test
    public void editBookAndSave() {
        Mockito.when(daoFactory.createBookDao()).thenReturn(bookDao);
        bookDTO.setId(1L);
        book.setBookId(bookDTO.getId());
        Mockito.when(bookDao.findById(bookDTO.getId()))
                .thenReturn(Optional.of(book));
        bookService.editBookAndSave(bookDTO);
        Mockito.verify(bookDao, Mockito.times(1))
                .update(ArgumentMatchers.eq(book));
    }

    @Test
    public void deleteBook() {
        Mockito.when(daoFactory.createBookDao()).thenReturn(bookDao);
        long id = 10L;
        bookService.deleteBook(id);
        Mockito.verify(bookDao, Mockito.times(1))
                .delete(id);
    }
}