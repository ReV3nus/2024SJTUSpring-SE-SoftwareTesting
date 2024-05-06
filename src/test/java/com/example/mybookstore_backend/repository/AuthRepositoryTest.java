package com.example.mybookstore_backend.repository;

import com.example.mybookstore_backend.entity.UserAuth;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
public class AuthRepositoryTest {

    @Autowired
    private AuthRepository authRepository;

    @Test
    public void testFindByUsername() {
        UserAuth userAuth = new UserAuth("testUser", "password123");
        authRepository.save(userAuth);

        UserAuth foundUserAuth = authRepository.findByUsername("testUser");
        assertNotNull(foundUserAuth);
        assertEquals("testUser", foundUserAuth.getUsername());
        assertEquals("password123", foundUserAuth.getPassword());
    }

    @Test
    public void testFindAll() {
        UserAuth userAuth1 = new UserAuth("user1", "pass1");
        UserAuth userAuth2 = new UserAuth("user2", "pass2");
        authRepository.save(userAuth1);
        authRepository.save(userAuth2);

        List<UserAuth> allUserAuths = authRepository.findAll();
        assertEquals(2, allUserAuths.size());
    }
}