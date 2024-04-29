package com.example.mybookstore_backend.daoimpl;

import com.example.mybookstore_backend.controller.CartController;
import com.example.mybookstore_backend.dao.CartDao;
import com.example.mybookstore_backend.entity.CartRecord;
import com.example.mybookstore_backend.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CartDaoImpl implements CartDao {

    private final CartRepository cartRepository;

    @Autowired
    public CartDaoImpl(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }
    @Override
    public CartRecord AddRecord(CartRecord record){
        return cartRepository.save(record);
    }
    @Override
    public void DeleteRecord(Integer bid,String user){
        cartRepository.deleteByBookidAndUsername(bid,user);
    }
    @Override
    public List<CartRecord> getCart(String user){
        return cartRepository.findByUsername(user);
    }
    @Override
    public void ClearCart(String user){cartRepository.deleteByUsername(user);}
}
