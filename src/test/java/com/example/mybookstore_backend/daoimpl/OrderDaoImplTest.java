package com.example.mybookstore_backend.daoimpl;

import com.example.mybookstore_backend.daoimpl.OrderDaoImpl;
import com.example.mybookstore_backend.entity.OrderRecord;
import com.example.mybookstore_backend.repository.OrderRepository;
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

public class OrderDaoImplTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderDaoImpl orderDao;

    @BeforeEach
    void setUp() {
        orderRepository = mock(OrderRepository.class);
        orderDao = new OrderDaoImpl(orderRepository);
    }

    @Test
    public void testAddRecord() {
        // Arrange
        OrderRecord record = new OrderRecord();
        when(orderRepository.save(any(OrderRecord.class))).thenReturn(record);

        // Act
        OrderRecord result = orderDao.AddRecord(record);

        // Assert
        assertEquals(record, result);
        verify(orderRepository, times(1)).save(record);
    }

    @Test
    public void testGetOrder() {
        // Arrange
        String user = "testUser";
        List<OrderRecord> userOrders = new ArrayList<>();
        userOrders.add(new OrderRecord());
        when(orderRepository.findOrderRecordsByUsername(user)).thenReturn(userOrders);

        // Act
        List<OrderRecord> result = orderDao.getOrder(user);

        // Assert
        assertEquals(userOrders, result);
        verify(orderRepository, times(1)).findOrderRecordsByUsername(user);
    }

    @Test
    public void testGetRecord() {
        // Arrange
        Integer recordId = 1;
        OrderRecord record = new OrderRecord();
        when(orderRepository.findOrderRecordByRecordId(recordId)).thenReturn(record);

        // Act
        OrderRecord result = orderDao.getRecord(recordId);

        // Assert
        assertEquals(record, result);
        verify(orderRepository, times(1)).findOrderRecordByRecordId(recordId);
    }

    @Test
    public void testGetAllOrders() {
        // Arrange
        List<OrderRecord> allOrders = new ArrayList<>();
        allOrders.add(new OrderRecord());
        when(orderRepository.findAll()).thenReturn(allOrders);

        // Act
        List<OrderRecord> result = orderDao.getAllOrders();

        // Assert
        assertEquals(allOrders, result);
        verify(orderRepository, times(1)).findAll();
    }
}
