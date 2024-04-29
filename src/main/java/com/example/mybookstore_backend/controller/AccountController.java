package com.example.mybookstore_backend.controller;


import com.example.mybookstore_backend.entity.User;
import com.example.mybookstore_backend.entity.UserAuth;
import com.example.mybookstore_backend.service.AuthService;
import com.example.mybookstore_backend.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/apiAccount")
public class AccountController {

    private final AuthService authService;
    private final UserService userService;

    @Autowired
    public AccountController(AuthService authService, UserService userService){
        this.authService = authService;
        this.userService = userService;
    }
    @GetMapping("/checkLogin")
    public ResponseEntity<String> checkLogin(HttpSession session) {
        Object obj = session.getAttribute("username");
        if(obj == null)
        {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        String username= obj.toString();
        User user = userService.findUser(username);
        if(user == null)
        {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok(username);
    }
    @GetMapping("/checkAdmin")
    public ResponseEntity<String> checkAdmin(HttpSession session) {
        Object obj = session.getAttribute("username");
        if(obj == null)
        {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        String username= obj.toString();
        User user = userService.findUser(username);
        if(user == null)
        {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        obj = session.getAttribute("authority");
        if(obj == null)
        {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        String auth = obj.toString();
        if(auth!=null)
        {
            return ResponseEntity.ok(auth);
        }
        if(!Objects.equals(user.getUserAuth().getUsertype(), auth))
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        else
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
    @PostMapping("/login")
    public ResponseEntity<String> Login(@RequestBody Map<String, String> loginInfo, HttpSession session) {
        String username = loginInfo.get("username");
        String password = loginInfo.get("password");
        UserAuth User=authService.findUser(username);
        if(User == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        if(!Objects.equals(User.getPassword(), password))
        {
            String error="Wrong Password";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
        session.setAttribute("username",username);
        session.setAttribute("authority",User.getUsertype());
        return ResponseEntity.ok(User.getUsertype());
    }
    @PostMapping("/checkName")
    public ResponseEntity<String> CheckName(@RequestBody Map<String, String> username, HttpSession session) {
        UserAuth user=authService.findUser(username.get("username"));
        if(user==null)return ResponseEntity.ok("NO");
        return ResponseEntity.ok("YES");
    }
    @PostMapping("/logout")
    public ResponseEntity<String> Logout(HttpSession session) {
        session.removeAttribute("username");
        session.removeAttribute("authority");
        return ResponseEntity.ok().build();
    }
    @PostMapping("/register")
    public ResponseEntity<String> Register(@RequestBody Map<String,String> registerInfo ,HttpSession session) {
        String username = (String) registerInfo.get("username");
        String password = (String) registerInfo.get("password");
        String email = (String) registerInfo.get("email");

        User user = userService.findUser(username);
        if(user != null)
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        UserAuth userAuth=new UserAuth(username,password);
        user=new User(username,email);
        user.setUserAuth(userAuth);
        userAuth.setUser(user);
        authService.AddAuth(userAuth);
//        userService.AddUser(user);
        return ResponseEntity.ok().build();
    }
    @PostMapping("/setstate")
    public ResponseEntity<String> SetState(@RequestBody Map<String,String> request ,HttpSession session) {
        String username = (String) request.get("username");
        String usertype = (String) request.get("usertype");
        System.out.println("Got username: "+username+" usertype: "+usertype);

        User user = userService.findUser(username);
        if(user == null)
        {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        UserAuth userAuth=authService.findUser(username);
        if(userAuth==null)
            return ResponseEntity.notFound().build();
        userAuth.setUsertype(usertype);
        authService.AddAuth(userAuth);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/getUsers")
    public ResponseEntity<List<UserAuth>> GetUsers(HttpSession session) {
        Object obj = session.getAttribute("username");
        if(obj == null)
        {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        String username= obj.toString();
        User user = userService.findUser(username);
        if(user == null)
        {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        List<UserAuth> users=authService.GetAllUsers();
        return ResponseEntity.ok(users);
    }

}