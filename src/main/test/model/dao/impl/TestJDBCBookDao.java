package model.dao.impl;

import model.dao.BookDao;
import model.entity.Author;
import model.entity.Book;
import model.entity.Tag;
import org.junit.Assert;
import org.junit.Test;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

public class TestJDBCBookDao {
    BookDao bookDao = new JDBCDaoFactory().createBookDao();

    @Test
    public void testFindAll() {
        if (bookDao.findAll().size() == 0) {
            Assert.fail();
        }
    }

    @Test
    public void testFindById() {//todo fix authors
        List<Book> books = bookDao.findAll();
        for (Book book : books) {
            Assert.assertEquals(book,
                    bookDao.findById(book.getBookId()).orElseThrow(() -> new RuntimeException("oops")));
        }
    }

    @Test
    public void testGetBooksByFilter() {
        List<Book> books = bookDao.findAll();
        for (Book book : books) {
            System.out.println(book);
            List<Book> booksByFilter = bookDao.getBooksByFilter("%" + book.getName() + "%",
                    book.getAuthors().stream().map(Author::getName).toArray(String[]::new),
                    new String[]{book.getTag().getName()});
            System.out.println(booksByFilter);
            Assert.assertTrue(booksByFilter.contains(book));
        }
    }

    @Test
    public void testGetBooksByFilterUa() {
        List<Book> books = bookDao.findAll();
        for (Book book : books) {
            List<Book> booksByFilter = bookDao.getBooksByFilterUa("%" + book.getNameUa() + "%",
                    book.getAuthors().stream().map(Author::getNameUa).toArray(String[]::new),
                    new String[]{book.getTag().getNameUa()});
            Assert.assertTrue(booksByFilter.contains(book));
        }
    }

    @Test
    public void testCreate() {
        Book book = Book.Builder.aBook()
                .name("test")
                .nameUa("test_ua")
                .available(true)
                .authors(new ArrayList<>(List.of(JDBCDaoFactory.getInstance().createAuthorDao().findAll().get(0))))
                .tag(JDBCDaoFactory.getInstance().createTagDao().findAll().get(0))
                .build();
        bookDao.create(book);
    }

    @Test
    public void testDelete() {

    }
}
