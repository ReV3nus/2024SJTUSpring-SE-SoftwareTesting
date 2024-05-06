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
        String username = "testUser";
        userService = mock(UserService.class);
        User user = new User(username, "a");
        UserAuth auth = new UserAuth(username,"a");
        user.setUserAuth(auth);
        when(userService.findUser(username)).thenReturn(user);

        session = mock(HttpSession.class);
        when(session.getAttribute("username")).thenReturn(username);
        when(session.getAttribute("authority")).thenReturn("User");

        orderService = mock(OrderService.class);
        orderItemService = mock(OrderItemService.class);

        bookService = mock(BookService.class);
        Book book1 = new Book(1, "Book Title", "Author","Image", "Isbn", 10);
        Book book2 = new Book(2, "Book Title", "Author","Image", "Isbn", 0);
        when(bookService.findBookById(1)).thenReturn(book1);
        when(bookService.findBookById(2)).thenReturn(book2);

        cartService = mock(CartService.class);
        List<CartRecord> userCart = new ArrayList<>();
        userCart.add(new CartRecord(1, username, "Book", "Author", "Type", 10.0, 10));
        when(cartService.getCart(username)).thenReturn(userCart);


        orderController = new OrderController(orderService, orderItemService, bookService, cartService, userService);
    }

    @Test
    public void testAddToOrder_Success() {
        // Act
        ResponseEntity<OrderItem> response = orderController.AddToOrder(1, session);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testAddToOrder_Failed_InvalidSession() {
        // Arrange
        when(session.getAttribute("username")).thenReturn(null);

        // Act
        ResponseEntity<OrderItem> response = orderController.AddToOrder(1, session);

        // Assert
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    public void testAddToOrder_Failed_InvalidUsername() {
        // Arrange
        String username = "UnknownUser";
        when(session.getAttribute("username")).thenReturn(username);

        // Act
        ResponseEntity<OrderItem> response = orderController.AddToOrder(1, session);

        // Assert
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    public void testAddToOrder_Failed_InvalidBook() {
        // Arrange

        // Act
        ResponseEntity<OrderItem> response = orderController.AddToOrder(5, session);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void testAddToOrder_Failed_InvalidInventory() {
        // Arrange

        // Act
        ResponseEntity<OrderItem> response = orderController.AddToOrder(2, session);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void testCartPurchase_Success() {
        // Arrange

        // Act
        ResponseEntity<OrderRecord> response = orderController.CartPurchase(session);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testCartPurchase_Failed_InvalidSession() {
        // Arrange
        when(session.getAttribute("username")).thenReturn(null);

        // Act
        ResponseEntity<OrderRecord> response = orderController.CartPurchase(session);

        // Assert
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    public void testCartPurchase_Failed_InvalidUsername() {
        // Arrange
        String username = "unknown";
        when(session.getAttribute("username")).thenReturn(username);

        // Act
        ResponseEntity<OrderRecord> response = orderController.CartPurchase(session);

        // Assert
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    public void testCartPurchase_Failed_InvalidBook() {
        // Arrange
        List<CartRecord> userCart = new ArrayList<>();
        userCart.add(new CartRecord(5, "testUser", "Book", "Author", "Type", 10.0, 10));
        when(cartService.getCart("testUser")).thenReturn(userCart);

        // Act
        ResponseEntity<OrderRecord> response = orderController.CartPurchase(session);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
    @Test
    public void testCartPurchase_Failed_InvalidCount() {
        // Arrange
        List<CartRecord> userCart = new ArrayList<>();
        userCart.add(new CartRecord(2, "testUser", "Book", "Author", "Type", 10.0, 10));
        when(cartService.getCart("testUser")).thenReturn(userCart);

        // Act
        ResponseEntity<OrderRecord> response = orderController.CartPurchase(session);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
    @Test
    public void testCartPurchase_Failed_EmptyCart() {
        // Arrange
        List<CartRecord> userCart = new ArrayList<>();
        when(cartService.getCart("testUser")).thenReturn(userCart);

        // Act
        ResponseEntity<OrderRecord> response = orderController.CartPurchase(session);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
    @Test
    public void testGetOrderIds_User() {
        // Arrange
        String username = "testUser";
        List<OrderRecord> orders = new ArrayList<>();
        orders.add(new OrderRecord(username, "2024-04-30 10:00:00"));
        when(orderService.getOrder(username)).thenReturn(orders);

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
    public void testGetOrderIds_Failed_InvalidSession() {
        // Arrange
        when(session.getAttribute("username")).thenReturn(null);

        // Act
        ResponseEntity<List<OrderRecord>> response = orderController.GetOrderIds(session);

        // Assert
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    public void testGetOrderIds_Failed_InvalidSession2() {
        // Arrange
        when(session.getAttribute("authority")).thenReturn(null);

        // Act
        ResponseEntity<List<OrderRecord>> response = orderController.GetOrderIds(session);

        // Assert
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }
    @Test
    public void testGetOrderIds_Failed_InvalidUsername() {
        // Arrange
        String username = "unknown";
        when(session.getAttribute("username")).thenReturn(username);

        // Act
        ResponseEntity<List<OrderRecord>> response = orderController.GetOrderIds(session);

        // Assert
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }
    @Test
    public void testGetOrderIds_Failed_InvalidAuth() {
        // Arrange
        when(session.getAttribute("authority")).thenReturn("Admin");

        // Act
        ResponseEntity<List<OrderRecord>> response = orderController.GetOrderIds(session);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
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
    @Test
    public void testGetOrderItems_Failed() {
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
        ResponseEntity<List<OrderItem>> response = orderController.GetOrderItems(2);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(null, response.getBody());
    }
}