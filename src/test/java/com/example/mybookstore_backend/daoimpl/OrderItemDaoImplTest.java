package com.example.mybookstore_backend.daoimpl;

import com.example.mybookstore_backend.dao.OrderItemDao;
import com.example.mybookstore_backend.daoimpl.OrderItemDaoImpl;
import com.example.mybookstore_backend.entity.OrderItem;
import com.example.mybookstore_backend.repository.OrderItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


public class OrderItemDaoImplTest {

    @Mock
    private OrderItemRepository orderItemRepository;

    @InjectMocks
    private OrderItemDaoImpl orderItemDao;

    @BeforeEach
    void setUp() {
        orderItemRepository = mock(OrderItemRepository.class);
        orderItemDao = new OrderItemDaoImpl(orderItemRepository);
    }

    @Test
    public void testAddItem() {
        // Arrange
        OrderItem item = new OrderItem();
        when(orderItemRepository.save(any(OrderItem.class))).thenReturn(item);

        // Act
        OrderItem result = orderItemDao.AddItem(item);

        // Assert
        assertEquals(item, result);
        verify(orderItemRepository, times(1)).save(item);
    }
}