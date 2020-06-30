package model.service;

import com.atomikos.icatch.jta.UserTransactionImp;
import model.dao.*;
import model.dto.BookDTO;
import model.dto.OrderDTO;
import model.entity.Book;
import model.entity.Order;
import model.entity.Shelf;
import model.exception.CustomException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.transaction.SystemException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class OrderService {
    private final DaoFactory daoFactory = DaoFactory.getInstance();
    private static final int PERIOD_OF_USE = 1;
    private final static Logger log = LogManager.getLogger(OrderService.class);

    public void createAndSaveNewOrder(BookDTO bookDTO, String username) {
        try (OrderDao orderDao = daoFactory.createOrderDao()) {
            orderDao.create(buildNewOrder(bookDTO, username));
        }
    }

    private Order buildNewOrder(BookDTO bookDTO, String username) {
        try (BookDao bookDao = daoFactory.createBookDao();
             UserDao userDao = daoFactory.createUserDao()) {
            return Order.Builder.anOrder()
                    .user(userDao.findByUsername(username)
                            .orElseThrow(() -> new CustomException("user.not.found")))
                    .book(bookDao.findById(bookDTO.getId())
                            .orElseThrow(() -> new CustomException("book.not.found")))
                    .active(false)
                    .endDate(LocalDate.now())
                    .startDate(LocalDate.now())
                    .build();
        }
    }

    public void permitOrder(OrderDTO orderDTO) {
        log.info("permit order - " + orderDTO);
        try (OrderDao orderDao = daoFactory.createOrderDao()) {
            orderDao.update(activateAndChangeOrder(orderDTO));
        }
    }

    private Order activateAndChangeOrder(OrderDTO orderDTO) {
        try (OrderDao orderDao = daoFactory.createOrderDao()) {
            Order order = orderDao
                    .findById(orderDTO.getId())
                    .orElseThrow(() -> new CustomException("order.not.found"));
            if (!order.getBook().isAvailable()) throw new CustomException("book.not.available");
            order.setActive(true);
            order.setStartDate(LocalDate.now());
            order.setEndDate(LocalDate.now().plusMonths(PERIOD_OF_USE));
            order.getBook().setAvailable(false);
            return order;
        }
    }

    public List<OrderDTO> getActiveOrders(Locale locale) {
        try (OrderDao orderDao = daoFactory.createOrderDao()) {
            return orderDao
                    .findAllByActive(true)
                    .stream()
                    .map(order -> buildOrderDTO(order, locale))
                    .collect(Collectors.toList());
        }
    }

    public List<OrderDTO> getPassiveOrders(Locale locale) {
        try (OrderDao orderDao = daoFactory.createOrderDao()) {
            return orderDao.findAllByActive(false)
                    .stream()
                    .map(order -> buildOrderDTO(order, locale))
                    .collect(Collectors.toList());
        }
    }

    public List<OrderDTO> getActiveOrdersByUserName(String name, Locale locale) {
        log.info("active orders by username - " + name);
        try (OrderDao orderDao = daoFactory.createOrderDao()) {
            return orderDao.findAllByActiveAndUser_Username(true, name)
                    .stream()
                    .map(order -> buildOrderDTO(order, locale))
                    .collect(Collectors.toList());
        }
    }

    private OrderDTO buildOrderDTO(Order order, Locale locale) {
        return OrderDTO.Builder.anOrderDTO()
                .bookName(getBookNameByLocale(order, locale))
                .id(order.getOrderId())
                .userName(order.getUser().getUsername())
                .endDate(order.getEndDate()
                        .format(getFormatterByLocale(locale)))
                .startDate(order.getStartDate()
                        .format(getFormatterByLocale(locale)))
                .build();
    }

    private String getBookNameByLocale(Order order, Locale locale) {
        return locale.equals(Locale.ENGLISH) ?
                order.getBook().getName() : order.getBook().getNameUa();
    }

    private DateTimeFormatter getFormatterByLocale(Locale locale) {
        return DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)
                .withLocale(locale);
    }

    public List<OrderDTO> getPassiveOrdersByUserName(String name, Locale locale) {
        log.info("passive orders by username - " + name);
        try (OrderDao orderDao = daoFactory.createOrderDao()) {
            return orderDao.findAllByActiveAndUser_Username(false, name)
                    .stream()
                    .map(order -> buildOrderDTO(order, locale))
                    .collect(Collectors.toList());
        }
    }

    public void returnBook(OrderDTO orderDTO) {
        log.info("return book - " + orderDTO.getBookName());
        saveBookAndDeleteOrder(getOrderAndPrepareBookForReturning(orderDTO));
    }

    void saveBookAndDeleteOrder(Order order) {
        UserTransactionImp utx = new UserTransactionImp();
        try (OrderDao orderDao = daoFactory.createOrderDao();
             BookDao bookDao = daoFactory.createBookDao()) {
            utx.begin();
            bookDao.update(order.getBook());
            orderDao.delete(order.getOrderId());
            try {
                utx.commit();
            } catch (Exception e) {
                try {
                    utx.rollback();
                } catch (SystemException systemException) {
                    log.fatal("cannot perform transaction {}", systemException.getMessage());
                }
            }

        } catch (Exception e) {
            log.error("cannot perform transaction {}", e.getMessage());
            throw new CustomException("order.not.found");
        }
    }

    private Order getOrderAndPrepareBookForReturning(OrderDTO orderDTO) {
        try (OrderDao orderDao = daoFactory.createOrderDao()) {
            Order order = orderDao
                    .findById(orderDTO.getId())
                    .orElseThrow(() -> new CustomException("order.not.found"));
            prepareBookForReturning(order.getBook());
            return order;
        }
    }

    private void prepareBookForReturning(Book book) {
        book.setAvailable(true);
        try (ShelfDao shelfDao = daoFactory.createShelfDao()) {
            book.setShelf(shelfDao.findByBookId(null).orElse(new Shelf()));
        }
    }
}