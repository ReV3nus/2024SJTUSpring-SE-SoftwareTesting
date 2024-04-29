package com.example.mybookstore_backend.serviceimpl;

import com.example.mybookstore_backend.dao.UserDao;
import com.example.mybookstore_backend.entity.User;
import com.example.mybookstore_backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    @Autowired
    public UserServiceImpl(UserDao userDao){
        this.userDao = userDao;
    }
    @Override
    public User findUser(String user){return userDao.findUser(user);}
    @Override
    public User AddUser(User user){return userDao.AddUser(user);}
}
