package model.service;

import model.dto.BookDTO;
import model.entity.Pageable;
import org.junit.Assert;
import org.junit.Test;

import java.util.Locale;

public class TestBookService {

    private final BookService bookService = ServiceFactory.getInstance().createBookService();

    @Test
    public void testSaveNewBookFromClient() {
        bookService.saveNewBookFromClient(BookDTO.Builder.aBookDTO()
                .authors(new String[]{"Fyodor Dostoyevsky"})
                .tag("drama")
                .name("The Idiot")
                .nameUa("Ідіот")
                .build(), Locale.ENGLISH);
    }

    @Test
    public void testDeleteBook() {
        try {
            bookService.deleteBook(58L);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    @Test
    public void testAvailableBooks() {
        System.out.println(bookService.getAvailableBooks(Pageable.Builder.aPageable()
                        .page(1)
                        .number(3)
                        .build(),
                new Locale("ua")));
    }

}
