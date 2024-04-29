package com.example.mybookstore_backend.serviceimpl;

import com.example.mybookstore_backend.dao.OrderItemDao;
import com.example.mybookstore_backend.entity.OrderItem;
import com.example.mybookstore_backend.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderItemServiceImpl implements OrderItemService {

    private final OrderItemDao orderItemDao;
    @Autowired
    public OrderItemServiceImpl(OrderItemDao orderItemDao){
        this.orderItemDao = orderItemDao;
    }
    @Override
    public OrderItem AddItemToOrder(OrderItem item){return orderItemDao.AddItem(item);}
}
