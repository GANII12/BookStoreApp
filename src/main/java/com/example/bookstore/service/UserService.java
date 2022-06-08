package com.example.bookstore.service;

import com.example.bookstore.DTO.ResponseUserDTO;
import com.example.bookstore.DTO.UserDTO;
import com.example.bookstore.Model.User;
import com.example.bookstore.exceptions.BookStoreException;
import com.example.bookstore.repository.UserRepository;
import com.example.bookstore.util.EmailSenderService;
import com.example.bookstore.util.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserService implements IUserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TokenUtil tokenUtil;
    @Autowired
    private EmailSenderService sender;

    @Override
    public ResponseEntity<ResponseUserDTO> getAllUsers() {
        log.info("Fetching all User Details...");
        List<User> userList = userRepository.findAll();
        ResponseUserDTO respUserDTO = new ResponseUserDTO("All User Details",userList);
        return new ResponseEntity<>(respUserDTO, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResponseUserDTO> getUserById(Optional<String> id, String token) {
        log.info("Retrieving information from the DB");
        int tokenId = tokenUtil.decodeToken(token);
        ResponseUserDTO responseUserDTO;
        Optional<User> tokenBook = userRepository.findById(Math.toIntExact(tokenId));
        if(tokenBook.isEmpty()){
            responseUserDTO = new ResponseUserDTO("Error :This is not an authorized User!",null,token);
            return new ResponseEntity<ResponseUserDTO>(responseUserDTO,HttpStatus.UNAUTHORIZED);
        }
        Optional<User> user = userRepository.findById(Math.toIntExact(Long.parseLong(id.get())));
        User user1 = user.orElse(null);

        if (user.isPresent()){
            responseUserDTO = new ResponseUserDTO("Found the Book",user,null);
            return new ResponseEntity<ResponseUserDTO>(responseUserDTO,HttpStatus.OK);
        }
        return null;
    }

    @Override
    public ResponseEntity<ResponseUserDTO> getUserByEmailId(String email) {

        return null;
    }

    @Override
    public ResponseEntity<ResponseUserDTO> createUser(UserDTO userDTO) {
        User user = new User(userDTO);
        log.debug("User Data: " +user.toString());
        userRepository.save(user);
        String token = tokenUtil.createToken(user.getUserId());
        sender.sendEmail("ganeshmoturu1467@gmail.com","Test Email","New User Created SuccessFully");
        ResponseUserDTO responseUserDTO = new ResponseUserDTO("User created",user,token);
        return new  ResponseEntity<>(responseUserDTO,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResponseUserDTO> updateUser( UserDTO userDTO,String token) {
        Integer Id = tokenUtil.decodeToken(token);
        Optional<User> users = userRepository.findById(Id);
        if(users.isEmpty()){
            throw new BookStoreException("User details for given Id is not found");
        }
        User user = new User(userDTO);
        user.setUserId(Id);
        User user1 = userRepository.save(user);
        ResponseUserDTO responseUserDTO = new ResponseUserDTO("Book Updated",user1,token);
        return new ResponseEntity<>(responseUserDTO,HttpStatus.OK);
    }

    @Override
    public String changePassword(String email,String password) {
        Optional<User> user = userRepository.findByEmailId(email);
        if (!user.isPresent()){
            throw new BookStoreException("User not found");
        }else {
            User user1 = user.get();
            user1.setPassword(password);
            userRepository.save(user1);
            return "Password changed successfully";
        }

    }

    @Override
    public String loginUser(String email, String password) {
        System.out.println("service class");
        System.out.println(email);
        System.out.println(password);
        Optional<User> login = userRepository.findByEmailId(email);
        System.out.println(login);
        if (login.isPresent()){
            String pass = login.get().getPassword();
            System.out.println(pass);
            System.out.println(password);
            if (login.get().getPassword().equals(password)){
                return "User has logged in successfully";
            }else {
                return "Wrong password!!";
            }
        }
        return "User not found";
    }


}
