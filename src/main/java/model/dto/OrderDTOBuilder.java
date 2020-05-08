package model.dto;

public class OrderDTOBuilder {
    private Long id;
    private String bookName;
    private String userName;
    private String startDate;
    private String endDate;

    public OrderDTOBuilder setId(Long id) {
        this.id = id;
        return this;
    }

    public OrderDTOBuilder setBookName(String bookName) {
        this.bookName = bookName;
        return this;
    }

    public OrderDTOBuilder setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public OrderDTOBuilder setStartDate(String startDate) {
        this.startDate = startDate;
        return this;
    }

    public OrderDTOBuilder setEndDate(String endDate) {
        this.endDate = endDate;
        return this;
    }

    public OrderDTO createOrderDTO() {
        return new OrderDTO(id, bookName, userName, startDate, endDate);
    }
}