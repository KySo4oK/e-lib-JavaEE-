package model.dao.impl;

import model.dao.BookDao;
import model.entity.Author;
import model.entity.Book;
import model.entity.Tag;
import org.junit.Assert;
import org.junit.Test;

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
    public void testFindById() {
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
            List<Book> booksByFilter = bookDao.getBooksByFilter("%" + book.getName() + "%",
                    book.getAuthors().stream().map(Author::getName).toArray(String[]::new),
                    book.getTags().stream().map(Tag::getName).toArray(String[]::new));
            Assert.assertTrue(booksByFilter.contains(book));
        }
    }

    @Test
    public void testGetBooksByFilterUa() {
        List<Book> books = bookDao.findAll();
        for (Book book : books) {
            List<Book> booksByFilter = bookDao.getBooksByFilterUa("%" + book.getNameUa() + "%",
                    book.getAuthors().stream().map(Author::getNameUa).toArray(String[]::new),
                    book.getTags().stream().map(Tag::getNameUa).toArray(String[]::new));
            Assert.assertTrue(booksByFilter.contains(book));
        }
    }
}