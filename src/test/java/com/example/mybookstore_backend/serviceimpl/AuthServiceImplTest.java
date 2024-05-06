package com.example.mybookstore_backend.serviceimpl;


import com.example.mybookstore_backend.dao.AuthDao;
import com.example.mybookstore_backend.daoimpl.AuthDaoImpl;
import com.example.mybookstore_backend.entity.UserAuth;
import com.example.mybookstore_backend.repository.AuthRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;


public class AuthServiceImplTest {

    @Mock
    private AuthDao authDao;

    @InjectMocks
    private AuthServiceImpl authService;

    @BeforeEach
    void setUp() {
        authDao = mock(AuthDaoImpl.class);
        authService = new AuthServiceImpl(authDao);
    }

    @Test
    public void testFindUser() {
        // Test data
        String username = "testUser";
        UserAuth userAuth = new UserAuth();
        userAuth.setUsername(username);
        when(authDao.findUser(username)).thenReturn(userAuth);

        // Call the method being tested
        UserAuth result = authService.findUser(username);

        // Assertions
        assertThat(result).isNotNull();
        assertThat(result.getUsername()).isEqualTo(username);
    }

    @Test
    public void testAddAuth() {
        // Test data
        UserAuth userAuth = new UserAuth("testUser", "password123");
        when(authDao.AddAuth(userAuth)).thenReturn(userAuth);

        // Call the method being tested
        UserAuth result = authService.AddAuth(userAuth);

        // Verify interaction with mock
        verify(authDao).AddAuth(userAuth);

        // Assertions
        assertThat(result).isEqualTo(userAuth);
    }

    @Test
    public void testGetAllUsers() {
        // Test data
        List<UserAuth> userList = new ArrayList<>();
        userList.add(new UserAuth());
        userList.add(new UserAuth());
        when(authDao.GetAllUsers()).thenReturn(userList);

        // Call the method being tested
        List<UserAuth> result = authService.GetAllUsers();

        // Assertions
        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(2);
        assertThat(result).isEqualTo(userList);
    }
}






