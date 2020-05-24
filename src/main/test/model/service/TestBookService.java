package model.service;

import model.dto.BookDTO;
import org.junit.Test;

public class TestBookService {
    @Test
    public void testSaveNewBookFromClient() {
        BookService bookService = ServiceFactory.getInstance().createBookService();
        bookService.saveNewBookFromClient(BookDTO.Builder.aBookDTO()
                .authors(new String[]{"Fyodor Dostoyevsky"})
                .tag("drama")
                .name("The Idiot")
                .nameUa("Ідіот")
                .build());
    }
}
