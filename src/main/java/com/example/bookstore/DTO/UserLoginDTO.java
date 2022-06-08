package com.example.bookstore.DTO;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class UserLoginDTO {
    public String Email;
    public String password;
    public UserLoginDTO(String EMail , String password) {
        this.Email = EMail;
        this.password = password;
    }
}
