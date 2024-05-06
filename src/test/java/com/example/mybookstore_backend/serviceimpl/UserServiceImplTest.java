package com.example.mybookstore_backend.serviceimpl;

import com.example.mybookstore_backend.dao.UserDao;
import com.example.mybookstore_backend.entity.User;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

public class UserServiceImplTest {

    @Mock
    private UserDao userDao;

    @InjectMocks
    private UserServiceImpl userService;

    public UserServiceImplTest() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testFindUser() {
        // Test data
        String username = "testUser";
        User user = new User();
        user.setUsername(username);
        when(userDao.findUser(username)).thenReturn(user);

        // Call the method being tested
        User result = userService.findUser(username);

        // Assertions
        assertThat(result).isNotNull();
        assertThat(result.getUsername()).isEqualTo(username);
    }

    @Test
    public void testAddUser() {
        // Test data
        User user = new User();

        // Mock behavior
        when(userDao.AddUser(user)).thenReturn(user);

        // Call the method being tested
        User result = userService.AddUser(user);

        // Verify interaction with mock
        verify(userDao, times(1)).AddUser(user);

        // Assertions
        assertThat(result).isEqualTo(user);
    }

}