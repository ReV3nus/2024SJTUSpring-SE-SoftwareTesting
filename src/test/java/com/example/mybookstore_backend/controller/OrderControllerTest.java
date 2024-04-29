package com.example.mybookstore_backend.controller;

import com.example.mybookstore_backend.controller.OrderController;
import com.example.mybookstore_backend.entity.*;
import com.example.mybookstore_backend.service.*;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

class OrderControllerTest {

    @Mock
    private OrderService orderService;

    @Mock
    private OrderItemService orderItemService;

    @Mock
    private BookService bookService;

    @Mock
    private CartService cartService;
    @Mock
    private UserService userService;

    @Mock
    private HttpSession session;

    @InjectMocks
    private OrderController orderController;

    @BeforeEach
    void setUp() {
        session = mock(HttpSession.class);
        orderService = mock(OrderService.class);
        orderItemService = mock(OrderItemService.class);
        bookService = mock(BookService.class);
        cartService = mock(CartService.class);
        userService = mock(UserService.class);
        orderController = new OrderController(orderService, orderItemService, bookService, cartService, userService);
        User user = new User("testUser", "a");
        UserAuth auth = new UserAuth("testUser","a");
        user.setUserAuth(auth);

        when(userService.findUser(any(String.class))).thenReturn(user);
    }

    @Test
    public void testAddToOrder_Success() {
        // Arrange
        int bookId = 1;
        String username = "testUser";
        Book book = new Book(bookId, "Book Title", "Author","Image", "Isbn", 10);
        when(session.getAttribute("username")).thenReturn(username);
        when(bookService.findBookById(any(Integer.class))).thenReturn(book);
        ResponseEntity<OrderItem> expectedResponse = ResponseEntity.ok(new OrderItem());

        // Act
        ResponseEntity<OrderItem> response = orderController.AddToOrder(bookId, session);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testCartPurchase_Success() {
        // Arrange
        int bookId = 1;
        String username = "testUser";
        List<CartRecord> userCart = new ArrayList<>();
        userCart.add(new CartRecord(1, username, "Book", "Author", "Type", 10.0, 10));
        Book book = new Book(bookId, "Book Title", "Author","Image", "Isbn", 10);
        when(session.getAttribute("username")).thenReturn(username);
        when(bookService.findBookById(any(Integer.class))).thenReturn(book);
        when(cartService.getCart(username)).thenReturn(userCart);
        ResponseEntity<OrderRecord> expectedResponse = ResponseEntity.ok().build();

        // Act
        ResponseEntity<OrderRecord> response = orderController.CartPurchase(session);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testGetOrderIds_User() {
        // Arrange
        String username = "testUser";
        when(session.getAttribute("username")).thenReturn(username);
        when(session.getAttribute("authority")).thenReturn("User");
        List<OrderRecord> orders = new ArrayList<>();
        orders.add(new OrderRecord(username, "2024-04-30 10:00:00"));
        when(orderService.getOrder(username)).thenReturn(orders);
        ResponseEntity<List<OrderRecord>> expectedResponse = ResponseEntity.ok(orders);

        // Act
        ResponseEntity<List<OrderRecord>> response = orderController.GetOrderIds(session);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(orders, response.getBody());
    }

    @Test
    public void testGetOrderIds_Admin() {
        // Arrange
        String username = "testUser";
        User user = new User("testUser", "a");
        UserAuth auth = new UserAuth("testUser","a");
        auth.setUsertype("Admin");
        user.setUserAuth(auth);
        when(userService.findUser(any(String.class))).thenReturn(user);

        when(session.getAttribute("username")).thenReturn(username);
        when(session.getAttribute("authority")).thenReturn("Admin");
        List<OrderRecord> orders = new ArrayList<>();
        orders.add(new OrderRecord("user1", "2024-04-30 10:00:00"));
        orders.add(new OrderRecord("user2", "2024-04-30 11:00:00"));
        when(orderService.getAllOrders()).thenReturn(orders);
        ResponseEntity<List<OrderRecord>> expectedResponse = ResponseEntity.ok(orders);

        // Act
        ResponseEntity<List<OrderRecord>> response = orderController.GetOrderIds(session);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(orders, response.getBody());
    }

    @Test
    public void testGetOrderItems() {
        // Arrange
        int orderId = 1;
        OrderRecord orderRecord = new OrderRecord("testUser", "2024-04-30 10:00:00");
        orderRecord.setRecordId(orderId);
        List<OrderItem> orderItems = new ArrayList<>();
        orderItems.add(new OrderItem(1, "Book Title", "Author", "Type", 10.0, 1, "testUser"));
        orderRecord.setOrderItems(orderItems);
        when(orderService.getRecord(orderId)).thenReturn(orderRecord);
        ResponseEntity<List<OrderItem>> expectedResponse = ResponseEntity.ok(orderItems);

        // Act
        ResponseEntity<List<OrderItem>> response = orderController.GetOrderItems(orderId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(orderItems, response.getBody());
    }
}