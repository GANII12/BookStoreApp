package com.example.bookstore.controllers;

import com.example.bookstore.DTO.*;
import com.example.bookstore.Model.User;
import com.example.bookstore.repository.UserRepository;
import com.example.bookstore.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.lang.constant.Constable;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("User")
@Slf4j
public class UserRegistrationController {
    @Autowired
    private IUserService userService;

    @Autowired
    private UserRepository userRepository;

    //get all users
    @GetMapping(value = {"","/","/get"})
    public ResponseEntity<ResponseUserDTO> getAllUsers(){
        return userService.getAllUsers();
    }

    //user login
    @PostMapping("/login")
    public String loginUser(@RequestParam String email , @RequestParam String password){
        UserLoginDTO userLoginDTO = new UserLoginDTO(email,password);
        System.out.println(email);
        System.out.println(password);
        String response = userService.loginUser(userLoginDTO.getEmail(), userLoginDTO.getPassword());
        return response;
    }

    //get user by Id
    @GetMapping("/get/{token}")
    public ResponseEntity<ResponseUserDTO> getUserById(@PathVariable Optional<String> id , @RequestParam(name = "token") String token){
        return userService.getUserById(id, token);
    }

    //create user
    @PostMapping("/create")
    public ResponseEntity<ResponseUserDTO> createUser(@Valid @RequestBody UserDTO userDTO){
        return userService.createUser(userDTO);
    }

    //update user
    @PutMapping("/update")
    public ResponseEntity<ResponseUserDTO> updateUser( @Valid @RequestBody UserDTO userDTO , @RequestParam(name = "token") String token){
        return userService.updateUser(userDTO,token);
    }

    //forgot password
    @PutMapping("/changePassword")
    public String changePassword(@RequestParam String email , @RequestParam String password){
        return userService.changePassword(email,password);
    }

}
