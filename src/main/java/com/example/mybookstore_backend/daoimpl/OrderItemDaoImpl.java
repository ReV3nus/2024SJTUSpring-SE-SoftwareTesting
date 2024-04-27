package com.example.mybookstore_backend.daoimpl;

import com.example.mybookstore_backend.dao.OrderItemDao;
import com.example.mybookstore_backend.entity.OrderItem;
import com.example.mybookstore_backend.repository.OrderItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OrderItemDaoImpl implements OrderItemDao {

    private final OrderItemRepository orderItemRepository;

    @Autowired
    public OrderItemDaoImpl(OrderItemRepository orderItemRepository){
        this.orderItemRepository = orderItemRepository;
    }
    @Override
    public OrderItem AddItem(OrderItem item){return orderItemRepository.save(item);}
}
