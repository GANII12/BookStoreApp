package com.example.bookstore.DTO;

import com.example.bookstore.Model.Book;
import com.example.bookstore.Model.User;
import lombok.Data;
import lombok.ToString;

@Data
public @ToString class CartDTO {
    public int userId;
    public int bookId;
    public int quantity;
}
