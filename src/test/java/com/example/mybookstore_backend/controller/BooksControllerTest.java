package com.example.mybookstore_backend.controller;

import com.example.mybookstore_backend.entity.Book;
import com.example.mybookstore_backend.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class BooksControllerTest {

    @Mock
    private BookService bookService;

    @InjectMocks
    private BooksController booksController;

    @BeforeEach
    void setUp() {
        bookService = mock(BookService.class);
        booksController = new BooksController(bookService);
    }

    @Test
    void testGetBooks() {
        // Arrange
        List<Book> expectedBooks = Collections.singletonList(new Book());
        when(bookService.getBooks()).thenReturn(expectedBooks);

        // Act
        ResponseEntity<List<Book>> response = booksController.getBooks();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedBooks, response.getBody());
    }

    @Test
    void testGetBook_ValidId() {
        // Arrange
        int validId = 1;
        Book expectedBook = new Book(validId, "Title", "Author", "URL", "ISBN", 10);
        when(bookService.findBookById(validId)).thenReturn(expectedBook);

        // Act
        ResponseEntity<Book> response = booksController.getBook(validId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedBook, response.getBody());
    }

    @Test
    void testGetBook_InvalidId() {
        // Arrange
        int invalidId = -1;

        // Act
        ResponseEntity<Book> response = booksController.getBook(invalidId);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void testGetBook_InvalidBook() {
        // Arrange
        int validId = 1;
        Book expectedBook = new Book(validId, "Title", "Author", "URL", "ISBN", 10);
        when(bookService.findBookById(validId)).thenReturn(null);

        // Act
        ResponseEntity<Book> response = booksController.getBook(validId);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

    }

    @Test
    void testAddBook() {
        // Arrange
        Map<String, String> bookData = new java.util.HashMap<>(Collections.singletonMap("name", "Book Name"));
        bookData.put("inventory", "123");
        Book newBook = new Book();
        when(bookService.SaveBook(any(Book.class))).thenReturn(newBook);

        // Act
        ResponseEntity<Book> response = booksController.addBook(bookData);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(bookService).SaveBook(any(Book.class));
    }

    @Test
    void testModifyBook_ValidId() {
        // Arrange
        int validId = 1;
        Map<String, String> bookData = new HashMap<>();
        bookData.put("bookid", String.valueOf(validId));
        bookData.put("name", "New Book Name");
        bookData.put("author", "New Author");
        bookData.put("url", "New URL");
        bookData.put("isbn", "New ISBN");
        bookData.put("inventory", "123");

        Book existingBook = new Book(validId, "Old Book Name", "Old Author", "Old URL", "Old ISBN", 100);
        when(bookService.findBookById(validId)).thenReturn(existingBook);

        // Act
        ResponseEntity<Book> response = booksController.modifyBook(bookData);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(bookService).SaveBook(any(Book.class));
    }

    @Test
    void testModifyBook_InvalidId() {
        // Arrange
        int invalidId = -1;
        Map<String, String> bookData = Collections.singletonMap("bookid", String.valueOf(invalidId));

        // Act
        ResponseEntity<Book> response = booksController.modifyBook(bookData);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void testDeleteBook_ValidId() {
        // Arrange
        int validId = 1;
        Map<String, String> bookData = Collections.singletonMap("bookid", String.valueOf(validId));
        Book existingBook = new Book(validId, "Existing Book", "Existing Author", "Existing URL", "Existing ISBN", 100);
        when(bookService.findBookById(validId)).thenReturn(existingBook);

        // Act
        ResponseEntity<Book> response = booksController.deleteBook(bookData);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(bookService).DeleteBook(validId);
    }

    @Test
    void testDeleteBook_InvalidId() {
        // Arrange
        int invalidId = -1;
        Map<String, String> bookData = Collections.singletonMap("bookid", String.valueOf(invalidId));

        // Act
        ResponseEntity<Book> response = booksController.deleteBook(bookData);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }


    // Add tests for other methods like modifyBook and deleteBook similarly
}