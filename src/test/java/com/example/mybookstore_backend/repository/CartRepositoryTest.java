package com.example.mybookstore_backend.repository;

import com.example.mybookstore_backend.entity.CartRecord;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CartRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CartRepository cartRepository;

    @BeforeEach
    public void setUp() {
        CartRecord order1 = new CartRecord(1,"user1", "book1", "author1", "type1", 10.0,1);
        CartRecord order2 = new CartRecord(2,"user1", "book2", "author2", "type2", 10.0,1);
        CartRecord order3 = new CartRecord(3,"user2", "book3", "author3", "type3", 10.0,1);

        CartRecord persistedOrder1 = entityManager.merge(order1);
        CartRecord persistedOrder2 = entityManager.merge(order2);
        CartRecord persistedOrder3 = entityManager.merge(order3);
        entityManager.flush();
    }

    @Test
    public void testFindByUsername() {
        String username = "user1";
        List<CartRecord> cartRecords = cartRepository.findByUsername(username);
        assertEquals(2, cartRecords.size());
        assertTrue(cartRecords.stream().allMatch(record -> record.getUsername().equals(username)));
    }

    @Test
    public void testDeleteByBookidAndUsername() {
        Integer bookId = 1;
        String username = "user1";
        cartRepository.deleteByBookidAndUsername(bookId, username);
        List<CartRecord> cartRecords = cartRepository.findByUsername(username);
        assertEquals(1, cartRecords.size());
        assertTrue(cartRecords.stream().noneMatch(record -> record.getBookid().equals(bookId)));
    }

    @Test
    public void testDeleteByUsername() {
        String username = "user2";
        cartRepository.deleteByUsername(username);
        List<CartRecord> cartRecords = cartRepository.findByUsername(username);
        assertTrue(cartRecords.isEmpty());
    }
}