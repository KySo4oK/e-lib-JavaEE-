package model.entity;

import java.time.LocalDate;

@Builder
public class Order {
    private Long orderId;
    private User user;
    private Book book;
    private boolean active;
    private LocalDate startDate = LocalDate.now();

    public Order() {
    }

    private LocalDate endDate = LocalDate.now();

    public Order(Long orderId, User user, Book book, boolean active, LocalDate startDate, LocalDate endDate) {
        this.orderId = orderId;
        this.user = user;
        this.book = book;
        this.active = active;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
}
