package com.example.mybookstore_backend.daoimpl;

import com.example.mybookstore_backend.entity.User;
import com.example.mybookstore_backend.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserDaoImplTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserDaoImpl userDao;

    @Test
    public void testFindUser() {
        User user = new User("testUser", "password123");
        when(userRepository.findByUsername("testUser")).thenReturn(user);

        assertEquals(user, userDao.findUser("testUser"));
    }

    @Test
    public void testAddUser() {
        User user = new User("testUser", "password123");
        when(userRepository.save(user)).thenReturn(user);

        assertEquals(user, userDao.AddUser(user));
    }
}