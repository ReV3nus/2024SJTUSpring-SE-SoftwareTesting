package com.example.mybookstore_backend.serviceimpl;

import com.example.mybookstore_backend.dao.OrderItemDao;
import com.example.mybookstore_backend.entity.OrderItem;
import com.example.mybookstore_backend.service.OrderItemService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;

public class OrderItemServiceImplTest {

    @Mock
    private OrderItemDao orderItemDao;

    @InjectMocks
    private OrderItemServiceImpl orderItemService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddItemToOrder() {
        OrderItem item = new OrderItem();

        orderItemService.AddItemToOrder(item);

        verify(orderItemDao).AddItem(item);
    }
}