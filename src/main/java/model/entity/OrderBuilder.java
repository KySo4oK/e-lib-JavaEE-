package model.entity;

import java.time.LocalDate;

public class OrderBuilder {
    private Long orderId;
    private User user;
    private Book book;
    private boolean active;
    private LocalDate startDate;
    private LocalDate endDate;

    public OrderBuilder orderId(Long orderId) {
        this.orderId = orderId;
        return this;
    }

    public OrderBuilder user(User user) {
        this.user = user;
        return this;
    }

    public OrderBuilder book(Book book) {
        this.book = book;
        return this;
    }

    public OrderBuilder active(boolean active) {
        this.active = active;
        return this;
    }

    public OrderBuilder startDate(LocalDate startDate) {
        this.startDate = startDate;
        return this;
    }

    public OrderBuilder endDate(LocalDate endDate) {
        this.endDate = endDate;
        return this;
    }

    public Order createOrder() {
        return new Order(orderId, user, book, active, startDate, endDate);
    }
}