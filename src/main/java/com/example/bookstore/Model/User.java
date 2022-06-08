package com.example.bookstore.Model;

import com.example.bookstore.DTO.UserDTO;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "User")
public @Data class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "User_Id")
    private int userId;
    private String fName;
    private String lName;
    private String Email;
    private String address;
    private String password;

    public void updateUser(UserDTO userDTO){
        this.fName = userDTO.fName;
        this.lName = userDTO.lName;
        this.Email = userDTO.Email;
        this.address = userDTO.address;
        this.password = userDTO.password;
    }

    public User(UserDTO userDTO) {
        this.updateUser(userDTO);
    }

    public User() {
    }
}
