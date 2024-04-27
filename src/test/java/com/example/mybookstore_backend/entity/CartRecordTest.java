package com.example.mybookstore_backend.entity;

import com.example.mybookstore_backend.entity.CartRecord;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CartRecordTest {

    @Test
    public void testCartRecordConstructor() {
        // Arrange
        Integer bookId = 1;
        String username = "testUser";
        String bookName = "Book Title";
        String author = "Author";
        String type = "Type";
        Double price = 10.0;
        Integer count = 1;

        // Act
        CartRecord cartRecord = new CartRecord(bookId, username, bookName, author, type, price, count);

        // Assert
        assertEquals(bookId, cartRecord.getBookid());
        assertEquals(username, cartRecord.getUsername());
        assertEquals(bookName, cartRecord.getBookname());
        assertEquals(author, cartRecord.getAuthor());
        assertEquals(type, cartRecord.getType());
        assertEquals(price, cartRecord.getPrice());
        assertEquals(count, cartRecord.getCount());
    }

    @Test
    public void testCartRecordDefaultConstructor() {
        // Arrange & Act
        CartRecord cartRecord = new CartRecord();

        // Assert
        assertEquals(0, cartRecord.getRecordId());
        assertEquals(null, cartRecord.getBookid());
        assertEquals(null, cartRecord.getUsername());
        assertEquals(null, cartRecord.getBookname());
        assertEquals(null, cartRecord.getAuthor());
        assertEquals(null, cartRecord.getType());
        assertEquals(null, cartRecord.getPrice());
        assertEquals(null, cartRecord.getCount());
    }
}
