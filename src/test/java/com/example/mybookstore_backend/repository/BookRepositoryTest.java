package com.example.mybookstore_backend.repository;

import com.example.mybookstore_backend.entity.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

@DataJpaTest
public class BookRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private BookRepository bookRepository;

    @Test
    public void testGetBooks() {
        // Add some test data to the database
        Book book1 = new Book("Book1", "Author1", "image1.jpg", "ISBN1", 10);
        entityManager.persist(book1);

        Book book2 = new Book("Book2", "Author2", "image2.jpg", "ISBN2", 15);
        entityManager.persist(book2);

        // Call the method being tested
        List<Book> books = bookRepository.getBooks();

        // Assertions
        assertThat(books).isNotNull();
        assertThat(books.size()).isEqualTo(2);
        assertThat(books).contains(book1, book2);
        // Add more assertions as needed
    }

    @Test
    public void testDeleteByBookId() {
        // Add some test data to the database
        Book bookToDelete = entityManager.persist(new Book("BookToDelete", "Author", "image.jpg", "ISBN", 20));
        int bookId = bookToDelete.getBookId();

        // Call the method being tested
        bookRepository.deleteByBookId(bookId);

        // Verify deletion
        assertThat(bookRepository.findById(bookId)).isEmpty();
    }
}