package com.example.mybookstore_backend.controller;

import com.example.mybookstore_backend.entity.Book;
import com.example.mybookstore_backend.entity.CartRecord;
import com.example.mybookstore_backend.service.BookService;
import com.example.mybookstore_backend.service.CartService;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;


class CartControllerTest {

    @Mock
    private CartService cartService;

    @Mock
    private BookService bookService;

    @Mock
    private HttpSession session;

    @InjectMocks
    private CartController cartController;

    @BeforeEach
    void setUp() {
        session = mock(HttpSession.class);
        bookService = mock(BookService.class);
        cartService = mock(CartService.class);
        cartController = new CartController(cartService, bookService);
    }

    @AfterEach
    void tearDown() {
    }
    @Test
    public void testAddToCart_Success() {
        // Arrange
        int bookId = 1;
        String username = "testUser";
        Book book = new Book(bookId, "Book Title", "Author","Image", "Isbn", 10);
        CartRecord cartRecord = new CartRecord(bookId, username, book.getName(), book.getAuthor(), book.getType(), book.getPrice(), 1);
        when(session.getAttribute("username")).thenReturn(username);
        when(bookService.findBookById(anyInt())).thenReturn(book);
        when(cartService.AddRecordToCart(any())).thenReturn(cartRecord);

        // Act
        ResponseEntity<CartRecord> response = cartController.AddToCart(bookId, session);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(cartRecord, response.getBody());
        verify(cartService, times(1)).AddRecordToCart(any());
    }

    @Test
    public void testAddToCart_Failure() {
        // Arrange
        int bookId = 1;
        String username = "testUser";
        when(session.getAttribute("username")).thenReturn(username);
        when(bookService.findBookById(anyInt())).thenReturn(null);

        // Act
        ResponseEntity<CartRecord> response = cartController.AddToCart(bookId, session);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        verify(cartService, never()).AddRecordToCart(any());
    }

    @Test
    public void testDeleteCart() {
        // Arrange
        int bookId = 1;
        String username = "testUser";

        // Act
        when(session.getAttribute("username")).thenReturn(username);
        ResponseEntity<Void> response = cartController.DeleteCart(bookId, session);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(cartService, times(1)).DeleteCartRecord(bookId, username);
    }

    @Test
    public void testGetCart() {
        // Arrange
        String username = "testUser";
        CartRecord cartRecord = new CartRecord();
        when(session.getAttribute("username")).thenReturn(username);
        when(cartService.getCart(username)).thenReturn(Collections.singletonList(cartRecord));

        // Act
        ResponseEntity<List<CartRecord>> response = cartController.GetCart(session);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(Collections.singletonList(cartRecord), response.getBody());
    }

    @Test
    public void testClearCart() {
        // Arrange
        String username = "testUser";

        // Act
        when(session.getAttribute("username")).thenReturn(username);
        ResponseEntity<Void> response = cartController.ClearCart(session);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(cartService, times(1)).ClearCart(username);
    }
}