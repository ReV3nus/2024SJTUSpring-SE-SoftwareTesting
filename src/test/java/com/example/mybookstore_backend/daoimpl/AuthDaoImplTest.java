package com.example.mybookstore_backend.daoimpl;

import com.example.mybookstore_backend.entity.UserAuth;
import com.example.mybookstore_backend.repository.AuthRepository;
import com.example.mybookstore_backend.repository.CartRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AuthDaoImplTest {
    @Mock
    private AuthRepository authRepository;

    @InjectMocks
    private AuthDaoImpl authDao;

    @BeforeEach
    void setUp() {
        authRepository = mock(AuthRepository.class);
        authDao = new AuthDaoImpl(authRepository);
    }

    @Test
    public void testFindUser() {
        UserAuth user = new UserAuth("testUser", "testPassword");
        when(authRepository.findByUsername("testUser")).thenReturn(user);

        assertEquals(user, authDao.findUser("testUser"));
    }

    @Test
    public void testAddAuth() {
        UserAuth user = new UserAuth("testUser", "testPassword");
        when(authRepository.save(user)).thenReturn(user);

        assertEquals(user, authDao.AddAuth(user));
    }

    @Test
    public void testGetAllUsers() {
        UserAuth user1 = new UserAuth("user1", "password1");
        UserAuth user2 = new UserAuth("user2", "password2");
        List<UserAuth> userList = Arrays.asList(user1, user2);
        when(authRepository.findAll()).thenReturn(userList);

        assertEquals(userList, authDao.GetAllUsers());
    }
}
