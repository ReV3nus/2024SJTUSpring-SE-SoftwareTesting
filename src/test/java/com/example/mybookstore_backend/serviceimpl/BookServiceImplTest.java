package com.example.mybookstore_backend.serviceimpl;

import com.example.mybookstore_backend.dao.BookDao;
import com.example.mybookstore_backend.entity.Book;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

public class BookServiceImplTest {

    @Mock
    private BookDao bookDao;

    @InjectMocks
    private BookServiceImpl bookService;

    public BookServiceImplTest() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testFindBookById() {
        // Test data
        int bookId = 1;
        Book book = new Book();
        book.setBookId(bookId);
        when(bookDao.findOne(bookId)).thenReturn(book);

        // Call the method being tested
        Book result = bookService.findBookById(bookId);

        // Assertions
        assertThat(result).isNotNull();
        assertThat(result.getBookId()).isEqualTo(bookId);
    }

    @Test
    public void testGetBooks() {
        // Test data
        List<Book> bookList = new ArrayList<>();
        bookList.add(new Book());
        bookList.add(new Book());
        when(bookDao.getBooks()).thenReturn(bookList);

        // Call the method being tested
        List<Book> result = bookService.getBooks();

        // Assertions
        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(2);
        assertThat(result).isEqualTo(bookList);
    }

    @Test
    public void testSaveBook() {
        // Test data
        Book book = new Book();

        // Mock behavior
        when(bookDao.AddBook(book)).thenReturn(book);

        // Call the method being tested
        Book result = bookService.SaveBook(book);

        // Verify interaction with mock
        verify(bookDao, times(1)).AddBook(book);

        // Assertions
        assertThat(result).isEqualTo(book);
    }

    @Test
    public void testDeleteBook() {
        // Test data
        int bookId = 1;

        // Call the method being tested
        bookService.DeleteBook(bookId);

        // Verify interaction with mock
        verify(bookDao, times(1)).DeleteBook(bookId);
    }
}