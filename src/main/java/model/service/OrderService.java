package model.service;

import model.dao.BookDao;
import model.dao.OrderDao;
import model.dao.ShelfDao;
import model.dao.UserDao;
import model.dto.BookDTO;
import model.dto.OrderDTO;
import model.entity.Book;
import model.entity.Order;
import model.entity.Shelf;
import model.exception.BookNotAvailableException;
import model.exception.BookNotFoundException;
import model.exception.OrderNotFoundException;
import model.exception.UsernameNotFoundException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class OrderService {
    private static final int PERIOD_OF_USE = 1;
    private final OrderDao orderDao;
    private final BookDao bookDao;
    private final ShelfDao shelfDao;
    private final UserDao userDao;
    private static final org.apache.logging.log4j.Logger log
            = org.apache.logging.log4j.LogManager.getLogger(OrderService.class);

    public OrderService(OrderDao orderDao, BookDao bookDao, ShelfDao shelfDao, UserDao userDao) {
        this.orderDao = orderDao;
        this.bookDao = bookDao;
        this.shelfDao = shelfDao;
        this.userDao = userDao;
    }

    public void createAndSaveNewOrder(BookDTO bookDTO, String username) {
        orderDao.create(buildNewOrder(bookDTO, username));
    }

    private Order buildNewOrder(BookDTO bookDTO, String username) {
        return Order.Builder.anOrder()
                .user(userDao.findByUsername(username)
                        .orElseThrow(() -> new UsernameNotFoundException("User not exist")))
                .book(bookDao.findById(bookDTO.getId())
                        .orElseThrow(() -> new BookNotFoundException("book not exist")))
                .active(false)
                .endDate(LocalDate.now())
                .startDate(LocalDate.now())
                .build();
    }

    public void permitOrder(OrderDTO orderDTO) {
        log.info("permit order {}", orderDTO);
        orderDao.update(activateAndChangeOrder(orderDTO));// todo normal updating
    }

    private Order activateAndChangeOrder(OrderDTO orderDTO) {
        Order order = orderDao
                .findById(orderDTO.getId())
                .orElseThrow(() -> new OrderNotFoundException("Active order not exist"));
        if (!order.getBook().isAvailable()) throw new BookNotAvailableException("Book not available");
        order.setActive(true);
        order.setStartDate(LocalDate.now());
        order.setEndDate(LocalDate.now().plusMonths(PERIOD_OF_USE));
        order.getBook().setAvailable(false);
        return order;
    }

    public List<OrderDTO> getActiveOrders() {
        return orderDao
                .findAllByActive(true)
                .stream()
                .map(this::buildOrderDTO)
                .collect(Collectors.toList());
    }

    public List<OrderDTO> getPassiveOrders() {
        return orderDao.findAllByActive(false)
                .stream()
                .map(this::buildOrderDTO)
                .collect(Collectors.toList());
    }

//    public List<OrderDTO> getActiveOrdersByUserName(String name) {
//        log.info("active orders by username {}", name);
//        return orderDao.findAllByActiveIsTrueAndUser_Username(name)
//                .stream()
//                .map(this::buildOrderDTO)
//                .collect(Collectors.toList());
//    } todo

    private OrderDTO buildOrderDTO(Order order) {
        return OrderDTO.Builder.anOrderDTO()
                .bookName(getBookNameByLocale(order))
                .id(order.getOrderId())
                .userName(order.getUser().getUsername())
                .endDate(order.getEndDate()
                        .format(getFormatterByLocale()))
                .startDate(order.getStartDate()
                        .format(getFormatterByLocale()))
                .build();
    }

    private String getBookNameByLocale(Order order) {
        return /*LocaleContextHolder.getLocale().equals(Locale.ENGLISH)*/true ?
                order.getBook().getName() : order.getBook().getNameUa();
    }

    private DateTimeFormatter getFormatterByLocale() {
        return DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)
                .withLocale(//LocaleContextHolder.getLocale());
                Locale.ENGLISH);
    }

//    public List<OrderDTO> getPassiveOrdersByUserName(String name) {
//        log.info("passive orders by username {}", name);
//        return orderDao.findAllByActiveIsFalseAndUser_Username(name)
//                .stream()
//                .map(this::buildOrderDTO)
//                .collect(Collectors.toList());
//    }

//    public void returnBook(OrderDTO orderDTO) {
//        log.info("return book {}", orderDTO.getBookName());
//        saveBookAndDeleteOrder(getOrderAndPrepareBookForReturning(orderDTO));
//    }

//    @Transactional
//    void saveBookAndDeleteOrder(Order order) {
//        bookDao.save(order.getBook());
//        orderDao.delete(order);
//    }

    private Order getOrderAndPrepareBookForReturning(OrderDTO orderDTO) {
        Order order = orderDao
                .findById(orderDTO.getId())
                .orElseThrow(() -> new OrderNotFoundException("order not exist"));
        prepareBookForReturning(order.getBook());
        return order;
    }

    private void prepareBookForReturning(Book book) {
        book.setAvailable(true);
        book.setShelf(shelfDao.findByBookId(null).orElse(new Shelf()));
    }
}
