package com.example.mybookstore_backend.entity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
public class BookTest {
    @Test
    public void testRemove() {
        Book book = new Book(1, "Test Book", "Test Author", "Test Image", "Test ISBN", 10);
        book.Remove(5);
        assertEquals(5, book.getInventory());
    }

    @Test
    public void testConstructors() {
        Book book1 = new Book("Test Book", "Test Author", "Test Image", "Test ISBN", 10);
        assertEquals("Test Book", book1.getName());
        assertEquals("Test Author", book1.getAuthor());
        assertEquals("Test Image", book1.getImage());
        assertEquals("Test ISBN", book1.getIsbn());
        assertEquals(10, book1.getInventory());

        Book book2 = new Book(1, "Test Book 2", "Test Author 2", "Test Image 2", "Test ISBN 2", 20);
        assertEquals(1, book2.getBookId());
        assertEquals("Test Book 2", book2.getName());
        assertEquals("Test Author 2", book2.getAuthor());
        assertEquals("Test Image 2", book2.getImage());
        assertEquals("Test ISBN 2", book2.getIsbn());
        assertEquals(20, book2.getInventory());

        Book nonebook = new Book();
        nonebook.setDescription("test des");
        assertEquals("test des", nonebook.getDescription());
        nonebook.setPrice(1.22);
        assertEquals(1.22,nonebook.getPrice());
        nonebook.setType("test type");
        assertEquals("test type",nonebook.getType());

    }
}
