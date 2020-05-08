package model.dto;

public class OrderDTOBuilder {
    private Long id;
    private String bookName;
    private String userName;
    private String startDate;
    private String endDate;

    public OrderDTOBuilder id(Long id) {
        this.id = id;
        return this;
    }

    public OrderDTOBuilder bookName(String bookName) {
        this.bookName = bookName;
        return this;
    }

    public OrderDTOBuilder userName(String userName) {
        this.userName = userName;
        return this;
    }

    public OrderDTOBuilder startDate(String startDate) {
        this.startDate = startDate;
        return this;
    }

    public OrderDTOBuilder endDate(String endDate) {
        this.endDate = endDate;
        return this;
    }

    public OrderDTO createOrderDTO() {
        return new OrderDTO(id, bookName, userName, startDate, endDate);
    }
}