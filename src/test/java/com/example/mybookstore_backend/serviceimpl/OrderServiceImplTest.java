package com.example.mybookstore_backend.serviceimpl;

import com.example.mybookstore_backend.dao.OrderDao;
import com.example.mybookstore_backend.entity.OrderRecord;
import com.example.mybookstore_backend.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class OrderServiceImplTest {

    @Mock
    private OrderDao orderDao;

    @InjectMocks
    private OrderServiceImpl orderService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddRecordToOrder() {
        OrderRecord record = new OrderRecord();
        when(orderDao.AddRecord(record)).thenReturn(record);

        OrderRecord result = orderService.AddRecordToOrder(record);

        assertEquals(record, result);
        verify(orderDao, times(1)).AddRecord(record);
    }

    @Test
    public void testGetOrder() {
        String user = "testUser";
        List<OrderRecord> orders = new ArrayList<>();
        when(orderDao.getOrder(user)).thenReturn(orders);

        List<OrderRecord> result = orderService.getOrder(user);

        assertEquals(orders, result);
        verify(orderDao, times(1)).getOrder(user);
    }

    @Test
    public void testGetRecord() {
        Integer rid = 1;
        OrderRecord record = new OrderRecord();
        when(orderDao.getRecord(rid)).thenReturn(record);

        OrderRecord result = orderService.getRecord(rid);

        assertEquals(record, result);
        verify(orderDao, times(1)).getRecord(rid);
    }

    @Test
    public void testGetAllOrders() {
        List<OrderRecord> orders = new ArrayList<>();
        when(orderDao.getAllOrders()).thenReturn(orders);

        List<OrderRecord> result = orderService.getAllOrders();

        assertEquals(orders, result);
        verify(orderDao, times(1)).getAllOrders();
    }
}