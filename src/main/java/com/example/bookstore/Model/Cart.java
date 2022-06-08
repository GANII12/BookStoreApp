package com.example.bookstore.Model;

import com.example.bookstore.DTO.CartDTO;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "Cart")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Cart_Id")
    private int cartID;
    private int userId;
    private int bookId;
    private int quantity;

    public Cart(int quantity, Book book, User user) {
    }

    public Cart() {

    }

    public void updateCart(CartDTO cartDTO){
        this.userId = cartDTO.userId;
        this.bookId = cartDTO.bookId;
        this.quantity = cartDTO.quantity;
    }

    public Cart(CartDTO cartDTO) {
        this.updateCart(cartDTO);
    }
    public Cart(int userId,int bookId) {

    }
}
