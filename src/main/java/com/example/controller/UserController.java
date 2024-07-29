package com.example.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.User;
import com.example.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/addUser")
    public String addUser(@RequestBody User user){
        try {
            userService.addUser(user);
        } catch (Exception e) {
            
            e.printStackTrace();
        }
        return "added succesfully";
        
    }
    @GetMapping("/getUser/{id}")
    public User getUser(@PathVariable long id){
        Optional<User> user=userService.getUser(id);
        return user.get();

    }


}
