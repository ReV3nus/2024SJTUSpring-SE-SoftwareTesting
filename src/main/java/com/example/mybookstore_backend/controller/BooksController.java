package com.example.mybookstore_backend.controller;

import com.example.mybookstore_backend.entity.Book;
import com.example.mybookstore_backend.service.BookService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/apiBook")
public class BooksController{


    private final BookService bookService;
    @Autowired
    public BooksController(BookService bookService){
        this.bookService = bookService;
    }
    @GetMapping("/getbooks")
    public ResponseEntity<List<Book>> getBooks()
    {
        List<Book> books;
        books = bookService.getBooks();
        return ResponseEntity.ok(books);
//        return ;
    }
    @GetMapping("/getbook")
    public ResponseEntity<Book> getBook(@RequestParam("id") Integer id)
    {
        if(id < 0)
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        Book book;
        book = bookService.findBookById(id);
        if(book == null)
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(book);
    }
    @PostMapping("/AddBook")
    public ResponseEntity<Book> addBook(@RequestBody Map<String, String> BookData)
    {
        String name = BookData.get("name");
        String author = BookData.get("author");
        String url = BookData.get("url");
        String isbn = BookData.get("isbn");
        Integer inventory = Integer.parseInt(BookData.get("inventory"));
        Book newBook=new Book(name,author,url,isbn,inventory);
        bookService.SaveBook(newBook);
        System.out.println(newBook);
        return ResponseEntity.ok().build();
    }
    @PostMapping("/ModifyBook")
    public ResponseEntity<Book> modifyBook(@RequestBody Map<String, String> BookData)
    {
        String stringId = BookData.get("bookid");
        int id=Integer.parseInt(stringId);
        if(id < 0)
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        Book book;
        book = bookService.findBookById(id);
        if(book == null)
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        String name = BookData.get("name");
        String author = BookData.get("author");
        String url = BookData.get("url");
        String isbn = BookData.get("isbn");
        Integer inventory = Integer.parseInt(BookData.get("inventory"));
        Book newBook=new Book(id,name,author,url,isbn,inventory);
        bookService.SaveBook(newBook);
        return ResponseEntity.ok().build();
    }
    @PostMapping("/DeleteBook")
    public ResponseEntity<Book> deleteBook(@RequestBody Map<String,String> BookData)
    {
        String stringId = BookData.get("bookid");
        int id=Integer.parseInt(stringId);
        if(id < 0)
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        Book book;
        book = bookService.findBookById(id);
        if(book == null)
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        System.out.println(id);
        bookService.DeleteBook(id);
        return ResponseEntity.ok().build();
    }
}