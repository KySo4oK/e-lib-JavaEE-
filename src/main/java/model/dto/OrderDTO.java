package model.dto;

public class OrderDTO {
    private Long id;
    private String bookName;
    private String userName;
    private String startDate;

    public OrderDTO() {
    }

    public OrderDTO(Long id, String bookName, String userName, String startDate, String endDate) {
        this.id = id;
        this.bookName = bookName;
        this.userName = userName;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "OrderDTO{" +
                "id=" + id +
                ", bookName='" + bookName + '\'' +
                ", userName='" + userName + '\'' +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                '}';
    }

    private String endDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
