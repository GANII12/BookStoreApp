package com.example.bookstore.DTO;

import com.example.bookstore.Model.Book;
import com.example.bookstore.Model.User;
import lombok.Data;
import lombok.ToString;
import org.hibernate.query.criteria.internal.expression.function.CurrentDateFunction;

import java.nio.file.LinkOption;
import java.time.LocalDate;
@Data
public @ToString class OrderDTO {
    public LocalDate date;
    public int price;
    public int quantity;
    public String address;
    public int userId;
    public int bookId;
    public boolean cancel;
}
