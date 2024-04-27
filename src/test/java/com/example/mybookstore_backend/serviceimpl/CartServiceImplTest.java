package com.example.mybookstore_backend.serviceimpl;

import com.example.mybookstore_backend.dao.CartDao;
import com.example.mybookstore_backend.entity.CartRecord;
import com.example.mybookstore_backend.serviceimpl.CartServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class CartServiceImplTest {

    @Mock
    private CartDao cartDao;

    @InjectMocks
    private CartServiceImpl cartService;

    @Test
    public void testAddRecordToCart() {
        CartRecord record = new CartRecord();
        when(cartDao.AddRecord(record)).thenReturn(record);

        CartRecord result = cartService.AddRecordToCart(record);

        assertEquals(record, result);
    }

    @Test
    public void testDeleteCartRecord() {
        Integer bookId = 1;
        String username = "testUser";

        cartService.DeleteCartRecord(bookId, username);

        verify(cartDao).DeleteRecord(bookId, username);
    }

    @Test
    public void testGetCart() {
        String username = "testUser";
        List<CartRecord> expected = Collections.singletonList(new CartRecord());
        when(cartDao.getCart(username)).thenReturn(expected);

        List<CartRecord> result = cartService.getCart(username);

        assertEquals(expected, result);
    }

    @Test
    public void testClearCart() {
        String username = "testUser";

        cartService.ClearCart(username);

        verify(cartDao).ClearCart(username);
    }
}