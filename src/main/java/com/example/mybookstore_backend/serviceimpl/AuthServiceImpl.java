package com.example.mybookstore_backend.serviceimpl;

import com.example.mybookstore_backend.dao.AuthDao;
import com.example.mybookstore_backend.entity.UserAuth;
import com.example.mybookstore_backend.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthServiceImpl implements AuthService {
    private final AuthDao authDao;
    @Autowired
    public AuthServiceImpl(AuthDao authDao){
        this.authDao = authDao;
    }
    @Override
    public UserAuth findUser(String user){return authDao.findUser(user);}
    @Override
    public UserAuth AddAuth(UserAuth auth){return authDao.AddAuth(auth);}
    @Override
    public List<UserAuth> GetAllUsers(){return authDao.GetAllUsers();}
}
