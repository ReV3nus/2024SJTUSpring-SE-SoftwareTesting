package com.example.mybookstore_backend.repository;

import com.example.mybookstore_backend.entity.CartRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository  extends JpaRepository<CartRecord,Integer> {
    List<CartRecord> findByUsername(String username);
    void deleteByBookidAndUsername(Integer bid,String user);

    void deleteByUsername(String username);
}
