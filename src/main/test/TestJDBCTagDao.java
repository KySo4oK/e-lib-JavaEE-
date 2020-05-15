import model.dao.TagDao;
import model.dao.impl.JDBCDaoFactory;
import model.entity.Tag;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class TestJDBCTagDao {
    TagDao tagDao = new JDBCDaoFactory().createTagDao();

    @Test
    public void testFindAll() {
        if (tagDao.findAll().size() == 0) {
            Assert.fail();
        }
    }

    @Test
    public void testFindByName() {
        List<Tag> tags = tagDao.findAll();
        for (Tag tag : tags) {
            Assert.assertEquals(tagDao.findByName(tag.getName())
                    .orElseThrow(()-> new RuntimeException("oops")).getName(), tag.getName());
        }
    }

    @Test
    public void testFindByNameUa() {
        List<Tag> tags = tagDao.findAll();
        for (Tag tag : tags) {
            Assert.assertEquals(tagDao.findByNameUa(tag.getNameUa())
                    .orElseThrow(()-> new RuntimeException("oops")).getNameUa(), tag.getNameUa());
        }
    }

    @Test
    public void testCreate() {
        Tag tag = Tag.Builder.aTag().name("test").nameUa("test_ua").build();
        tagDao.create(tag);
        Assert.assertEquals(tag.getName(), tagDao.findByName(tag.getName())
                .orElseThrow(()-> new RuntimeException("oops")).getName());
    }

    @Test
    public void testDelete() {
        Tag tag = Tag.Builder.aTag().name("test").nameUa("test_ua").build();
        tagDao.delete(tagDao.findByName(tag.getName())
                .orElseThrow(()-> new RuntimeException("oops")).getTagId().intValue());
        Assert.assertNull(tagDao.findByName(tag.getName()));
    }

    @Test
    public void testFindById() {
        Tag tag = tagDao.findAll().get(0);
        Assert.assertEquals(tag.getName(), tagDao.findById(tag.getTagId().intValue())
                .orElseThrow(()-> new RuntimeException("oops")).getName());
    }

    @Test
    public void testUpdate() {
        Tag tag = Tag.Builder.aTag().name("test").nameUa("test_ua").build();
        tagDao.create(tag);
        tag = tagDao.findByNameUa(tag.getNameUa()).orElseThrow(()-> new RuntimeException("oops"));
        tag.setName("test_another");
        tagDao.update(tag);
        Assert.assertEquals(tag.getName(), tagDao.findByNameUa(tag.getNameUa())
                .orElseThrow(()-> new RuntimeException("oops")).getName());
        tagDao.delete(tag.getTagId().intValue());
    }
}
