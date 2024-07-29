package com.example.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Optional<User>> getUser(@PathVariable long id){
        Optional<User> user=null;

        try {
            user = Optional.of(userService.getUser(id));
            
        } catch (Exception e) {
            HttpHeaders h=new HttpHeaders();
            h.add("Error", "No User Found");
            return new ResponseEntity<>(h,HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(user,HttpStatus.OK);

    }


}
