package com.example.bookstore.Model;

import com.example.bookstore.DTO.BookDTO;
import lombok.Data;

import javax.persistence.*;
import java.awt.*;
@Entity
@Data
@Table(name = "Books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Book_Id")
    private int id;
    @Column(name = "Book_Name")
    private String bookName;
    private String authorName;
    private String bookDescription;
    private String bookImg;
    private int price;
    private int quantity;

    public void updateBook(BookDTO bookDTO){
        this.bookName = bookDTO.bookName;
        this.authorName =  bookDTO.authorName;
        this.bookDescription = bookDTO.bookDescription;
        this.bookImg =  bookDTO.bookImg;
        this.price =  bookDTO.price;
        this.quantity =  bookDTO.quantity;
    }

    public Book(BookDTO bookDTO) {
        this.updateBook(bookDTO);
    }

    public Book() {
    }
}
