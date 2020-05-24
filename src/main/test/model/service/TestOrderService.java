package model.service;

import model.dto.OrderDTO;
import org.junit.Assert;
import org.junit.Test;

public class TestOrderService {
    private static final OrderService orderService = ServiceFactory.getInstance().createOrderService();

    @Test
    public void testReturnBook() {
        try {
            orderService.returnBook(OrderDTO.Builder.anOrderDTO()
                    .bookName("White nights")
                    .userName("testuser")
                    .id(47L)
                    .build());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }
    }
}
