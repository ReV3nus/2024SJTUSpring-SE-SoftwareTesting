package com.example.mybookstore_backend.entity;

import com.example.mybookstore_backend.entity.OrderRecord;
import com.example.mybookstore_backend.entity.OrderItem;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class OrderRecordTest {

    @Test
    public void testOrderRecordConstructor() {
        // Arrange
        String username = "testUser";
        String time = "2022-01-01 12:00:00";

        // Act
        OrderRecord orderRecord = new OrderRecord(username, time);

        // Assert
        assertEquals(username, orderRecord.getUsername());
        assertEquals(time, orderRecord.getTime());
    }

    @Test
    public void testOrderRecordDefaultConstructor() {
        // Arrange & Act
        OrderRecord orderRecord = new OrderRecord();

        // Assert
        assertEquals(0, orderRecord.__TestOrderItem());
    }

    @Test
    public void testOrderRecordAddOrderItem() {
        // Arrange
        OrderRecord orderRecord = new OrderRecord();
        OrderItem orderItem = new OrderItem();

        // Act
        orderRecord.getOrderItems().add(orderItem);

        // Assert
        assertEquals(1, orderRecord.__TestOrderItem());
        assertTrue(orderRecord.getOrderItems().contains(orderItem));
    }
}
