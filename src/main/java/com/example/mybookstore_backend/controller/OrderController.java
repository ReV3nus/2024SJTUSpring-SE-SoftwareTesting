package com.example.mybookstore_backend.controller;

import com.example.mybookstore_backend.entity.*;
import com.example.mybookstore_backend.service.*;
import jakarta.servlet.http.HttpSession;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/apiOrder")
public class OrderController {
    private final OrderService orderService;
    private final OrderItemService orderItemService;
    private final BookService bookService;
    private final CartService cartService;
    private final UserService userService;

    @Autowired
    public OrderController(OrderService orderService, OrderItemService orderItemService, BookService bookService, CartService cartService, UserService userService) {
        this.orderService = orderService;
        this.orderItemService = orderItemService;
        this.cartService = cartService;
        this.bookService = bookService;
        this.userService = userService;
    }
    @PostMapping("/addItem")
    public ResponseEntity<OrderItem> AddToOrder(@RequestBody Integer BookId, HttpSession session) {
        Object obj = session.getAttribute("username");
        if(obj == null)
        {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        String username= obj.toString();
        User user = userService.findUser(username);
        if(user == null)
        {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = now.format(formatter);

        Book optBook=bookService.findBookById(BookId);
        System.out.println(optBook);
        if(optBook == null)
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        if(optBook.getInventory() <= 0)
        {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        optBook.Remove(1);
        bookService.SaveBook(optBook);

        OrderRecord orderRecord=new OrderRecord(username,formattedDateTime);
        OrderItem orderItem=new OrderItem(optBook.getBookId(),optBook.getName(),optBook.getAuthor(),optBook.getType(),optBook.getPrice(),1,username);
        System.out.print(orderRecord);
        System.out.println();
        orderItem.setOrderRecord(orderRecord);
        orderRecord.getOrderItems().add(orderItem);
        System.out.print(orderItem.__TestOrderRecord());
        System.out.println();
        orderService.AddRecordToOrder(orderRecord);
        orderItemService.AddItemToOrder(orderItem);
        return ResponseEntity.ok(orderItem);
    }
    @PostMapping("/cartPurchase")
    public ResponseEntity<OrderRecord> CartPurchase(HttpSession session) {
        Object obj = session.getAttribute("username");
        if(obj == null)
        {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        String username= obj.toString();
        User user = userService.findUser(username);
        if(user == null)
        {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = now.format(formatter);

        OrderRecord orderRecord=new OrderRecord(username,formattedDateTime);

        List<CartRecord>UserCart=cartService.getCart(username);
        for (CartRecord cartRecord : UserCart) {
            Book optBook=bookService.findBookById(cartRecord.getBookid());
            System.out.println(optBook);
            if(optBook == null)
            {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
            if(optBook.getInventory() <= 0)
            {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
            optBook.Remove(1);
            bookService.SaveBook(optBook);
            System.out.println("Saved.");

            OrderItem orderItem=new OrderItem(optBook.getBookId(),optBook.getName(),optBook.getAuthor(),optBook.getType(),optBook.getPrice(),optBook.getInventory(),username);
            orderItem.setOrderRecord(orderRecord);
            orderRecord.getOrderItems().add(orderItem);
            orderService.AddRecordToOrder(orderRecord);
            orderItemService.AddItemToOrder(orderItem);
        }
        if(orderRecord.getOrderItems().size() == 0)
        {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        System.out.println(orderRecord);
        cartService.ClearCart(username);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/getOrderIds")
    public ResponseEntity<List<OrderRecord>> GetOrderIds(HttpSession session) {
        Object obj = session.getAttribute("username");
        if(obj == null)
        {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        String username= obj.toString();
        User user = userService.findUser(username);
        if(user == null)
        {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        obj = session.getAttribute("authority");
        if(obj == null)
        {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        String usertype = obj.toString();
        if(!Objects.equals(user.getUserAuth().getUsertype(), usertype))
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        List<OrderRecord>ret = null;
        if(Objects.equals(usertype, "User")) {
            ret=orderService.getOrder(username);
        }
        else if(Objects.equals(usertype, "Admin")){
            ret=orderService.getAllOrders();
        }
        System.out.println(ret);
        return ResponseEntity.ok(ret);
    }
    @GetMapping("/getOrderItems")
    public ResponseEntity<List<OrderItem>> GetOrderItems(@RequestParam("oid") Integer oid) {
        System.out.println("get order id"+oid);
        OrderRecord orderRecord=orderService.getRecord(oid);
        if(orderRecord == null)
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        List<OrderItem> orderItems=orderRecord.getOrderItems();
        return ResponseEntity.ok(orderItems);
    }
}
