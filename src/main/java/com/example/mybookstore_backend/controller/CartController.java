package com.example.mybookstore_backend.controller;

import com.example.mybookstore_backend.entity.Book;
import com.example.mybookstore_backend.entity.CartRecord;
import com.example.mybookstore_backend.entity.User;
import com.example.mybookstore_backend.service.BookService;
import com.example.mybookstore_backend.service.CartService;
import com.example.mybookstore_backend.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/apiCart")
public class CartController {

    private final CartService cartService;
    private final BookService bookService;
    private final UserService userService;
    @Autowired
    public CartController(CartService cartService, BookService bookService, UserService userService) {
        this.cartService = cartService;
        this.bookService = bookService;
        this.userService = userService;
    }
    @PostMapping("/add")
    public ResponseEntity<CartRecord> AddToCart(@RequestBody Integer BookId, HttpSession session) {
        System.out.println("##Received book ID: " + BookId);
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
        if(BookId < 0)
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        Book optBook=bookService.findBookById(BookId);
        if(optBook == null)
        {
            return ResponseEntity.badRequest().build();
        }
        CartRecord cartRecord;
        cartRecord=new CartRecord(BookId,username,optBook.getName(),optBook.getAuthor(),optBook.getType(),optBook.getPrice(),1);
        CartRecord ret=cartService.AddRecordToCart(cartRecord);
        System.out.println(ret);
        return ResponseEntity.ok(ret);
    }
    @PostMapping("/del")
    public ResponseEntity<Void> DeleteCart(@RequestBody Integer BookId, HttpSession session) {
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

        if(BookId < 0)
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        Book optBook=bookService.findBookById(BookId);
        if(optBook == null)
        {
            return ResponseEntity.badRequest().build();
        }

        cartService.DeleteCartRecord(BookId,username);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/getCart")
    public ResponseEntity<List<CartRecord>> GetCart(HttpSession session) {
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

        return ResponseEntity.ok(cartService.getCart(username));
    }
    @PostMapping("/clear")
    public ResponseEntity<Void> ClearCart(HttpSession session){
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

        cartService.ClearCart(username);
        return ResponseEntity.ok().build();
    }
}
