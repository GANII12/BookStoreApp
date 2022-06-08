package com.example.bookstore.service;


import com.example.bookstore.DTO.BookDTO;
import com.example.bookstore.DTO.ResponseBookDTO;
import com.example.bookstore.DTO.ResponseUserDTO;
import com.example.bookstore.DTO.UserDTO;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

public interface IUserService {
    ResponseEntity<ResponseUserDTO> getAllUsers();
    ResponseEntity<ResponseUserDTO> getUserById(Optional<String> id, String token);
    ResponseEntity<ResponseUserDTO> getUserByEmailId(String email);
    ResponseEntity<ResponseUserDTO> createUser(UserDTO userDTO);
    ResponseEntity<ResponseUserDTO> updateUser( UserDTO userDTO, String token );
    // forgotpassword/changepassword
    String changePassword(String email,String password);
    String loginUser(String email , String password);
}
