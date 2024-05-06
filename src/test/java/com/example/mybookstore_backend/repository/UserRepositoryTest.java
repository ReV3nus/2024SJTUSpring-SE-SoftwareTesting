package com.example.mybookstore_backend.repository;

import com.example.mybookstore_backend.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testFindByUsername() {

        User foundUser = userRepository.findByUsername("1234");
        assertNotNull(foundUser);
        assertEquals("1234", foundUser.getUsername());
        assertEquals("奔少", foundUser.getEmail());
    }
}