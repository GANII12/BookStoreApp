package com.example.bookstore.DTO;

import lombok.ToString;

import java.awt.*;

public @ToString class BookDTO {
    public String bookName;
    public String authorName;
    public String bookDescription;
    public String bookImg;
    public int price;
    public int quantity;

}
