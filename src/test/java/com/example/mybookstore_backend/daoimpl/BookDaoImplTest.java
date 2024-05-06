package com.example.mybookstore_backend.daoimpl;


import com.example.mybookstore_backend.entity.Book;
import com.example.mybookstore_backend.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BookDaoImplTest {
    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookDaoImpl bookDao;

    @Test
    public void testFindOne() {
        Book book = new Book("Test Book", "Test Author", "Test Image","Test ISBN",1234);
        Optional<Book> optionalBook = Optional.of(book);
        when(bookRepository.findById(1)).thenReturn(optionalBook);

        assertEquals(book, bookDao.findOne(1));
    }

    @Test
    public void testAddBook() {
        Book book = new Book("Test Book", "Test Author", "Test Image","Test ISBN",1234);
        when(bookRepository.save(book)).thenReturn(book);

        assertEquals(book, bookDao.AddBook(book));
    }

    @Test
    public void testGetBooks() {
        Book book1 = new Book("Test Book 1", "Test Author 1", "Test Image 1","Test ISBN 1",1234);
        Book book2 = new Book("Test Book 2", "Test Author 2", "Test Image 2","Test ISBN 2",12345);
        List<Book> bookList = Arrays.asList(book1, book2);
        when(bookRepository.getBooks()).thenReturn(bookList);

        assertEquals(bookList, bookDao.getBooks());
    }

    @Test
    public void testDeleteBook() {
        int bookId = 1;
        bookDao.DeleteBook(bookId);

        verify(bookRepository).deleteByBookId(bookId);
    }
}