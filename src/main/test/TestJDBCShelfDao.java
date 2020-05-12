import model.dao.ShelfDao;
import model.dao.impl.JDBCDaoFactory;
import model.entity.Shelf;
import org.junit.Assert;
import org.junit.Test;

public class TestJDBCShelfDao {
    ShelfDao shelfDao = new JDBCDaoFactory().createShelfDao();

    @Test
    public void testFindAll() {
        if (shelfDao.findAll().size() == 0) {
            Assert.fail();
        }
    }//todo add test for findByBookId

//    @Test
//    public void testCreate() {
//        Shelf shelf = new Shelf();
//        int sizeBefore = shelfDao.findAll().size();
//        shelfDao.create(shelf);
//        Assert.assertEquals(sizeBefore + 1, shelfDao.findAll().size());
//    }
//
//    @Test
//    public void testDelete() {
//        Tag tag = Tag.Builder.aTag().name("test").nameUa("test_ua").build();
//        shelfDao.delete(shelfDao.findByName(tag.getName()).getTagId().intValue());
//        Assert.assertNull(shelfDao.findByName(tag.getName()));
//    }

    @Test
    public void testFindById() {
        Shelf shelf = shelfDao.findAll().get(0);
        Assert.assertEquals(shelf.getShelfId(), shelfDao.findById(shelf.getShelfId().intValue()).getShelfId());
    }

//    @Test
//    public void testUpdate() {
//        Tag shelf = Tag.Builder.aTag().name("test").nameUa("test_ua").build();
//        shelfDao.create(shelf);
//        shelf = shelfDao.findByNameUa(shelf.getNameUa());
//        shelf.setName("test_another");
//        shelfDao.update(shelf);
//        Assert.assertEquals(shelf.getName(), shelfDao.findByNameUa(shelf.getNameUa()).getName());
//        shelfDao.delete(shelf.getTagId().intValue());
//    }
}
