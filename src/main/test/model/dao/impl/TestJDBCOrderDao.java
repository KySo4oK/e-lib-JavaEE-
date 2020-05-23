package model.dao.impl;

import model.dao.DaoFactory;
import model.dao.OrderDao;
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

    @Test
    public void testCreate() {
        orderDao.create(Order.Builder.anOrder().active(true).book(
                DaoFactory.getInstance().createBookDao().findAll().get(0))
                .user(DaoFactory.getInstance().createUserDao().findAll().get(0)).build());
    }

    @Test
    public void testUpdate() {
        Order order = orderDao.findAll().get(0);
        boolean before = order.isActive();
        order.setActive(!before);
        orderDao.update(order);
        boolean after = orderDao.findById(order.getOrderId()).get().isActive();
        if (before == after) {
            Assert.fail();
        }
    }

    @Test
    public void testDelete() {
        try {
            orderDao.delete(orderDao.findAll().stream().map(Order::getOrderId).max(Long::compareTo).get());
        } catch (Exception e) {
            Assert.fail();
        }
    }
}
