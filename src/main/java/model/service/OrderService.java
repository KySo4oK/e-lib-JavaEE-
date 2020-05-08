package model.service;

import model.dto.BookDTO;
import model.dto.OrderDTO;
import model.entity.Book;
import model.entity.Order;
import model.entity.Shelf;
import model.exception.BookNotAvailableException;
import model.exception.BookNotFoundException;
import model.exception.OrderNotFoundException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class OrderService {
    private static final int PERIOD_OF_USE = 1;
    private final OrderRepository orderRepository;
    private final BookRepository bookRepository;
    private final ShelfRepository shelfRepository;
    private final UserRepository userRepository;

    public OrderService(OrderRepository orderRepository,
                        BookRepository bookRepository,
                        ShelfRepository shelfRepository,
                        UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.bookRepository = bookRepository;
        this.shelfRepository = shelfRepository;
        this.userRepository = userRepository;
    }

    public void createAndSaveNewOrder(BookDTO bookDTO, String username) {
        orderRepository.save(buildNewOrder(bookDTO, username));
    }

    private Order buildNewOrder(BookDTO bookDTO, String username) {
        return Order.builder()
                .user(userRepository.findByUsername(username)
                        .orElseThrow(() -> new UsernameNotFoundException("User not exist")))
                .book(bookRepository.findById(bookDTO.getId())
                        .orElseThrow(() -> new BookNotFoundException("book not exist")))
                .active(false)
                .endDate(LocalDate.now())
                .startDate(LocalDate.now())
                .build();
    }

    public void permitOrder(OrderDTO orderDTO) {
        log.info("permit order {}", orderDTO);
        orderRepository.save(activateAndChangeOrder(orderDTO));
    }

    private Order activateAndChangeOrder(OrderDTO orderDTO) {
        Order order = orderRepository
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
        return orderRepository
                .findAllByActiveIsTrue()
                .stream()
                .map(this::buildOrderDTO)
                .collect(Collectors.toList());
    }

    public List<OrderDTO> getPassiveOrders() {
        return orderRepository.findAllByActiveIsFalse()
                .stream()
                .map(this::buildOrderDTO)
                .collect(Collectors.toList());
    }

    public List<OrderDTO> getActiveOrdersByUserName(String name) {
        log.info("active orders by username {}", name);
        return orderRepository.findAllByActiveIsTrueAndUser_Username(name)
                .stream()
                .map(this::buildOrderDTO)
                .collect(Collectors.toList());
    }

    private OrderDTO buildOrderDTO(Order order) {
        return OrderDTO.builder()
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
        return LocaleContextHolder.getLocale().equals(Locale.ENGLISH) ?
                order.getBook().getName() : order.getBook().getNameUa();
    }

    private DateTimeFormatter getFormatterByLocale() {
        return DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)
                .withLocale(LocaleContextHolder.getLocale());
    }

    public List<OrderDTO> getPassiveOrdersByUserName(String name) {
        log.info("passive orders by username {}", name);
        return orderRepository.findAllByActiveIsFalseAndUser_Username(name)
                .stream()
                .map(this::buildOrderDTO)
                .collect(Collectors.toList());
    }

    public void returnBook(OrderDTO orderDTO) {
        log.info("return book {}", orderDTO.getBookName());
        saveBookAndDeleteOrder(getOrderAndPrepareBookForReturning(orderDTO));
    }

    @Transactional
    void saveBookAndDeleteOrder(Order order) {
        bookRepository.save(order.getBook());
        orderRepository.delete(order);
    }

    private Order getOrderAndPrepareBookForReturning(OrderDTO orderDTO) {
        Order order = orderRepository
                .findById(orderDTO.getId())
                .orElseThrow(() -> new OrderNotFoundException("order not exist"));
        prepareBookForReturning(order.getBook());
        return order;
    }

    private void prepareBookForReturning(Book book) {
        book.setAvailable(true);
        book.setShelf(shelfRepository.findByBookIsNull().orElse(new Shelf()));
    }
}
