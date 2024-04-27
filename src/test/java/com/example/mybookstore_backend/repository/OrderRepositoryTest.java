package com.example.mybookstore_backend.repository;

import com.example.mybookstore_backend.entity.OrderRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class OrderRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private OrderRepository orderRepository;

    @BeforeEach
    public void setUp() {
    }

    @Test
    public void testFindOrderRecordsByUsername() {
        OrderRecord order1 = new OrderRecord("user1", "2024-04-27");
        OrderRecord order2 = new OrderRecord("user1", "2024-04-28");
        OrderRecord order3 = new OrderRecord("user2", "2024-04-29");

        OrderRecord persistedOrder1 = entityManager.merge(order1);
        OrderRecord persistedOrder2 = entityManager.merge(order2);
        OrderRecord persistedOrder3 = entityManager.merge(order3);
        entityManager.flush();

        Integer id1 = persistedOrder1.getRecordId();

        // 调用 Repository 方法
        List<OrderRecord> orders = orderRepository.findOrderRecordsByUsername("user1");

        // 验证返回结果
        assertEquals(2, orders.size());
    }

    @Test
    public void testFindOrderRecordByRecordId() {

        OrderRecord order1 = new OrderRecord("user1", "2024-04-27");
        OrderRecord order2 = new OrderRecord("user1", "2024-04-28");
        OrderRecord order3 = new OrderRecord("user2", "2024-04-29");

        OrderRecord persistedOrder1 = entityManager.merge(order1);
        OrderRecord persistedOrder2 = entityManager.merge(order2);
        OrderRecord persistedOrder3 = entityManager.merge(order3);
        entityManager.flush();

        Integer id1 = persistedOrder1.getRecordId();

        // 调用 Repository 方法
        OrderRecord order = orderRepository.findOrderRecordByRecordId(id1);

        // 验证返回结果
        assertEquals("user1", order.getUsername());
    }
}