package com.example.bookstore.DTO;

import lombok.Data;
import lombok.ToString;
@Data
public @ToString class UserDTO {
    public String fName;
    public String lName;
    public String Email;
    public String address;
    public String password;


}
