package com.example.mybookstore_backend.controller;

import com.example.mybookstore_backend.entity.Book;
import com.example.mybookstore_backend.entity.CartRecord;
import com.example.mybookstore_backend.entity.User;
import com.example.mybookstore_backend.entity.UserAuth;
import com.example.mybookstore_backend.service.BookService;
import com.example.mybookstore_backend.service.CartService;
import com.example.mybookstore_backend.service.UserService;
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
    @Mock
    private UserService userService;
    @InjectMocks
    private CartController cartController;

    @BeforeEach
    void setUp() {
        String username = "testUser";
        userService = mock(UserService.class);
        User user = new User(username, "a");
        UserAuth auth = new UserAuth(username,"a");
        user.setUserAuth(auth);
        when(userService.findUser(username)).thenReturn(user);

        session = mock(HttpSession.class);
        when(session.getAttribute("username")).thenReturn(username);
        when(session.getAttribute("authority")).thenReturn("User");

        bookService = mock(BookService.class);
        Book book = new Book(1, "Book Title", "Author","Image", "Isbn", 10);
        when(bookService.findBookById(1)).thenReturn(book);

        cartService = mock(CartService.class);
        CartRecord cartRecord = new CartRecord(1, username, book.getName(), book.getAuthor(), book.getType(), book.getPrice(), 1);
        when(cartService.AddRecordToCart(any())).thenReturn(cartRecord);

        cartController = new CartController(cartService, bookService, userService);
    }

    @AfterEach
    void tearDown() {
    }
    @Test
    public void testAddToCart_Success() {
        // Arrange
        Book book = new Book(1, "Book Title", "Author","Image", "Isbn", 10);
        CartRecord cartRecord = new CartRecord(1, "testUser", book.getName(), book.getAuthor(), book.getType(), book.getPrice(), 1);

        // Act
        ResponseEntity<CartRecord> response = cartController.AddToCart(1, session);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(cartRecord, response.getBody());
        verify(cartService, times(1)).AddRecordToCart(any());
    }

    @Test
    public void testAddToCart_Failure_InvalidSession() {
        // Arrange
        when(session.getAttribute("username")).thenReturn(null);

        // Act
        ResponseEntity<CartRecord> response = cartController.AddToCart(1, session);

        // Assert
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        verify(cartService, never()).AddRecordToCart(any());
    }

    @Test
    public void testAddToCart_Failure_InvalidUsername() {
        // Arrange
        when(session.getAttribute("username")).thenReturn("Unknown");

        // Act
        ResponseEntity<CartRecord> response = cartController.AddToCart(1, session);

        // Assert
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        verify(cartService, never()).AddRecordToCart(any());
    }
    @Test
    public void testAddToCart_Failure_InvalidBook() {
        // Arrange

        // Act
        ResponseEntity<CartRecord> response = cartController.AddToCart(0, session);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        verify(cartService, never()).AddRecordToCart(any());
    }
    @Test
    public void testAddToCart_Failure_UnknownBook() {
        // Arrange

        // Act
        ResponseEntity<CartRecord> response = cartController.AddToCart(100, session);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        verify(cartService, never()).AddRecordToCart(any());
    }
    @Test
    public void testDeleteCart() {
        // Arrange

        // Act

        ResponseEntity<Void> response = cartController.DeleteCart(1, session);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(cartService, times(1)).DeleteCartRecord(1, "testUser");
    }

    @Test
    public void testDeleteCart_Failure_InvalidSession() {
        // Arrange
        when(session.getAttribute("username")).thenReturn(null);

        // Act

        ResponseEntity<Void> response = cartController.DeleteCart(1, session);

        // Assert
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        verify(cartService, never()).DeleteCartRecord(any(),any());
    }

    @Test
    public void testDeleteCart_Failure_InvalidUsername() {
        // Arrange
        when(session.getAttribute("username")).thenReturn("Unknown");

        // Act

        ResponseEntity<Void> response = cartController.DeleteCart(1, session);

        // Assert
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        verify(cartService, never()).DeleteCartRecord(any(),any());
    }
    @Test
    public void testDeleteCart_Failure_InvalidBook() {
        // Arrange

        // Act

        ResponseEntity<Void> response = cartController.DeleteCart(0, session);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        verify(cartService, never()).DeleteCartRecord(any(),any());
    }
    @Test
    public void testDeleteCart_Failure_UnknownBook() {
        // Arrange

        // Act
        ResponseEntity<Void> response = cartController.DeleteCart(100, session);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        verify(cartService, never()).DeleteCartRecord(any(),any());
    }

    @Test
    public void testGetCart() {
        // Arrange
        String username = "testUser";
        CartRecord cartRecord = new CartRecord();
        when(cartService.getCart(username)).thenReturn(Collections.singletonList(cartRecord));

        // Act
        ResponseEntity<List<CartRecord>> response = cartController.GetCart(session);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(Collections.singletonList(cartRecord), response.getBody());
    }
    @Test
    public void testGetCart_Failure_InvalidSession() {
        // Arrange
        String username = "testUser";
        CartRecord cartRecord = new CartRecord();
        when(session.getAttribute("username")).thenReturn(null);
        when(cartService.getCart(username)).thenReturn(Collections.singletonList(cartRecord));

        // Act
        ResponseEntity<List<CartRecord>> response = cartController.GetCart(session);

        // Assert
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }
    @Test
    public void testGetCart_Failure_InvalidUsername() {
        // Arrange
        String username = "Unknown";
        CartRecord cartRecord = new CartRecord();
        when(session.getAttribute("username")).thenReturn(username);
        when(cartService.getCart(username)).thenReturn(Collections.singletonList(cartRecord));

        // Act
        ResponseEntity<List<CartRecord>> response = cartController.GetCart(session);

        // Assert
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    public void testClearCart() {
        // Arrange
        String username = "testUser";
        when(session.getAttribute("username")).thenReturn(username);

        // Act
        ResponseEntity<Void> response = cartController.ClearCart(session);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(cartService, times(1)).ClearCart(username);
    }
    @Test
    public void testClearCart_Failure_InvalidSession() {
        // Arrange
        String username = "testUser";
        CartRecord cartRecord = new CartRecord();
        when(session.getAttribute("username")).thenReturn(null);
        when(cartService.getCart(username)).thenReturn(Collections.singletonList(cartRecord));

        // Act
        ResponseEntity<Void> response = cartController.ClearCart(session);

        // Assert
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }
    @Test
    public void testClearCart_Failure_InvalidUsername() {
        // Arrange
        String username = "Unknown";
        CartRecord cartRecord = new CartRecord();
        when(session.getAttribute("username")).thenReturn(username);
        when(cartService.getCart(username)).thenReturn(Collections.singletonList(cartRecord));

        // Act
        ResponseEntity<Void> response = cartController.ClearCart(session);

        // Assert
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }
}