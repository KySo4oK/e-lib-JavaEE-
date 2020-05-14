import model.dao.OrderDao;
import model.dao.impl.JDBCDaoFactory;
import model.entity.Order;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class TestJDBCOrderDao {
    OrderDao orderDao = new JDBCDaoFactory().createOrderDao();

    @Test
    public void testFindAll() {
        if (orderDao.findAll().size() == 0) {
            Assert.fail();
        }
    }

    @Test
    public void testFindAllByActive() {
        List<Order> orders = orderDao.findAllByActive(true);
        for (Order order : orders) {
            System.out.println(order);
            Assert.assertTrue(order.isActive());
        }
        orders = orderDao.findAllByActive(false);
        for (Order order : orders) {
            System.out.println(order);
            Assert.assertFalse(order.isActive());
        }
    }
//
//    @Test
//    public void testFindByNameUa() {
//        List<Order> orders = orderDao.findAll();
//        for (Order order : orders) {
//            Assert.assertEquals(orderDao.findByNameUa(order.getNameUa()).getNameUa(), order.getNameUa());
//        }
//    }
//
//    @Test
//    public void testCreate() {
//        Order order = Order.Builder.anAuthor().name("test").nameUa("test_ua").build();
//        orderDao.create(order);
//        Assert.assertEquals(order.getName(), orderDao.findByName(order.getName()).getName());
//    }
//
//    @Test
//    public void testDelete() {
//        Author author = Author.Builder.anAuthor().name("test").nameUa("test_ua").build();
//        orderDao.delete(orderDao.findByName(author.getName()).getAuthorId().intValue());
//        Assert.assertNull(orderDao.findByName(author.getName()));
//    }
//
//    @Test
//    public void testFindById() {
//        Author author = orderDao.findAll().get(0);
//        Assert.assertEquals(author.getName(), orderDao.findById(author.getAuthorId().intValue()).getName());
//    }
//    @Test
//    public void testUpdate(){
//        Author author = Author.Builder.anAuthor().name("test").nameUa("test_ua").build();
//        orderDao.create(author);
//        author = orderDao.findByNameUa(author.getNameUa());
//        author.setName("test_another");
//        orderDao.update(author);
//        Assert.assertEquals(author.getName(), orderDao.findByNameUa(author.getNameUa()).getName());
//        orderDao.delete(author.getAuthorId().intValue());
//    }
}
