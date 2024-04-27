package com.example.mybookstore_backend.daoimpl;

import com.example.mybookstore_backend.dao.CartDao;
import com.example.mybookstore_backend.entity.CartRecord;
import com.example.mybookstore_backend.repository.CartRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


public class CartDaoImplTest {

    @Mock
    private CartRepository cartRepository;

    @InjectMocks
    private CartDaoImpl cartDao;

    @BeforeEach
    void setUp() {
        cartRepository = mock(CartRepository.class);
        cartDao = new CartDaoImpl(cartRepository);
    }

    @Test
    public void testAddRecord() {
        // Arrange
        CartRecord record = new CartRecord();
        when(cartRepository.save(any(CartRecord.class))).thenReturn(record);

        // Act
        CartRecord result = cartDao.AddRecord(record);

        // Assert
        assertEquals(record, result);
        verify(cartRepository, times(1)).save(record);
    }

    @Test
    public void testDeleteRecord() {
        // Arrange
        Integer bookId = 1;
        String user = "testUser";

        // Act
        cartDao.DeleteRecord(bookId, user);

        // Assert
        verify(cartRepository, times(1)).deleteByBookidAndUsername(bookId, user);
    }

    @Test
    public void testGetCart() {
        // Arrange
        String user = "testUser";
        List<CartRecord> userCart = new ArrayList<>();
        userCart.add(new CartRecord(1, user, "Bookname", "Author", "Type", 10.0, 10));
        when(cartRepository.findByUsername(user)).thenReturn(userCart);

        // Act
        List<CartRecord> result = cartDao.getCart(user);

        // Assert
        assertEquals(userCart, result);
        verify(cartRepository, times(1)).findByUsername(user);
    }

    @Test
    public void testClearCart() {
        // Arrange
        String user = "testUser";

        // Act
        cartDao.ClearCart(user);

        // Assert
        verify(cartRepository, times(1)).deleteByUsername(user);
    }
}