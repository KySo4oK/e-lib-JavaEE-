package model.dao.impl;

import model.dao.ShelfDao;
import model.entity.Shelf;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.Optional;

public class TestJDBCShelfDao {
    ShelfDao shelfDao = new JDBCDaoFactory().createShelfDao();

    @Test
    public void testFindAll() {
        if (shelfDao.findAll().size() == 0) {
            Assert.fail();
        }
    }

    @Test
    public void testFindByBookId() {
        List<Shelf> shelves = shelfDao.findAll();
        for (Shelf shelf : shelves) {
            if (shelf.getBook() != null) {
                Assert.assertEquals(Optional.of(shelf), shelfDao.findByBookId(shelf.getBook().getBookId()));
            }
        }
    }

    @Test
    public void testCreate() {
        Shelf shelf = new Shelf();
        int sizeBefore = shelfDao.findAll().size();
        shelfDao.create(shelf);
        Assert.assertEquals(sizeBefore + 1, shelfDao.findAll().size());
    }

    @Test
    public void testDelete() {
        Shelf shelf = shelfDao.findById(shelfDao.findAll()
                .stream()
                .map(Shelf::getShelfId)
                .max(Long::compare).get()).get();
        shelfDao.delete(shelf.getShelfId());
        Assert.assertEquals(shelfDao.findById(shelf.getShelfId()), Optional.empty());
    }

    @Test
    public void testFindById() {
        Shelf shelf = shelfDao.findAll().get(0);
        Assert.assertEquals(shelf.getShelfId(), shelfDao.findById(shelf.getShelfId())
                .orElseThrow(() -> new RuntimeException("oops")).getShelfId());
    }

//    @Test
//    public void testUpdate() {
//        Tag shelf = Tag.Builder.aTag().name("test").nameUa("test_ua").build();
//        shelfDao.create(shelf);
//        shelf = shelfDao.findByNameUa(shelf.getNameUa());
//        shelf.setName("test_another");
//        shelfDao.update(shelf);
//        Assert.assertEquals(shelf.getName(), shelfDao.findByNameUa(shelf.getNameUa()).getName());
//        shelfDao.delete(shelf.getTagId());
//    }
}
