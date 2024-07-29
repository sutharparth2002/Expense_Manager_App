package com.example.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.entity.User;
import com.example.respository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public void addUser(User user) throws Exception {
        if(user.getName()==null|| user.getEmailId()==null || user.getMobNo()==null){
            throw error("Fill the All Fields");
        }
        userRepository.save(user);
    }

    private Exception error(String string) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException(string);
    }

    @Override
    public User getUser(long id) throws Exception {
        User u=null;
        try {
            u= userRepository.findById(id).get();
            
        } catch (Exception e) {
            throw error("User Not Found");
        }
        return u;
    }

    

}
