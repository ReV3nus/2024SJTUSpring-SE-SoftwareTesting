package com.example.mybookstore_backend.entity;

import com.example.mybookstore_backend.entity.OrderItem;
import com.example.mybookstore_backend.entity.OrderRecord;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OrderItemTest {

    @Test
    public void testOrderItemConstructor() {
        // Arrange
        Integer bookId = 1;
        String bookName = "Book Title";
        String author = "Author";
        String type = "Type";
        Double price = 10.0;
        Integer count = 1;
        String username = "testUser";

        // Act
        OrderItem orderItem = new OrderItem(bookId, bookName, author, type, price, count, username);

        // Assert
        assertEquals(bookId, orderItem.getBookid());
        assertEquals(bookName, orderItem.getBookname());
        assertEquals(author, orderItem.getAuthor());
        assertEquals(type, orderItem.getType());
        assertEquals(price, orderItem.getPrice());
        assertEquals(count, orderItem.getCount());
        assertEquals(username, orderItem.getUsername());
    }

    @Test
    public void testTestOrderRecordMethod() {
        // Arrange
        OrderRecord orderRecord = new OrderRecord();
        orderRecord.setTime("2022-01-01 12:00:00");
        OrderItem orderItem = new OrderItem();
        orderItem.setOrderRecord(orderRecord);

        // Act
        String time = orderItem.__TestOrderRecord();

        // Assert
        assertEquals("2022-01-01 12:00:00", time);
    }
}
