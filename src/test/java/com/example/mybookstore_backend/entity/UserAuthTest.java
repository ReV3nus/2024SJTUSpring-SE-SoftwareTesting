package com.example.mybookstore_backend.entity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
public class UserAuthTest {
    @Test
    public void testConstructors() {
        UserAuth userAuth1 = new UserAuth("testUser", "password123");
        assertEquals("testUser", userAuth1.getUsername());
        assertEquals("password123", userAuth1.getPassword());
        assertEquals("User", userAuth1.getUsertype());
        int test = userAuth1.getUserid();
    }

    @Test
    public void testUserAssociation() {
        UserAuth userAuth = new UserAuth();
        User user = new User();
        userAuth.setUser(user);

        assertEquals(user, userAuth.getUser());
    }
}
