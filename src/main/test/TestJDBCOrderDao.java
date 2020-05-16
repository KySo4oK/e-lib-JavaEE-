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
            Assert.assertTrue(order.isActive());
        }
        orders = orderDao.findAllByActive(false);
        for (Order order : orders) {
            Assert.assertFalse(order.isActive());
        }
    }

    @Test
    public void testFindAllByActiveAndUser_Username() {
        List<Order> orders = orderDao.findAll();
        for (Order order : orders) {
            List<Order> orderList = orderDao
                    .findAllByActiveAndUser_Username(order.isActive(), order.getUser().getUsername());
            Assert.assertTrue(orderList.contains(order));
        }
    }
}
