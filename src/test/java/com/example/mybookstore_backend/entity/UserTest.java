package com.example.mybookstore_backend.entity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
public class UserTest {
    @Test
    public void testConstructors() {
        User user1 = new User("testUser", "test@example.com");
        assertEquals("testUser", user1.getUsername());
        assertEquals("test@example.com", user1.getEmail());
        int test = user1.getUserId();

    }

    @Test
    public void testUserAuthAssociation() {
        User user = new User();
        UserAuth userAuth = new UserAuth();
        user.setUserAuth(userAuth);

        assertEquals(userAuth, user.getUserAuth());
    }
}
