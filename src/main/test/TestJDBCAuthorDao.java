import model.dao.AuthorDao;
import model.dao.impl.JDBCDaoFactory;
import model.entity.Author;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class TestJDBCAuthorDao {
    AuthorDao authorDao = new JDBCDaoFactory().createAuthorDao();

    @Test
    public void testFindAll() {
        if (authorDao.findAll().size() == 0) {
            Assert.fail();
        }
    }

    @Test
    public void testFindByName() {
        List<Author> authors = authorDao.findAll();
        for (Author author : authors) {
            Assert.assertEquals(authorDao.findByName(author.getName()).getName(), author.getName());
        }
    }

    @Test
    public void testFindByNameUa() {
        List<Author> authors = authorDao.findAll();
        for (Author author : authors) {
            Assert.assertEquals(authorDao.findByNameUa(author.getNameUa()).getNameUa(), author.getNameUa());
        }
    }

    @Test
    public void testCreate() {
        Author author = Author.Builder.anAuthor().name("test").nameUa("test_ua").build();
        authorDao.create(author);
        Assert.assertEquals(author.getName(), authorDao.findByName(author.getName()).getName());
    }

    @Test
    public void testDelete() {
        Author author = Author.Builder.anAuthor().name("test").nameUa("test_ua").build();
        authorDao.delete(authorDao.findByName(author.getName()).getAuthorId().intValue());
        Assert.assertNull(authorDao.findByName(author.getName()));
    }

    @Test
    public void testFindById() {
        Author author = authorDao.findAll().get(0);
        Assert.assertEquals(author.getName(), authorDao.findById(author.getAuthorId().intValue()).getName());
    }
    @Test
    public void testUpdate(){
        Author author = Author.Builder.anAuthor().name("test").nameUa("test_ua").build();
        authorDao.create(author);
        author = authorDao.findByNameUa(author.getNameUa());
        author.setName("test_another");
        authorDao.update(author);
        Assert.assertEquals(author.getName(), authorDao.findByNameUa(author.getNameUa()).getName());
        authorDao.delete(author.getAuthorId().intValue());
    }
}
