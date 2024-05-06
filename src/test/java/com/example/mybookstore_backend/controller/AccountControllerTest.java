package com.example.mybookstore_backend.controller;

import com.example.mybookstore_backend.entity.User;
import com.example.mybookstore_backend.entity.UserAuth;
import com.example.mybookstore_backend.service.AuthService;
import com.example.mybookstore_backend.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AccountControllerTest {

    @Mock
    private HttpSession session;

    @Mock
    private AuthService authService;

    @Mock
    private UserService userService;

    @InjectMocks
    private AccountController accountController;

    @BeforeEach
    void setUp() {
        session = mock(HttpSession.class);
        authService = mock(AuthService.class);
        userService = mock(UserService.class);
        accountController = new AccountController(authService, userService);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testCheckLogin_Success() {
        // Arrange
        String username = "testUser";
        User user = new User(username, "test@example.com");
        when(session.getAttribute("username")).thenReturn(username);
        when(userService.findUser(username)).thenReturn(user);

        // Act
        ResponseEntity<String> response = accountController.checkLogin(session);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(username, response.getBody());
    }

    @Test
    void testCheckLogin_Failed() {
        // Arrange
        String username = "testUser";
        User user = new User(username, "test@example.com");
        when(session.getAttribute("username")).thenReturn(username);
        when(userService.findUser(username)).thenReturn(null);

        // Act
        ResponseEntity<String> response = accountController.checkLogin(session);

        // Assert
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    void testCheckLogin_Unauthorized() {
        // Arrange
        when(session.getAttribute("username")).thenReturn(null);

        // Act
        ResponseEntity<String> response = accountController.checkLogin(session);

        // Assert
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());

    }

    @Test
    void testCheckAdmin_Failed() {
        // Arrange
        String username = "testUser";
        User user = new User(username, "test@example.com");
        user.setUserAuth(new UserAuth(username, "password"));
        when(session.getAttribute("username")).thenReturn(username);
        when(userService.findUser(username)).thenReturn(user);

        // Act
        ResponseEntity<String> response = accountController.checkAdmin(session);

        // Assert
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        // assertEquals(authority, response.getBody());
    }

    @Test
    void testCheckAdmin_Unauthorized() {
        // Arrange
        when(session.getAttribute("username")).thenReturn(null);
        // Act
        ResponseEntity<String> response = accountController.checkAdmin(session);

        // Assert
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());

        // Arrange
        String username = "testUser";
        User user = new User(username, "test@example.com");
        user.setUserAuth(new UserAuth(username, "password"));
        when(session.getAttribute("username")).thenReturn(username);
        when(userService.findUser(username)).thenReturn(null);

        // Act
        response = accountController.checkAdmin(session);

        // Assert
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    void testCheckAdmin_Success() {
        // Arrange
        String username = "testUser";
        String authority = "admin";
        User user = new User(username, "test@example.com");
        user.setUserAuth(new UserAuth(username, "password"));
        when(session.getAttribute("username")).thenReturn(username);
        when(userService.findUser(username)).thenReturn(user);
        when(session.getAttribute("authority")).thenReturn(authority);

        // Act
        ResponseEntity<String> response = accountController.checkAdmin(session);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(authority, response.getBody());
    }


    @Test
    void testCheckAdmin_NullUser() {
        // Arrange
        String username = "testUser";
        String authority = "admin";
        when(session.getAttribute("username")).thenReturn(username);
        when(userService.findUser(username)).thenReturn(null);

        // Act
        ResponseEntity<String> response = accountController.checkAdmin(session);

        // Assert
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    void testCheckAdmin_NullAuthority() {
        // Arrange
        String username = "testUser";
        User user = new User(username, "test@example.com");
        when(session.getAttribute("username")).thenReturn(username);
        when(userService.findUser(username)).thenReturn(user);
        when(session.getAttribute("authority")).thenReturn(null);

        // Act
        ResponseEntity<String> response = accountController.checkAdmin(session);

        // Assert
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    void testCheckAdmin_MismatchedAuthority() {
        // Arrange
        String username = "testUser";
        String authority = "user";
        User user = new User(username, "test@example.com");
        user.setUserAuth(new UserAuth(username, "password"));
        when(session.getAttribute("username")).thenReturn(username);
        when(userService.findUser(username)).thenReturn(user);
        when(session.getAttribute("authority")).thenReturn(authority);

        // Act
        ResponseEntity<String> response = accountController.checkAdmin(session);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testLogin_Success() {
        // Arrange
        String username = "testUser";
        String password = "testPassword";
        Map<String, String> loginInfo = new HashMap<>();
        loginInfo.put("username", username);
        loginInfo.put("password", password);
        UserAuth userAuth = new UserAuth(username, password);
        when(authService.findUser(username)).thenReturn(userAuth);

        // Act
        ResponseEntity<String> response = accountController.Login(loginInfo, session);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("User", response.getBody());
    }

    @Test
    void testLogin_NullUser() {
        // Arrange
        String username = "nonExistingUser";
        String password = "testPassword";
        Map<String, String> loginInfo = new HashMap<>();
        loginInfo.put("username", username);
        loginInfo.put("password", password);
        when(authService.findUser(username)).thenReturn(null);

        // Act
        ResponseEntity<String> response = accountController.Login(loginInfo, session);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void testLogin_WrongPassword() {
        // Arrange
        String username = "testUser";
        String password = "wrongPassword";
        Map<String, String> loginInfo = new HashMap<>();
        loginInfo.put("username", username);
        loginInfo.put("password", password);
        UserAuth userAuth = new UserAuth(username, "correctPassword");
        when(authService.findUser(username)).thenReturn(userAuth);

        // Act
        ResponseEntity<String> response = accountController.Login(loginInfo, session);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void testCheckName_UserExists() {
        // Arrange
        String username = "existingUser";
        Map<String, String> request = new HashMap<>();
        request.put("username", username);
        when(authService.findUser(username)).thenReturn(new UserAuth(username, "password"));

        // Act
        ResponseEntity<String> response = accountController.CheckName(request, session);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("YES", response.getBody());
    }

    @Test
    void testCheckName_UserNotExists() {
        // Arrange
        String username = "nonExistingUser";
        Map<String, String> request = new HashMap<>();
        request.put("username", username);
        when(authService.findUser(username)).thenReturn(null);

        // Act
        ResponseEntity<String> response = accountController.CheckName(request, session);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("NO", response.getBody());
    }

    @Test
    void testLogout() {
        // Act
        ResponseEntity<String> response = accountController.Logout(session);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(session).removeAttribute("username");
        verify(session).removeAttribute("authority");
    }

    @Test
    void testRegister_Success() {
        // Arrange
        String username = "newUser";
        String password = "newPassword";
        String email = "new@example.com";
        Map<String, String> registerInfo = new HashMap<>();
        registerInfo.put("username", username);
        registerInfo.put("password", password);
        registerInfo.put("email", email);
        when(userService.findUser(username)).thenReturn(null);

        // Act
        ResponseEntity<String> response = accountController.Register(registerInfo, session);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(authService).AddAuth(any(UserAuth.class));
    }

    @Test
    void testRegister_UserExists() {
        // Arrange
        String existingUsername = "existingUser";
        Map<String, String> registerInfo = new HashMap<>();
        registerInfo.put("username", existingUsername);
        when(userService.findUser(existingUsername)).thenReturn(new User(existingUsername, "existing@example.com"));

        // Act
        ResponseEntity<String> response = accountController.Register(registerInfo, session);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void testSetState_Success() {
        // Arrange
        String username = "existingUser";
        String userType = "admin";
        Map<String, String> request = new HashMap<>();
        request.put("username", username);
        request.put("usertype", userType);
        User user = new User(username, "existing@example.com");
        when(userService.findUser(username)).thenReturn(user);
        when(authService.findUser(username)).thenReturn(new UserAuth(username, "password"));

        // Act
        ResponseEntity<String> response = accountController.SetState(request, session);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(authService).AddAuth(any(UserAuth.class));
    }

    @Test
    void testSetState_UserNotFound() {
        // Arrange
        String username = "nonExistingUser";
        String userType = "admin";
        Map<String, String> request = new HashMap<>();
        request.put("username", username);
        request.put("usertype", userType);
        when(userService.findUser(username)).thenReturn(null);

        // Act
        ResponseEntity<String> response = accountController.SetState(request, session);

        // Assert
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    void testSetState_UserAuthNotFound() {
        // Arrange
        String username = "existingUser";
        String userType = "admin";
        Map<String, String> request = new HashMap<>();
        request.put("username", username);
        request.put("usertype", userType);
        User user = new User(username, "existing@example.com");
        when(userService.findUser(username)).thenReturn(user);
        when(authService.findUser(username)).thenReturn(null);

        // Act
        ResponseEntity<String> response = accountController.SetState(request, session);

        // Assert
        assertEquals(ResponseEntity.notFound().build(), response);
    }

    @Test
    void testGetUsers_Success() {
        // Arrange
        String username = "testUser";
        User user = new User(username, "test@example.com");
        when(session.getAttribute("username")).thenReturn(username);
        when(userService.findUser(username)).thenReturn(user);
        List<UserAuth> userList = new ArrayList<>();
        userList.add(new UserAuth("user1", "password1"));
        userList.add(new UserAuth("user2", "password2"));
        when(authService.GetAllUsers()).thenReturn(userList);

        // Act
        ResponseEntity<List<UserAuth>> response = accountController.GetUsers(session);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(userList, response.getBody());
    }

    @Test
    void testGetUsers_Unauthorized() {
        // Arrange
        when(session.getAttribute("username")).thenReturn(null);

        // Act
        ResponseEntity<List<UserAuth>> response = accountController.GetUsers(session);

        // Assert
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    void testGetUsers_Unauthorized_2() {
        // Arrange
        String username = "testUser";
        User user = new User(username, "test@example.com");
        when(session.getAttribute("username")).thenReturn(username);
        when(userService.findUser(username)).thenReturn(null);

        // Act
        ResponseEntity<List<UserAuth>> response = accountController.GetUsers(session);

        // Assert
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());

    }



    // Write similar tests for other methods in AccountController
}
