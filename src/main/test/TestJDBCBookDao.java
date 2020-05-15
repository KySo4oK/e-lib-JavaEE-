import model.dao.BookDao;
import model.dao.impl.JDBCDaoFactory;
import model.entity.Book;
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
        for (Book book: books) {
            Assert.assertEquals(book,
                    bookDao.findById(book.getBookId().intValue()).orElseThrow(()-> new RuntimeException("oops")));
        }
    }
}
